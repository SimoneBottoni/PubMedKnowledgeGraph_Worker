package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.AnnotatorService;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TestTags2 {

    private static final String path = "src/test/java/com/example/pubmedknowledgegraph/test_files/PubmedArticleSet.xml";

    private final WebApplicationContext context;

    @Autowired
    public TestTags2(WebApplicationContext context) {
        this.context = context;
    }

    public void setUp() {
        ParserService parser = (ParserService) context.getBean("parserService");
        AnnotatorService annotatorService = (AnnotatorService) context.getBean("annotatorService");
        HandlerContainer handlerContainer = parser.parse(path);
        List<Tag> tagMainList = annotatorService.Annotate(path, handlerContainer.getArticleMainList(), handlerContainer.getBookMainList());
    }

    @Test
    void TestArticleTag() {
        long startTime = System.nanoTime();
        setUp();
        final long duration = System.nanoTime() - startTime;
        System.out.println("Execution time is " + ((duration) / (1000000000)) + " seconds");
    }
}
