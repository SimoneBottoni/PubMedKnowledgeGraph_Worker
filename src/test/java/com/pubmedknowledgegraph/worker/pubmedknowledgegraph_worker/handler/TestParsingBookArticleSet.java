package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.handler;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TestParsingBookArticleSet {
    private static final String path = "src/test/java/com/pubmedknowledgegraph/master/test_files/PubmedArticleSet.xml";
    private HandlerContainer handlerContainer;

    @Autowired
    private ParserService parserService;

    @BeforeAll
    public void setUp() {
        handlerContainer = parserService.parse(path);
    }

    @Test
    void TestPubmedBook() {
        Book book = (handlerContainer.getBookMainList().stream().
                filter(item -> item.getPmid().equals("92349121")).collect(Collectors.toList())).get(0);

        assertEquals(book.getPmid(), "92349121");
        assertEquals(book.getVersion(), "1");
        assertEquals(book.getPublisherList().get(0).getName(), "PublisherName");
        assertEquals(book.getPublisherList().get(0).getLocation(), "Location");
        assertEquals(book.getBookTitle(), " Clinical Policy: Critical Issues in the Management of Adult Patients Presenting to the Emergency Department With Community-Acquired Pneumonia. ");
        assertEquals(book.getPubDate(), "2021-Jan");
        assertEquals(book.getBeginningDate(), "2020-Jan");
        assertEquals(book.getEndingDate(), "2020-Jan");
        assertEquals(book.getVolume(), "145");
        assertEquals(book.getVolumeTitle(), "Book");
        assertEquals(book.getEdition(), "123");
        assertEquals(book.getCollectionTitle(), "Book of books");
        assertEquals(book.getMedium(), "hello");
        assertEquals(book.getReportNumber(), "66");
        assertEquals(book.getVernacularTitle(), "VernacularTitleValue");
        assertEquals(book.getStartPage(), "2 ");
        assertEquals(book.getEndPage(), "5");
        assertEquals(book.getMedlinePgn(), " 12-A ");
        assertTrue(book.getAbstractText().contains("Changes in DNA methylation, whether hypo- or hypermethylation"));
        assertTrue(book.getCopyrightInformation().contains("Copyright © 2012 Pathological Socie"));
        assertEquals(book.getContributionDate(), "2020-Jan");
        assertEquals(book.getDateRevised(), "2010-Jan");

        Set<ArticleID> articleIDSet  =   book.getArticleIDList();
        List<ArticleID> articleIDList = new ArrayList<>(articleIDSet);
        ArticleID articleID1 = articleIDList.stream().
                filter(item -> item.getArticleId().equals("33349374")).collect(Collectors.toList()).get(0);
        assertEquals(articleID1.getType(), "pubmed");

        BookAuthor bookAuthor = book.getBookAuthorList().stream().
                filter(item -> item.getRole().equals("Author")).
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("Smith")).collect(Collectors.toList()).get(0);
        assertEquals(bookAuthor.getRole(), "Author");
        assertEquals(bookAuthor.getAuthor().getInitials(), "MD");
        assertEquals(bookAuthor.getAuthor().getForeName(), "Michael D");
        assertEquals(bookAuthor.getBook().getPmid(), "92349121");

        BookAuthor bookInvestigator = book.getBookAuthorList().stream().
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("Wolf")).collect(Collectors.toList()).get(0);
        assertEquals(bookInvestigator.getRole(), "Investigator");
        assertEquals(bookInvestigator.getAuthor().getInitials(), "SJ");
        assertEquals(bookInvestigator.getAuthor().getForeName(), "Stephen J");
        assertEquals(bookInvestigator.getBook().getPmid(), "92349121");
        BookAuthor bookInvestigator2 = book.getBookAuthorList().stream().
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("ByynyAAA")).collect(Collectors.toList()).get(0);
        assertEquals(bookInvestigator2.getRole(), "Investigator");
        assertEquals(bookInvestigator2.getAuthor().getInitials(), "R");

        List <Isbn> isbnList = book.getIsbnList().stream().
                filter(item -> item.getIsbn().equals("123-456")).collect(Collectors.toList());
        assertEquals(isbnList.size(),1);

        BookELocationID bookELocationID = book.getBookELocationIDList().stream().
                filter(item -> item.geteLocationID().equals("S1878-8750(20)32273-7")).collect(Collectors.toList()).get(0);
        assertEquals(bookELocationID.getBook().getPmid(),"92349121");
        assertEquals(bookELocationID.geteIdType(),"pii");

        LocationLabel locationLabel = book.getLocationLabelList().stream().
                filter(item -> item.getLocation().equals("chapterValue")).collect(Collectors.toList()).get(0);
        assertEquals(locationLabel.getType(), "chapter");

        Set <Language> languageList = book.getLanguageList();
        assertEquals(languageList.size(), 2);

        PublicationType publicationType = book.getPublicationTypeList().iterator().next();
        assertEquals(publicationType.getUI(), "D016428");
        assertEquals(publicationType.getName(), "Journal Article");

        Set<Section> sectionList = book.getSectionList();
        Section section1 = sectionList.stream().
                filter(item -> item.getTitle().equals("TitleSection1 ")).collect(Collectors.toList()).get(0);
        assertEquals(sectionList.size(), 3);
        assertEquals(section1.getLocationLabel(), "chapter:chapterValueSection ");

        Set <Keyword> keywordList = book.getKeywordList();
        Keyword keyword = keywordList.stream().
                filter(item -> item.getKeyword().equals("Keyword1")).collect(Collectors.toList()).get(0);
        assertEquals(keyword.getOwner(), "NLM");
        assertEquals(keyword.getMajorTopicYN(), "Y");
        assertEquals(keywordList.size(), 2);

        List <BookGrant> bookGrantList = book.getBookGrantList();
        BookGrant grant1 = bookGrantList.stream().
                filter(item -> item.getGrantID().equals("090532")).collect(Collectors.toList()).get(0);
        BookGrant grant2 = bookGrantList.stream().
                filter(item -> item.getGrantID().equals("090532/Z/09/Z")).collect(Collectors.toList()).get(0);
        assertEquals(grant1.getGrantAgency().getCountry(), "United Kingdom");
        assertEquals(grant1.getGrantAgency().getAgency(), "Wellcome Trust");
        assertEquals(grant2.getGrantAgency().getCountry(), "United Kingdom1");
        assertEquals(grant2.getGrantAgency().getAgency(), "Wellcome Trust1");

        Set <Item> itemList = book.getItemList();
        Item item1 = itemList.stream().
                filter(item -> item.getItem().equals("Item1")).collect(Collectors.toList()).get(0);
        Item item2 = itemList.stream().
                filter(item -> item.getItem().equals("Item2")).collect(Collectors.toList()).get(0);
        Item item3 = itemList.stream().
                filter(item -> item.getItem().equals("Item3")).collect(Collectors.toList()).get(0);
        assertEquals(item1.getItem(), "Item1");
        assertEquals(item2.getItem(), "Item2");
        assertEquals(item3.getItem(), "Item3");

        List <BookReference> bookReferenceList = book.getBookReferenceList();
        BookReference bookReference = bookReferenceList.stream().
                filter(item -> item.getTitle().equals("Reference")).collect(Collectors.toList()).get(0);
        assertTrue(bookReference.getCitation().contains("eldegiorgis M, Woodward M. The impact of hypertensio"));
        ArticleID articleID = bookReference.getArticleIDList().iterator().next();
        assertEquals(articleID.getType(), "doi");
        assertEquals(articleID.getArticleId(), "10.1186/s12882-020-02151-7");

    }


    @Test
    void TestPubmedBook2() {
        Book book = (handlerContainer.getBookMainList().stream().
                filter(item -> item.getPmid().equals("92349121")).collect(Collectors.toList())).get(0);

        assertEquals(book.getPmid(), "92349121");
        assertEquals(book.getVersion(), "1");
        assertEquals(book.getPublisherList().get(0).getName(), "PublisherName");
        assertEquals(book.getPublisherList().get(0).getLocation(), "Location");
        assertEquals(book.getBookTitle(), " Clinical Policy: Critical Issues in the Management of Adult Patients Presenting to the Emergency Department With Community-Acquired Pneumonia. ");
        assertEquals(book.getPubDate(), "2021-Jan");
        assertEquals(book.getBeginningDate(), "2020-Jan");
        assertEquals(book.getEndingDate(), "2020-Jan");
        assertEquals(book.getVolume(), "145");
        assertEquals(book.getVolumeTitle(), "Book");
        assertEquals(book.getEdition(), "123");
        assertEquals(book.getCollectionTitle(), "Book of books");
        assertEquals(book.getMedium(), "hello");
        assertEquals(book.getReportNumber(), "66");
        assertEquals(book.getVernacularTitle(), "VernacularTitleValue");
        assertEquals(book.getStartPage(), "2 ");
        assertEquals(book.getEndPage(), "5");
        assertEquals(book.getMedlinePgn(), " 12-A ");
        assertTrue(book.getAbstractText().contains("Changes in DNA methylation, whether hypo- or hypermethylation"));
        assertTrue(book.getCopyrightInformation().contains("Copyright © 2012 Pathological Socie"));
        assertEquals(book.getContributionDate(), "2020-Jan");
        assertEquals(book.getDateRevised(), "2010-Jan");

        Set<ArticleID> articleIDSet  =   book.getArticleIDList();
        List<ArticleID> articleIDList = new ArrayList<>(articleIDSet);
        ArticleID articleID1 = articleIDList.stream().
                filter(item -> item.getArticleId().equals("33349374")).collect(Collectors.toList()).get(0);
        assertEquals(articleID1.getType(), "pubmed");

        BookAuthor bookAuthor = book.getBookAuthorList().stream().
                filter(item -> item.getRole().equals("Author")).
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("Smith")).collect(Collectors.toList()).get(0);
        assertEquals(bookAuthor.getRole(), "Author");
        assertEquals(bookAuthor.getAuthor().getInitials(), "MD");
        assertEquals(bookAuthor.getAuthor().getForeName(), "Michael D");
        assertEquals(bookAuthor.getBook().getPmid(), "92349121");

        BookAuthor bookInvestigator = book.getBookAuthorList().stream().
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("Wolf")).collect(Collectors.toList()).get(0);
        assertEquals(bookInvestigator.getRole(), "Investigator");
        assertEquals(bookInvestigator.getAuthor().getInitials(), "SJ");
        assertEquals(bookInvestigator.getAuthor().getForeName(), "Stephen J");
        assertEquals(bookInvestigator.getBook().getPmid(), "92349121");
        BookAuthor bookInvestigator2 = book.getBookAuthorList().stream().
                filter(item -> item.getAuthor().getLastName()!=null).
                filter(item -> item.getAuthor().getLastName().equals("ByynyAAA")).collect(Collectors.toList()).get(0);
        assertEquals(bookInvestigator2.getRole(), "Investigator");
        assertEquals(bookInvestigator2.getAuthor().getInitials(), "R");

        List <Isbn> isbnList = book.getIsbnList().stream().
                filter(item -> item.getIsbn().equals("123-456")).collect(Collectors.toList());
        assertEquals(isbnList.size(),1);

        BookELocationID bookELocationID = book.getBookELocationIDList().stream().
                filter(item -> item.geteLocationID().equals("S1878-8750(20)32273-7")).collect(Collectors.toList()).get(0);
        assertEquals(bookELocationID.getBook().getPmid(),"92349121");
        assertEquals(bookELocationID.geteIdType(),"pii");

        LocationLabel locationLabel = book.getLocationLabelList().stream().
                filter(item -> item.getLocation().equals("chapterValue")).collect(Collectors.toList()).get(0);
        assertEquals(locationLabel.getType(), "chapter");

        Set <Language> languageList = book.getLanguageList();
        assertEquals(languageList.size(), 2);

        PublicationType publicationType = book.getPublicationTypeList().iterator().next();
        assertEquals(publicationType.getUI(), "D016428");
        assertEquals(publicationType.getName(), "Journal Article");

        Set<Section> sectionList = book.getSectionList();
        Section section1 = sectionList.stream().
                filter(item -> item.getTitle().equals("TitleSection1 ")).collect(Collectors.toList()).get(0);
        assertEquals(sectionList.size(), 3);
        assertEquals(section1.getLocationLabel(), "chapter:chapterValueSection ");

        Set <Keyword> keywordList = book.getKeywordList();
        Keyword keyword = keywordList.stream().
                filter(item -> item.getKeyword().equals("Keyword1")).collect(Collectors.toList()).get(0);
        assertEquals(keyword.getOwner(), "NLM");
        assertEquals(keyword.getMajorTopicYN(), "Y");
        assertEquals(keywordList.size(), 2);

        List <BookGrant> bookGrantList = book.getBookGrantList();
        BookGrant grant1 = bookGrantList.stream().
                filter(item -> item.getGrantID().equals("090532")).collect(Collectors.toList()).get(0);
        BookGrant grant2 = bookGrantList.stream().
                filter(item -> item.getGrantID().equals("090532/Z/09/Z")).collect(Collectors.toList()).get(0);
        assertEquals(grant1.getGrantAgency().getCountry(), "United Kingdom");
        assertEquals(grant1.getGrantAgency().getAgency(), "Wellcome Trust");
        assertEquals(grant2.getGrantAgency().getCountry(), "United Kingdom1");
        assertEquals(grant2.getGrantAgency().getAgency(), "Wellcome Trust1");

        Set <Item> itemList = book.getItemList();
        Item item1 = itemList.stream().
                filter(item -> item.getItem().equals("Item1")).collect(Collectors.toList()).get(0);
        Item item2 = itemList.stream().
                filter(item -> item.getItem().equals("Item2")).collect(Collectors.toList()).get(0);
        Item item3 = itemList.stream().
                filter(item -> item.getItem().equals("Item3")).collect(Collectors.toList()).get(0);
        assertEquals(item1.getItem(), "Item1");
        assertEquals(item2.getItem(), "Item2");
        assertEquals(item3.getItem(), "Item3");

        List <BookReference> bookReferenceList = book.getBookReferenceList();
        BookReference bookReference = bookReferenceList.stream().
                filter(item -> item.getTitle().equals("Reference")).collect(Collectors.toList()).get(0);
        assertTrue(bookReference.getCitation().contains("eldegiorgis M, Woodward M. The impact of hypertensio"));
        ArticleID articleID = bookReference.getArticleIDList().iterator().next();
        assertEquals(articleID.getType(), "doi");
        assertEquals(articleID.getArticleId(), "10.1186/s12882-020-02151-7");

    }

}