package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.handler;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.DeleteCitation;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TestParsingPubmedArticleSet {
    private HandlerContainer handlerContainer;

    @Autowired
    private ParserService parserService;

    @BeforeAll
    public void setUp() {
        handlerContainer = parserService.parse("src/test/java/com/pubmedknowledgegraph/master/test_files/PubmedArticleSet.xml");
    }

    @Test
    void TestSizeLists() {
        assertEquals(handlerContainer.getArticleMainList().size(), 23);
    }



    @Test
    void TestArticle() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0);
        assertEquals(article.getPmid(), "33348493");
        assertEquals(article.getVersion(), "1");
        assertEquals(article.getStatus(), "In-Data-Review");
        assertEquals(article.getOwner(), "NLM");
        assertEquals(article.getDateRevised(), "2020-12-22");
        assertEquals(article.getPubModel(), "Print");
        assertEquals(article.getArticleTitle(), "In Reply to the Letter to the Editor Regarding \"Decompressive Craniectomy for Patients with Traumatic Brain Injury: A Pooled Analysis of Randomized Controlled Trials\".");
        assertNull(article.getStartPage());
        assertNull(article.getEndPage());
        assertEquals(article.getMedlinePgn(), "512");
        assertNull(article.getAbstractText());
        assertNull(article.getCopyrightInformation());
        assertNull(article.getVernacularTitle());
        assertNull(article.getCoiStatement());
        assertEquals(article.getPublicationStatus(), "ppublish");
    }



    @Test
    void TestArticleJournal() {
        ArticleJournal articleJournal = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleJournalList().get(0);
       assertEquals(articleJournal.getJournal().getISSN(), "1878-8769");
       assertEquals(articleJournal.getJournal().getISSNLinking(), "1878-8750");
       assertEquals(articleJournal.getArticle().getPmid(), "33348493");
       assertEquals(articleJournal.getCitedMedium(), "Internet");
       assertNull(articleJournal.getIssue());
       assertEquals(articleJournal.getPubDate(), "2021-Jan");
       assertEquals(articleJournal.getVolume(), "145");
    }


    @Test
    void TestJournal() {
        Journal journal = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleJournalList().get(0).getJournal();
        assertEquals(journal.getISSN(), "1878-8769");
        assertEquals(journal.getISSNLinking(), "1878-8750");
        assertEquals(journal.getISSNType(), "Electronic");
        assertEquals(journal.getCountry(), "United States");
        assertEquals(journal.getTitle(), "World neurosurgery");
        assertEquals(journal.getISOAbbreviation(), "World Neurosurg");
        assertEquals(journal.getNlmUniqueID(), "101528275");
    }



    @Test
    void TestArticleAuthor() {
        List <ArticleAuthor> articleAuthorList = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleAuthorList();
        ArticleAuthor author1 = articleAuthorList.get(1);
        ArticleAuthor author2 = articleAuthorList.get(2);
        assertEquals(author1.getAuthor().getLastName(), "Lu");
        assertEquals(author1.getArticle().getPmid(), "33348493");
        assertNull(author1.getEqualContrib());
        assertEquals(author1.getRole(), "Author");
        assertEquals(author2.getAuthor().getLastName(), "Zhang");
        assertEquals(author2.getArticle().getPmid(), "33348493");
        assertNull(author2.getEqualContrib());
        assertEquals(author2.getRole(), "Author");
    }


    @Test
    void TestAuthor() {
        List <ArticleAuthor> articleAuthorList = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleAuthorList();
        Author author1 = articleAuthorList.get(1).getAuthor();
        Author author2 = articleAuthorList.get(2).getAuthor();
        assertEquals(author1.getLastName(), "Lu");
        assertEquals(author1.getAffiliationList().iterator().next().getAffiliation(), "Department of Preventive Medicine, Medical College of Yangzhou University, Yangzhou University, Yangzhou, China.");
        assertNull(author1.getCollectiveName());
        assertEquals(author1.getForeName(), "Guangyu");
        assertNull(author1.getIdentifierList());
        assertEquals(author1.getInitials(), "G");
        assertNull(author1.getSuffix());
        assertEquals(author2.getLastName(), "Zhang");
        assertEquals(author2.getAffiliationList().iterator().next().getAffiliation(), "Neuro-intensive Care Unit, Clinical Medical College of Yangzhou University, Yangzhou University, Yangzhou, China.");
        assertNull(author2.getCollectiveName());
        assertEquals(author2.getForeName(), "Jun");
        assertNull(author2.getIdentifierList());
        assertEquals(author2.getInitials(), "J");
        assertNull(author2.getSuffix());
    }


    @Test
    void TestAffiliation() {
        List <ArticleAuthor> articleAuthorList = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleAuthorList();
        Set<Affiliation> affiliationList = articleAuthorList.get(1).getAuthor().getAffiliationList();
        assertEquals(affiliationList.size(), 1);
        Affiliation affiliation = affiliationList.iterator().next();
        assertEquals(affiliation.getAffiliation(), "Department of Preventive Medicine, Medical College of Yangzhou University, Yangzhou University, Yangzhou, China.");
    }



    @Test
    void TestAffiliation2() {
        List <ArticleAuthor> articleAuthorList = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("31897414")).collect(Collectors.toList())).get(0).getArticleAuthorList();
        Set<Affiliation> affiliationList = new HashSet<>();
        for (ArticleAuthor articleAuthor : articleAuthorList) {
            if (articleAuthor.getAuthor().getLastName().equals("Solomon")) {
                affiliationList = articleAuthor.getAuthor().getAffiliationList();
            }
        }
        assertEquals(affiliationList.size(), 3);
        Identifier identifier = affiliationList.iterator().next().getIdentifierList().iterator().next();
        assertEquals(identifier.getIdentifier(), "0000 0001 2171 9311");
        assertEquals(identifier.getSource(), "ISNI");
    }



    @Test
    void TestNull() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0);
        assertNull(article.getArticleGrantList());
        assertNull(article.getAccessionNumberList());
        assertNull(article.getArticleDateList());
        assertNull(article.getChemicalList());
        assertNull(article.getSupplMeshNameList());
        assertNull(article.getCommentsCorrectionsList());
        assertNull(article.getMeshHeadingList());
        assertNull(article.getOtherIDList());
        assertNull(article.getOtherAbstractList());
        assertNull(article.getKeywordList());
        assertNull(article.getGeneralNoteList());
        assertNull(article.getArticleObjectList());
        assertEquals(article.getArticleReferenceList().size(), 0);

    }

    @Test
    void TestELocationID() {
         ArticleELocationID articleELocationID = new ArrayList<>((handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleELocationIDList()).get(1);
        assertEquals(articleELocationID.getArticle().getPmid(), "33348493");
        assertEquals(articleELocationID.geteLocationID(), "10.1016/j.wneu.2020.10.081");
        assertEquals(articleELocationID.geteIdType(), "doi");
    }

    @Test
    void TestLanguage() {
        Set <Language> languages =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getLanguageList();
        assertEquals(languages.iterator().next().getLanguage(), "eng");

    }


    @Test
    void TestPublicationTypeList() {
        Set <PublicationType> publicationTypeList =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getPublicationTypeList();
        assertEquals(publicationTypeList.iterator().next().getName(), "Journal Article");
        assertEquals(publicationTypeList.iterator().next().getUI(), "D016428");
    }

    @Test
    void TestCitationSubset() {
        Set<CitationSubset> citationSubsetList =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getCitationSubsetList();
        assertEquals(new ArrayList<>(citationSubsetList).get(0).getCitationSubset(), "IM");
    }

    @Test
    void TestHistory() {
        Set<ArticleHistory> articleHistoryList  =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleHistoryList();
        assertEquals(new ArrayList<>(articleHistoryList).get(0).getArticle().getPmid(), "33348493");
        assertEquals(new ArrayList<>(articleHistoryList).get(2).getStatus(), "received");
        assertEquals(new ArrayList<>(articleHistoryList).get(2).getDate(), "2020-10-14");
        assertEquals(new ArrayList<>(articleHistoryList).get(0).getStatus(), "accepted");
        assertEquals(new ArrayList<>(articleHistoryList).get(0).getDate(), "2020-10-15");
        assertEquals(new ArrayList<>(articleHistoryList).get(3).getStatus(), "entrez");
        assertEquals(new ArrayList<>(articleHistoryList).get(3).getDate(), "2020-12-22|T1:3");
        assertEquals(new ArrayList<>(articleHistoryList).get(1).getStatus(), "pubmed");
        assertEquals(new ArrayList<>(articleHistoryList).get(1).getDate(), "2020-12-23|T6:0");
        assertEquals(new ArrayList<>(articleHistoryList).get(4).getStatus(), "medline");
        assertEquals(new ArrayList<>(articleHistoryList).get(4).getDate(), "2020-12-23|T6:0");
    }

    @Test
    void TestArticleID() {
        Set <ArticleID> articleIDSet  =   (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348493")).collect(Collectors.toList())).get(0).getArticleIDList();
        List<ArticleID> articleIDList = new ArrayList<>(articleIDSet);
        ArticleID articleID1 = articleIDList.stream().
                filter(item -> item.getArticleId().equals("33348493")).collect(Collectors.toList()).get(0);
        ArticleID articleID2 = articleIDList.stream().
                filter(item -> item.getArticleId().equals("S1878-8750(20)32273-7")).collect(Collectors.toList()).get(0);
        ArticleID articleID3 = articleIDList.stream().
                filter(item -> item.getArticleId().equals("10.1016/j.wneu.2020.10.081")).collect(Collectors.toList()).get(0);
        assertEquals(articleID1.getType(), "pubmed");
        assertEquals(articleID2.getType(), "pii");
        assertEquals(articleID3.getType(), "doi");
    }


    @Test
    void TestAbstract() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348557")).collect(Collectors.toList())).get(0);
        assertTrue(article.getAbstractText().contains("In underwater wireless sensor networks (UWSNs), localization and time "));
    }

    @Test
    void TestAuthorIdentifier() {
        Author author = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348557")).collect(Collectors.toList())).get(0).getArticleAuthorList().
                stream().filter(item -> item.getAuthor().getLastName().equals("Otero")).collect(Collectors.toList()).get(0).getAuthor();
       assertEquals(author.getIdentifierList().iterator().next().getIdentifier(), "0000-0003-3042-4392");
       assertEquals(author.getIdentifierList().iterator().next().getSource(), "ORCID");
    }

    @Test
    void TestArticleDate() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348557")).collect(Collectors.toList())).get(0);
        assertEquals(article.getArticleDateList().get(0).getDate(), "2020-12-17");
        assertEquals(article.getArticleDateList().get(0).getDateType(), "Electronic");
    }

    @Test
    void TestCitationSubset2() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33348557")).collect(Collectors.toList())).get(0);
        CitationSubset citationSubset = new ArrayList<>(article.getCitationSubsetList()).get(0);
        assertEquals(citationSubset.getCitationSubset(), "IM");
    }


    @Test
    void  TestCommentsCorrections() {
        CommentsCorrections commentsCorrections = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("23096130")).collect(Collectors.toList())).get(0).getCommentsCorrectionsList().get(0);
        assertEquals(commentsCorrections.getNote(), "El-Bahwary, Mona [corrected to El-Bahrawy, Mona]");
        assertNull(commentsCorrections.getPmidRef());
        assertEquals(commentsCorrections.getRefSource(), "J Pathol. 2013 Dec;231(4):543");
        assertNull(commentsCorrections.getPmidRefVersion());
        assertEquals(commentsCorrections.getRefType(), "ErratumIn");
    }


    @Test
    void  TestAbstractTextWithTags() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349061")).collect(Collectors.toList())).get(0);
        assertTrue(article.getAbstractText().contains("Difficulty of implementing kinaesthetics in long-term care institutions - A multiple case-study Abstract. Background:"));
        OtherAbstract otherAbstract = article.getOtherAbstractList().get(0);
        assertEquals(otherAbstract.getAbstractText(), "Zusammenfassung. Hintergrund: Einrichtungen der stationären Langzeitpflege investieren seit Jahren Ressourcen in die Entwicklung der Kinästhetikkompetenz der Pflegenden. Aus aktuellen Studien geht hervor, dass die Implementierung, bzw. die nachhaltige Förderung der Kinästhetikkompetenz problematisch ist, vertiefte Erkenntnisse zu den Ursachen fehlen jedoch. Fragestellung: Welche Hemmnisse verhindern eine nachhaltige Implementierung von Kinästhetik in Einrichtungen der stationären Langzeitpflege? Methode: Es wurde eine Multiple Case-Study in drei Einrichtungen der deutschsprachigen Schweiz durchgeführt. Aus leitfadengestützten Interviews und (fallbezogener) Literatur zum externen Kontext wurden in den Within-Case-Analysen die Daten induktiv verdichtet und diese Ergebnisse in der Cross-Case-Synthese miteinander verglichen und abstrahierend zusammengeführt. Ergebnisse: Die Synthese zeigt, dass die Implementierung von Kinästhetik innerhalb der Einrichtung auf drei verschiedenen Ebenen – der Leitungs-, Pflegeteam- und Pflegeperson-Ebene – als auch durch externe Faktoren negativ beeinflusst werden kann. Schlussfolgerungen: In der Pflegepraxis und -wissenschaft sowie im Gesundheitswesen benötigt es ein grundlegendes Verständnis von Kinästhetik und wie dieses im Kontext des professionellen Pflegehandelns einzuordnen ist. Insbesondere Leitungs- und implementierungsverantwortliche Personen müssen mögliche Hemmnisse kennen, um entsprechende Strategien entwickeln zu können.");
    }


    @Test
    void TestCoistatement() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349332")).collect(Collectors.toList())).get(0);
        assertEquals(article.getCoiStatement(), "SB, SG, AR, AG, SO, PP, RR, SK, AI, VP, VA, MK, RD, PM No competing interests declared");
    }

    @Test
    void TestDataBank() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349332")).collect(Collectors.toList())).get(0);
        AccessionNumber accessionNumber = article.getAccessionNumberList().iterator().next();
        assertEquals(accessionNumber.getDataBankName(), "GEO");
        assertEquals(accessionNumber.getAccessionNumber(), "GSE139147");
    }


    @Test
    void TestInvestigators() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349374")).collect(Collectors.toList())).get(0);
        List<ArticleAuthor> articleInvestigators = article.getArticleAuthorList().stream().filter(item -> item.getRole().equals("Investigator"))
                .collect(Collectors.toList());
        ArticleAuthor articleAuthor1 = articleInvestigators.stream().filter(item -> item.getAuthor().getLastName().equals("Wolf"))
                .collect(Collectors.toList()).get(0);
        ArticleAuthor articleAuthor2 = articleInvestigators.stream().filter(item -> item.getAuthor().getLastName().equals("Carpenter"))
                .collect(Collectors.toList()).get(0);

        assertEquals(articleAuthor2.getRole(), "Investigator");
        assertEquals(articleAuthor2.getAuthor().getInitials(), "CR");
        assertEquals(articleAuthor2.getAuthor().getForeName(), "Christopher R");
        assertEquals(articleAuthor1.getRole(), "Investigator");
        assertEquals(articleAuthor1.getAuthor().getInitials(), "SJ");
        assertEquals(articleAuthor1.getAuthor().getForeName(), "Stephen J");
    }


    @Test
    void TestCollectiveName() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349374")).collect(Collectors.toList())).get(0);
        List<ArticleAuthor> articleInvestigators = article.getArticleAuthorList().stream().filter(item -> item.getAuthor().getCollectiveName()!=null)
                .collect(Collectors.toList());

        String s  = articleInvestigators.get(0).getAuthor().getCollectiveName();
        assertEquals(s, "American College of Emergency Physicians Clinical Policies Subcommittee (Writing Committee) on Community-Acquired Pneumonia");

    }


    @Test
    void TestReferenceList() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349232")).collect(Collectors.toList())).get(0);
        ArticleReference articleReference = article.getArticleReferenceList().get(0);

        assertEquals(articleReference.getCitation(), "Weldegiorgis M, Woodward M. The impact of hypertension on chronic kidney disease and end-stage renal disease is greater in men than women: a systematic review and meta-analysis. BMC Nephrol. 2020;21:506 https://doi.org/10.1186/s12882-020-02151-7 .");
        assertEquals(articleReference.getTitle(), "Reference");
        assertEquals(articleReference.getArticleIDList().iterator().next().getArticleId(), "10.1186/s12882-020-02151-7");
    }



    @Test
    void TestVernacularTitle() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("33349423")).collect(Collectors.toList())).get(0);
        assertEquals(article.getVernacularTitle(), "Actualisation 2020–2022 des recommandations françaises du Comité de cancérologie de l’AFU – Éditorial.");
    }

    @Test
    void TestChemical() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("4957")).collect(Collectors.toList())).get(0);
        Set <Chemical> chemicalSet = article.getChemicalList();
        List<Chemical> chemicalList = new ArrayList<>(chemicalSet);

        Chemical chemical1 = chemicalList.stream().
                filter(item -> item.getUI().equals("D000319")).collect(Collectors.toList()).get(0);
        Chemical chemical2 = chemicalList.stream().
                filter(item -> item.getUI().equals("D011412")).collect(Collectors.toList()).get(0);

        assertEquals(chemical1.getNameOfSubstance(), "Adrenergic beta-Antagonists");
        assertEquals(chemical1.getRegistryNumber(), "0");
        assertEquals(chemical2.getNameOfSubstance(), "Propanolamines");
        assertEquals(chemical2.getRegistryNumber(), "0");
    }



    @Test
    void TestOtherID() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("4957")).collect(Collectors.toList())).get(0);
        OtherID otherID = new ArrayList<>(article.getOtherIDList()).get(0);
        assertEquals(otherID.getOtherID(), "76181370");
        assertEquals(otherID.getSource(), "NASA");
    }



    @Test
    void TestOtherID2() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("21803848")).collect(Collectors.toList())).get(0);
        SupplMeshName supplMeshName = article.getSupplMeshNameList().iterator().next();
        assertEquals(supplMeshName.getType(), "Disease");
        assertEquals(supplMeshName.getName(), "Shwachman syndrome");
        assertEquals(supplMeshName.getUI(), "C537330");
    }



    @Test
    void MeshHeadingList() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("21803848")).collect(Collectors.toList())).get(0);
        Set <MeshHeading> setMeshHeading = article.getMeshHeadingList();
        List<MeshHeading> meshHeadingList = new ArrayList<>(setMeshHeading);

        MeshHeading meshHeading1 = meshHeadingList.stream().
                filter(item -> item.getUI().equals("Q000378")).collect(Collectors.toList()).get(0);
        MeshHeading meshHeading2 = meshHeadingList.stream().
                filter(item -> item.getUI().equals("D002460")).collect(Collectors.toList()).get(0);

        assertEquals(meshHeading1.getMajorTopicYN(), "N");
        assertEquals(meshHeading1.getName(), "metabolism");
        assertEquals(meshHeading2.getMajorTopicYN(), "N");
        assertEquals(meshHeading2.getName(), "Cell Line");
    }




    @Test
    void GrantList() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("21803848")).collect(Collectors.toList())).get(0);
        List <ArticleGrant> articleGrantList = article.getArticleGrantList();

        ArticleGrant articleGrant1 = articleGrantList.stream().
                filter(item -> item.getGrantID().equals("08-0183")).collect(Collectors.toList()).get(0);
        ArticleGrant articleGrant2 = articleGrantList.stream().
                filter(item -> item.getGrantID().equals("MC_U105115237")).collect(Collectors.toList()).get(0);

        assertEquals(articleGrant1.getGrantAgency().getAgency(), "Worldwide Cancer Research");
        assertEquals(articleGrant1.getGrantAgency().getCountry(), "United Kingdom");
        assertEquals(articleGrant1.getGrantAgency().getAcronym(), "AICR_");
        assertEquals(articleGrant2.getGrantAgency().getAgency(), "Medical Research Council");
        assertEquals(articleGrant2.getGrantAgency().getCountry(), "United Kingdom");
        assertEquals(articleGrant2.getGrantAgency().getAcronym(), "MRC_");
    }


    @Test
    void TestPersonalNameSubjectList() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("21927968")).collect(Collectors.toList())).get(0);
        List<ArticleAuthor> articleInvestigators = article.getArticleAuthorList().stream().filter(item -> item.getRole().equals("PersonalNameSubject"))
                .collect(Collectors.toList());
        ArticleAuthor articleAuthor1 = articleInvestigators.stream().filter(item -> item.getAuthor().getLastName().equals("Cottington"))
                .collect(Collectors.toList()).get(0);
        ArticleAuthor articleAuthor2 = articleInvestigators.stream().filter(item -> item.getAuthor().getLastName().equals("Riden"))
                .collect(Collectors.toList()).get(0);

        assertEquals(articleAuthor1.getRole(), "PersonalNameSubject");
        assertEquals(articleAuthor1.getAuthor().getInitials(), "GM");
        assertEquals(articleAuthor1.getAuthor().getForeName(), "Gordon M");
        assertEquals(articleAuthor2.getRole(), "PersonalNameSubject");
        assertEquals(articleAuthor2.getAuthor().getInitials(), "JM");
        assertEquals(articleAuthor2.getAuthor().getForeName(), "Jay M");
    }




    @Test
    void OtherAbstractMultiTag() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("23997785")).collect(Collectors.toList())).get(0);
        OtherAbstract otherAbstract = article.getOtherAbstractList().get(0);
        assertTrue(otherAbstract.getAbstractText().contains("HISTORIQUE|null|La maladie à méningocoque du sérogroupe"));
        String abstractText = article.getAbstractText();
        assertEquals(abstractText, "BACKGROUND|BACKGROUND|Serogroup C meningococcal disease has been endemic in Canada since the early 1990s, with periods of hyperendemic disease documented in the past two decades. The present study characterized invasive serogroup C meningococci in Canada during the period from 2002 to 2009.\n" +
                "METHODS|METHODS|Serogroup C meningococci were serotyped using monoclonal antibodies. Their clonal types were identified by either multilocus enzyme electrophoresis or multilocus sequence typing.\n" +
                "RESULTS|RESULTS|The number of invasive serogroup C Neisseria meningitidis isolates received at the National Microbiology Laboratory (Winnipeg, Manitoba) for characterization has dropped from a high of 173 isolates in 2001 to just 17 in 2009, possibly related to the introduction of the serogroup C meningococcal conjugate vaccine. Before 2006, 80% to 95% of all invasive serogroup C meningococci belonged to the electrophoreic type (ET)-15 clonal type, and the ET-37 (but not ET-15) type only accounted for up to 5% of all isolates. However, beginning in 2006, the percentage of the ET-15 clonal type decreased while the ET-37 (but not ET-15) type increased from 27% in 2006 to 52% in 2009. The percentage of invasive serogroup C isolates not belonging to either ET-15 or ET-37 also increased. Most ET-15 isolates expressed the antigenic formula of C:2a:P1.7,1 or C:2a:P1.5. In contrast, the ET-37 (but not ET-15) isolates mostly expressed the antigens of C:2a:P1.5,2 or C:2a:P1.2.\n" +
                "CONCLUSION|CONCLUSIONS|A shift in the antigenic and clonal type of invasive serogroup C meningococi was noted. This finding suggests vigilance in the surveillance of meningoccocal disease is warranted.");
       }


    @Test
    void TestCopyrightInformation() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("10662553")).collect(Collectors.toList())).get(0);
        assertEquals(article.getCopyrightInformation(), "Copyright 2000 Academic Press.");
    }


    @Test
    void TestGeneralNote() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("1697029")).collect(Collectors.toList())).get(0);
        GeneralNote generalNote1 = article.getGeneralNoteList().stream().filter(item -> item.getNote().equals("12 refs."))
                .collect(Collectors.toList()).get(0);
        GeneralNote generalNote2 = article.getGeneralNoteList().stream().filter(item -> item.getNote().equals("KIE Bib: resuscitation orders"))
                .collect(Collectors.toList()).get(0);

        assertEquals(generalNote1.getOwner(), "KIE");
        assertEquals(generalNote2.getOwner(), "KIE");
    }



    @Test
    void TestArticleAuthor2() {
            Article article = (handlerContainer.getArticleMainList().stream().
            filter(item -> item.getPmid().equals("31897414")).collect(Collectors.toList())).get(0);

        ArticleAuthor author = article.getArticleAuthorList().stream().filter(item -> item.getAuthor().getLastName().equals("Saravanan"))
                .collect(Collectors.toList()).get(0);
        assertEquals(author.getRole(), "Author");
        assertEquals(author.getAuthor().getForeName(), "S");
        assertEquals(author.getAuthor().getInitials(), "S");
        assertEquals(author.getAuthor().getAffiliationList().iterator().next().getAffiliation(), "3YR Gaitonde Centre for AIDS Research and Education, Chennai, India.");
    }


    @Test
    void TestGeneSymbol() {
        GeneSymbol geneSymbol =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("8159434")).collect(Collectors.toList())).get(0).getGeneSymbolList().stream()
                .filter(item -> item.getGeneSymbol().equals("c-fos")).iterator().next();
        assertTrue(geneSymbol !=null);
     }



    @Test
    void TestReferenceListRecursive() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("31932710")).collect(Collectors.toList())).get(0);
        assertEquals(article.getArticleReferenceList().get(0).getArticleIDList().iterator().next().getType(), "doi");
    }


    @Test
    void SpaceFlightMission() {
        SpaceFlightMission spaceFlightMission =  (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("111111")).collect(Collectors.toList())).get(0).getSpaceFlightMissionList().iterator().next();

        assertEquals(spaceFlightMission.getSpaceFlightMission(),"False Mission");
    }

    @Test
    void TestObjectList() {
        Article article = (handlerContainer.getArticleMainList().stream().
                filter(item -> item.getPmid().equals("111111")).collect(Collectors.toList())).get(0);
        List <ArticleObject> articleObjectList = article.getArticleObjectList();
        ArticleObject object = articleObjectList.stream().filter(item -> item.getText().equals("hamster"))
                .collect(Collectors.toList()).get(0);
        assertEquals(object.getText(), "hamster");
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
        assertEquals(new ArrayList<>(book.getBookHistoryList()).get(0).getDate(), "2020-12-23|T6:0");
        assertEquals(new ArrayList<>(book.getBookHistoryList()).get(0).getStatus(), "medline");

        Set <ArticleID> articleIDSet  =   book.getArticleIDList();
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

        List <BookObject> bookObjectList = book.getBookObjectList();
        assertEquals(bookObjectList.size(), 3);

    }

    @Test
    void TestDeleteCitation() {
        List<DeleteCitation> deleteCitationList = handlerContainer.getDeleteCitationList();
        assertTrue(deleteCitationList.stream().filter(item -> item.getPmid().equals("32848134")).collect(Collectors.toList()).size()!=0);
        assertTrue(deleteCitationList.stream().filter(item -> item.getPmid().equals("33412581")).collect(Collectors.toList()).size()!=0);
        assertTrue(deleteCitationList.stream().filter(item -> item.getPmid().equals("33417493")).collect(Collectors.toList()).size()!=0);
        assertTrue(deleteCitationList.stream().filter(item -> item.getPmid().equals("33412580")).collect(Collectors.toList()).size()!=0);
        assertTrue(deleteCitationList.stream().filter(item -> item.getPmid().equals("000false")).collect(Collectors.toList()).size()==0);
    }


}