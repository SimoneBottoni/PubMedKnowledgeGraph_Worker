package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.ArticleTag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.BookTag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.AnnotatorService;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TestTags {

    private final WebApplicationContext context;

    private static final String path = "src/test/java/com/example/pubmedknowledgegraph/test_files/PubmedArticleSet.xml";
    private HandlerContainer handlerContainer;

    @Autowired
    public TestTags(WebApplicationContext context) {
        this.context = context;
    }

    @BeforeAll
    public void setUp() {
        ParserService parser = (ParserService) context.getBean("parserService");
        AnnotatorService annotatorService = (AnnotatorService) context.getBean("annotatorService");
        handlerContainer = parser.parse(path);
        List<Tag> tagMainList = annotatorService.Annotate(path, handlerContainer.getArticleMainList(), handlerContainer.getBookMainList());
    }


    @Test
    void TestArticleTag() {
        List <ArticleTag> articleTagList = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348557")).collect(Collectors.toList())).get(0).getArticleTagList();
        ArticleTag articleTag1 = articleTagList.stream().filter(item -> item.getTag().getCUI().equals("C0002045")).collect(Collectors.toList()).get(0);
        assertEquals(articleTag1.getScore(), "2.30");
        assertEquals(articleTag1.getPosition(), "Title");
        assertEquals(articleTag1.getArticle().getPmid(), "33348557");
        assertEquals(articleTag1.getTag().getSemanticType(), "[inpr]");
        assertEquals(articleTag1.getTag().getPreferredName(), "algorithm");

        ArticleTag articleTag2 = articleTagList.stream().filter(item -> item.getTag().getCUI().equals("C0443131")).collect(Collectors.toList()).get(0);
        assertEquals(articleTag2.getScore(), "1.38");
        assertEquals(articleTag2.getPosition(), "Abstract");
        assertEquals(articleTag2.getArticle().getPmid(), "33348557");
        assertEquals(articleTag2.getTag().getSemanticType(), "[qlco]");
        assertEquals(articleTag2.getTag().getPreferredName(), "Accurate (qualifier)");

    }

    @Test
    void TestBookTag() {
        List <BookTag> bookTagList = (handlerContainer.getBookMainList().stream().
                filter(item -> item.getPmid().equals("92349121")).collect(Collectors.toList())).get(0).getBookTagList();
        int x = 12;

        BookTag bookTag1 = bookTagList.stream().filter(item -> item.getTag().getCUI().equals("C3272565")).collect(Collectors.toList()).get(0);
        assertEquals(bookTag1.getScore(), "0.46");
        assertEquals(bookTag1.getPosition(), "Title");
        assertEquals(bookTag1.getBook().getPmid(), "92349121");
        assertEquals(bookTag1.getTag().getSemanticType(), "[inpr]");
        assertEquals(bookTag1.getTag().getPreferredName(), "Clinical Lot");

        BookTag bookTag2 = bookTagList.stream().filter(item -> item.getTag().getCUI().equals("C1553386")).collect(Collectors.toList()).get(0);
        assertEquals(bookTag2.getScore(), "0.46");
        assertEquals(bookTag2.getPosition(), "Abstract");
        assertEquals(bookTag2.getBook().getPmid(), "92349121");
        assertEquals(bookTag2.getTag().getSemanticType(), "[inpr]");
        assertEquals(bookTag2.getTag().getPreferredName(), "normal Act Status");
    }
}