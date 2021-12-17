package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.handler;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.Date;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class Handler extends DefaultHandler {

    private List<Article> articleMainList;
    private List<Book> bookMainList;
    private List<DeleteCitation> deleteCitationList;

    private Map<String, Journal> journalMainList;
    private Map<String, Language> languageMainList;
    private Map<String, SpaceFlightMission> spaceFlightMissionMainList;
    private Map<String, GeneSymbol> geneSymbolMainList;
    private Map<String, AccessionNumber> accessionNumberMainList;
    private Map<Integer, GrantAgency> grantAgencyMainList;
    private Map<String, PublicationType> publicationTypeMainList;
    private Map<String, Chemical> chemicalMainList;
    private Map<String, SupplMeshName> supplMeshNameMainList;
    private Map<String, MeshHeading> meshHeadingMainList;
    private Map<String, Keyword> keywordMainList;
    private Map<Long, Author> authorMainList;
    private Map<Long, Affiliation> affiliationMainList;
    private Map<String, Identifier> identifierMainList;
    private Map<Integer, ArticleID> articleIDMainList;
    private Map<Integer, Item> itemMainList;
    private Map<Integer, Publisher> publisherMainList;

    private Article article;
    private ArticleJournal articleJournal;
    private Journal journal;
    private List<ArticleGrant> articleGrantList;
    private ArticleGrant articleGrant;
    private GrantAgency grantAgency;

    private Set<ArticleELocationID> articleELocationIDList;
    private ArticleELocationID articleELocationID;
    private List<ArticleDate> articleDateList;
    private ArticleDate articleDate;
    private Set<CitationSubset> citationSubsetList;
    private CitationSubset citationSubset;
    private List<CommentsCorrections> commentsCorrectionsList;
    private CommentsCorrections commentsCorrections;
    private Set<OtherID> otherIDList;
    private OtherID otherID;
    private List<OtherAbstract> otherAbstractList;
    private OtherAbstract otherAbstract;
    private Set<GeneralNote> generalNoteList;
    private GeneralNote generalNote;

    private Set<ArticleHistory> articleHistoryList;
    private ArticleHistory articleHistory;
    private Set<ArticleID> articleIDList;
    private ArticleID articleID;
    private List<ArticleObject> articleObjectList;
    private ArticleObject articleObject;
    private List<ArticleReference> articleReferenceList;
    private ArticleReference articleReference;

    private Set<Language> languageList;
    private Language language;
    private Set<SpaceFlightMission> spaceFlightMissionList;
    private SpaceFlightMission spaceFlightMission;
    private Set<GeneSymbol> geneSymbolList;
    private GeneSymbol geneSymbol;
    private Set<AccessionNumber> accessionNumberList;
    private AccessionNumber accessionNumber;
    private Set<PublicationType> publicationTypeList;
    private PublicationType publicationType;
    private Set<Chemical> chemicalList;
    private Chemical chemical;
    private Set<SupplMeshName> supplMeshNameList;
    private SupplMeshName supplMeshName;
    private Set<MeshHeading> meshHeadingList;
    private MeshHeading meshHeading;
    private Set<Keyword> keywordList;
    private Keyword keyword;

    private Book book;
    private Publisher publisher;
    private Title title;
    private Set<Isbn> isbnList;
    private Isbn isbn;
    private Set<BookELocationID> bookELocationIDList;
    private BookELocationID bookELocationID;
    private Set<LocationLabel> locationLabelList;
    private LocationLabel locationLabel;
    private Set<Section> sectionList;
    private List<BookGrant> bookGrantList;
    private BookGrant bookGrant;
    private Set<Item> itemList;
    private Item item;
    private List<BookReference> bookReferenceList;
    private BookReference bookReference;

    private Set<BookHistory> bookHistoryList;
    private BookHistory bookHistory;
    private List<BookObject> bookObjectList;
    private BookObject bookObject;

    private Map<Integer, ArticleAuthor> articleAuthorList;
    private ArticleAuthor articleAuthor;
    private Map<Integer, BookAuthor> bookAuthorList;
    private BookAuthor bookAuthor;
    private Author author;
    private Set<Affiliation> affiliationList;
    private Affiliation affiliation;
    private Set<Identifier> identifierList;
    private Identifier identifier;

    private Date date;
    private Abstract anAbstract;
    private List<AbstractText> abstractTextList;
    private AbstractText abstractText;
    private String authorRole;
    private String keywordListOwner;
    private String dataBankName;
    private String referenceTitle;
    private String itemType;
    private String articleObjectType;
    private String sectionTitle;
    private String sectionLocation;
    private DeleteCitation deleteCitation;

    private String ancestorTag = "";
    private String mainTag;

    private final StringBuilder stringBuilder;

    public Handler() {
        stringBuilder = new StringBuilder();
        initMainLists();
    }

    private void initMainLists() {
        articleMainList = new ArrayList<>();
        bookMainList = new ArrayList<>();
        deleteCitationList = new ArrayList<>();

        journalMainList = new HashMap<>();
        languageMainList = new HashMap<>();
        spaceFlightMissionMainList = new HashMap<>();
        geneSymbolMainList = new HashMap<>();
        accessionNumberMainList = new HashMap<>();
        grantAgencyMainList = new HashMap<>();
        publicationTypeMainList = new HashMap<>();
        chemicalMainList = new HashMap<>();
        supplMeshNameMainList = new HashMap<>();
        meshHeadingMainList = new HashMap<>();
        keywordMainList = new HashMap<>();
        authorMainList = new HashMap<>();
        affiliationMainList = new HashMap<>();
        identifierMainList = new HashMap<>();
        articleIDMainList = new HashMap<>();
        itemMainList = new HashMap<>();
        publisherMainList = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        switch (qName) {
            case "b":
            case "i":
            case "sup":
            case "sub":
            case "u":
                return;
        }

        stringBuilder.setLength(0);

        switch (qName) {
            case "PubmedArticle":
                mainTag = "article";
                article = new Article();
                articleAuthorList = new HashMap<>();
                return;
            case "MedlineCitation":
                ancestorTag = "MedlineCitation";
                article.setOwner(attributes.getValue("Owner"));
                article.setStatus(attributes.getValue("Status"));
                if (attributes.getValue("VersionDate") != null) {
                    article.setVersionDate(attributes.getValue("VersionDate"));
                }
                if (attributes.getValue("IndexingMethod") != null) {
                    article.setIndexingMethod(attributes.getValue("IndexingMethod"));
                }
                citationSubsetList = new HashSet<>();
                otherIDList = new HashSet<>();
                otherAbstractList = new ArrayList<>();
                keywordList = new HashSet<>();
                spaceFlightMissionList = new HashSet<>();
                generalNoteList = new HashSet<>();
                return;
            case "PMID":
                if (mainTag.equals("article")) {
                    if (ancestorTag.equals("MedlineCitation")) {
                        article.setVersion(attributes.getValue("Version"));
                    }
                    if (ancestorTag.equals("CommentsCorrectionsList")) {
                        commentsCorrections.setPmidRefVersion(attributes.getValue("Version"));
                    }
                }
                if (mainTag.equals("book")) {
                    book.setVersion(attributes.getValue("Version"));
                }
                if (mainTag.equals("DeleteCitation")) {
                    deleteCitation = new DeleteCitation();
                    deleteCitation.setVersion(attributes.getValue("Version"));
                }
                return;
            case "DateCompleted":
            case "DateRevised":
            case "PubDate":
            case "BeginningDate":
            case "EndingDate":
            case "ContributionDate":
                date = new Date();
                return;
            case "Article":
                articleELocationIDList = new HashSet<>();
                languageList = new HashSet<>();
                articleDateList = new ArrayList<>();
                article.setPubModel(attributes.getValue("PubModel"));
                return;
            case "Journal":
                ancestorTag = "Journal";
                journal = new Journal();
                articleJournal = new ArticleJournal();
                return;
            case "ISSN":
                journal.setISSNType(attributes.getValue("IssnType"));
                return;
            case "JournalIssue":
                articleJournal.setCitedMedium(attributes.getValue("CitedMedium"));
                return;
            case "ELocationID":
                if (mainTag.equals("article")) {
                    articleELocationID = new ArticleELocationID();
                    articleELocationID.seteIdType(attributes.getValue("EIdType"));
                }
                if (mainTag.equals("book")) {
                    bookELocationID = new BookELocationID();
                    bookELocationID.seteIdType(attributes.getValue("EIdType"));
                }
            case "Abstract":
                anAbstract = new Abstract();
                abstractTextList = new ArrayList<>();
                return;
            case "AbstractText":
                abstractText = new AbstractText();
                if (attributes.getValue("Label") != null) {
                    abstractText.setLabel(attributes.getValue("Label"));
                }
                if (attributes.getValue("NlmCategory") != null) {
                    abstractText.setNlmCategory(attributes.getValue("NlmCategory"));
                }
                return;
            case "AuthorList":
                if (attributes.getValue("Type") != null) {
                    authorRole = attributes.getValue("Type");
                } else {
                    authorRole = "Author";
                }
                return;
            case "Author":
                author = new Author();
                affiliationList = new HashSet<>();
                identifierList = new HashSet<>();
                if (mainTag.equals("article")) {
                    articleAuthor = new ArticleAuthor();
                    if (attributes.getValue("EqualContrib") != null) {
                        articleAuthor.setEqualContrib(attributes.getValue("EqualContrib"));
                    }
                }
                if (mainTag.equals("book")) {
                    bookAuthor = new BookAuthor();
                    if (attributes.getValue("EqualContrib") != null) {
                        bookAuthor.setEqualContrib(attributes.getValue("EqualContrib"));
                    }
                }
                return;
            case "Identifier":
                identifier = new Identifier();
                identifier.setSource(attributes.getValue("Source"));
                return;
            case "AffiliationInfo":
                if (author.getIdentifierList() == null && !identifierList.isEmpty()) {
                    author.setIdentifierList(identifierList);
                }
                identifierList = new HashSet<>();
                return;
            case "Affiliation":
                affiliation = new Affiliation();
                return;
            case "Language":
                language = new Language();
                return;
            case "DataBankList":
                accessionNumberList = new HashSet<>();
                return;
            case "DataBankName":
                return;
            case "AccessionNumber":
                accessionNumber = new AccessionNumber();
                return;
            case "GrantList":
                if (mainTag.equals("article")) {
                    articleGrantList = new ArrayList<>();
                }
                if (mainTag.equals("book")) {
                    bookGrantList = new ArrayList<>();
                }
                return;
            case "Grant":
                ancestorTag = "Grant";
                grantAgency = new GrantAgency();
                if (mainTag.equals("article")) {
                    articleGrant = new ArticleGrant();
                }
                if (mainTag.equals("book")) {
                    bookGrant = new BookGrant();
                }
                return;
            case "PublicationTypeList":
                publicationTypeList = new HashSet<>();
                return;
            case "PublicationType":
                publicationType = new PublicationType();
                publicationType.setUI(attributes.getValue("UI"));
                return;
            case "ArticleDate":
                articleDate = new ArticleDate();
                articleDate.setDateType(attributes.getValue("DateType"));
                return;
            case "MedlineJournalInfo":
                ancestorTag = "MedlineJournalInfo";
                return;
            case "ChemicalList":
                chemicalList = new HashSet<>();
                return;
            case "Chemical":
                chemical = new Chemical();
                return;
            case "RegistryNumber":
            case "NameOfSubstance":
                chemical.setUI(attributes.getValue("UI"));
                return;
            case "SupplMeshList":
                supplMeshNameList = new HashSet<>();
                return;
            case "SupplMeshName":
                supplMeshName = new SupplMeshName();
                supplMeshName.setType(attributes.getValue("Type"));
                supplMeshName.setUI(attributes.getValue("UI"));
                return;
            case "CitationSubset":
                citationSubset = new CitationSubset();
                return;
            case "CommentsCorrectionsList":
                ancestorTag = "CommentsCorrectionsList";
                commentsCorrectionsList = new ArrayList<>();
                return;
            case "CommentsCorrections":
                commentsCorrections = new CommentsCorrections();
                commentsCorrections.setRefType(attributes.getValue("RefType"));
                return;
            case "GeneSymbolList":
                geneSymbolList = new HashSet<>();
                return;
            case "GeneSymbol":
                geneSymbol = new GeneSymbol();
                return;
            case "MeshHeadingList":
                meshHeadingList = new HashSet<>();
                return;
            case "DescriptorName":
                meshHeading = new MeshHeading();
                if (attributes.getValue("Type") != null) {
                    meshHeading.setType(attributes.getValue("Type"));
                }
                meshHeading.setMajorTopicYN(attributes.getValue("MajorTopicYN"));
                meshHeading.setUI(attributes.getValue("UI"));
                meshHeading.setNameType("DescriptorName");
                return;
            case "QualifierName":
                meshHeading = new MeshHeading();
                meshHeading.setMajorTopicYN(attributes.getValue("MajorTopicYN"));
                meshHeading.setUI(attributes.getValue("UI"));
                meshHeading.setNameType("QualifierName");
                return;
            case "PersonalNameSubjectList":
                authorRole = "PersonalNameSubject";
                return;
            case "PersonalNameSubject":
                articleAuthor = new ArticleAuthor();
                author = new Author();
                return;
            case "OtherID":
                otherID = new OtherID();
                otherID.setSource(attributes.getValue("Source"));
                return;
            case "OtherAbstract":
                otherAbstract = new OtherAbstract();
                anAbstract = new Abstract();
                abstractTextList = new ArrayList<>();
                otherAbstract.setType(attributes.getValue("Type"));
                language = new Language();
                language.setLanguage(attributes.getValue("Language"));
                if (!languageMainList.containsKey(language.getLanguage())) {
                    languageMainList.put(language.getLanguage(), language);
                    otherAbstract.setLanguage(language);
                } else {
                    otherAbstract.setLanguage(languageMainList.get(language.getLanguage()));
                }
                return;
            case "KeywordList":
                keywordListOwner = attributes.getValue("Owner");
                return;
            case "Keyword":
                keyword = new Keyword();
                keyword.setOwner(keywordListOwner);
                keyword.setMajorTopicYN(attributes.getValue("MajorTopicYN"));
                return;
            case "SpaceFlightMission":
                spaceFlightMission = new SpaceFlightMission();
                return;
            case "InvestigatorList":
                authorRole = "Investigator";
                return;
            case "Investigator":
                author = new Author();
                if (mainTag.equals("article")) {
                    articleAuthor = new ArticleAuthor();
                }
                if (mainTag.equals("book")) {
                    bookAuthor = new BookAuthor();
                }
                return;
            case "GeneralNote":
                generalNote = new GeneralNote();
                generalNote.setOwner(attributes.getValue("Owner"));
                return;
            case "PubmedData":
                articleReferenceList = new ArrayList<>();
                return;
            case "History":
                if (mainTag.equals("article")) {
                    articleHistoryList = new HashSet<>();
                }
                if (mainTag.equals("book")) {
                    bookHistoryList = new HashSet<>();
                }
                return;
            case "PubMedPubDate":
                if (mainTag.equals("article")) {
                    articleHistory = new ArticleHistory();
                    articleHistory.setStatus(attributes.getValue("PubStatus"));
                }
                if (mainTag.equals("book")) {
                    bookHistory = new BookHistory();
                    bookHistory.setStatus(attributes.getValue("PubStatus"));
                }
                date = new Date();
                return;
            case "ArticleIdList":
                articleIDList = new HashSet<>();
                return;
            case "ArticleId":
                articleID = new ArticleID();
                articleID.setType(attributes.getValue("IdType"));
                return;
            case "ObjectList":
                if (mainTag.equals("article")) {
                    articleObjectList = new ArrayList<>();
                }
                if (mainTag.equals("book")) {
                    bookObjectList = new ArrayList<>();
                }
                return;
            case "Object":
                articleObjectType = attributes.getValue("Type");
                return;
            case "Param":
                if (mainTag.equals("article")) {
                    articleObject = new ArticleObject();
                    articleObject.setName(attributes.getValue("Name"));
                }
                if (mainTag.equals("book")) {
                    bookObject = new BookObject();
                    bookObject.setName(attributes.getValue("Name"));
                }
                return;
            case "ReferenceList":
                ancestorTag = "ReferenceList";
                return;
            case "Reference":
                if (mainTag.equals("article")) {
                    articleReference = new ArticleReference();
                }
                if (mainTag.equals("book")) {
                    bookReference = new BookReference();
                }
                articleIDList = new HashSet<>();
                return;
            case "BookDocument":
                mainTag = "book";
                book = new Book();
                bookAuthorList = new HashMap<>();
                isbnList = new HashSet<>();
                locationLabelList = new HashSet<>();
                bookELocationIDList = new HashSet<>();
                languageList = new HashSet<>();
                publicationTypeList = new HashSet<>();
                keywordList = new HashSet<>();
                itemList = new HashSet<>();
                bookReferenceList = new ArrayList<>();
                articleIDList = new HashSet<>();
                return;
            case "Publisher":
                publisher = new Publisher();
                return;
            case "BookTitle":
            case "CollectionTitle":
            case "ArticleTitle":
                if (mainTag.equals("article")) {
                    return;
                }
                title = new Title();
                if (attributes.getValue("book") != null) {
                    title.setBook(attributes.getValue("book"));
                }
                if (attributes.getValue("part") != null) {
                    title.setPart(attributes.getValue("part"));
                }
                if (attributes.getValue("sec") != null) {
                    title.setSec(attributes.getValue("sec"));
                }
                return;
            case "Isbn":
                isbn = new Isbn();
                return;
            case "LocationLabel":
                locationLabel = new LocationLabel();
                if (attributes.getValue("Type") != null) {
                    locationLabel.setType(attributes.getValue("Type"));
                }
                return;
            case "Sections":
                ancestorTag = "Sections";
                sectionList = new HashSet<>();
                return;
            case "SectionTitle":
                title = new Title();
                if (attributes.getValue("book") != null) {
                    title.setBook(attributes.getValue("book"));
                }
                if (attributes.getValue("part") != null) {
                    title.setPart(attributes.getValue("part"));
                }
                if (attributes.getValue("sec") != null) {
                    title.setSec(attributes.getValue("sec"));
                }
                return;
            case "ItemList":
                itemType = attributes.getValue("ListType");
                return;
            case "Item":
                item = new Item();
                return;
            case "PubmedBookData":
                articleIDList = book.getArticleIDList();
                return;
            case "DeleteCitation":
            case "DeleteDocument":
                mainTag = "DeleteCitation";
                return;
            default:
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "PubmedArticle":
                article.setArticleAuthorList(new ArrayList<>(articleAuthorList.values()));
                articleMainList.add(article);
                return;
            case "MedlineCitation":
                if (!citationSubsetList.isEmpty()) {
                    article.setCitationSubsetList(citationSubsetList);
                }
                if (!otherIDList.isEmpty()) {
                    article.setOtherIDList(otherIDList);
                }
                if (!otherAbstractList.isEmpty()) {
                    article.setOtherAbstractList(otherAbstractList);
                }
                if (!keywordList.isEmpty()) {
                    article.setKeywordList(keywordList);
                }
                if(!spaceFlightMissionList.isEmpty()){
                    article.setSpaceFlightMissionList(spaceFlightMissionList);
                }
                if (!generalNoteList.isEmpty()) {
                    article.setGeneralNoteList(generalNoteList);
                }
                return;
            case "PMID":
                if (mainTag.equals("article")) {
                    if (ancestorTag.equals("MedlineCitation")) {
                        article.setPmid(stringBuilder.toString());
                    }
                    if (ancestorTag.equals("CommentsCorrectionsList")) {
                        commentsCorrections.setPmidRef(stringBuilder.toString());
                    }
                }
                if (mainTag.equals("book")) {
                    book.setPmid(stringBuilder.toString());
                }
                if (mainTag.equals("DeleteCitation")) {
                    deleteCitation.setPmid(stringBuilder.toString());
                    deleteCitationList.add(deleteCitation);
                }
                return;
            case "DateCompleted":
                article.setDateCompleted(date.toString());
                return;
            case "DateRevised":
                if (mainTag.equals("article")) {
                    article.setDateRevised(date.toString());
                }
                if (mainTag.equals("book")) {
                    book.setDateRevised(date.toString());
                }
                return;
            case "Year":
                date.setYear(stringBuilder.toString());
                return;
            case "Month":
                date.setMonth(stringBuilder.toString());
                return;
            case "Day":
                date.setDay(stringBuilder.toString());
                return;
            case "Hour":
                date.setHour(stringBuilder.toString());
                return;
            case "Minute":
                date.setMinute(stringBuilder.toString());
                return;
            case "Season":
                date.setSeason(stringBuilder.toString());
                return;
            case "MedlineDate":
                date.setMedlineDate(stringBuilder.toString());
                return;
            case "Article":
                if (!articleELocationIDList.isEmpty()) {
                    article.setArticleELocationIDList(articleELocationIDList);
                }
                article.setLanguageList(languageList);
                if (!articleDateList.isEmpty()) {
                    article.setArticleDateList(articleDateList);
                }
                return;
            case "ISSN":
                journal.setISSN(stringBuilder.toString());
                return;
            case "ISSNLinking":
                journal.setISSNLinking(stringBuilder.toString());
                return;
            case "Volume":
                if (mainTag.equals("article")) {
                    articleJournal.setVolume(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setVolume(stringBuilder.toString());
                }
                return;
            case "Issue":
                articleJournal.setIssue(stringBuilder.toString());
                return;
            case "PubDate":
                if (mainTag.equals("article")) {
                    articleJournal.setPubDate(date.toString());
                }
                if (mainTag.equals("book")) {
                    book.setPubDate(date.toString());
                }
                return;
            case "Title":
                if (ancestorTag.equals("Journal")) {
                    journal.setTitle(stringBuilder.toString());
                }
                if (ancestorTag.equals("ReferenceList")) {
                    if (referenceTitle == null) {
                        referenceTitle = stringBuilder.toString();
                    } else {
                        referenceTitle = referenceTitle.concat(" | ").concat(stringBuilder.toString());
                    }
                }
                return;
            case "ISOAbbreviation":
            case "MedlineTA":
                journal.setISOAbbreviation(stringBuilder.toString());
                return;
            case "ArticleTitle":
                if (mainTag.equals("article")) {
                    article.setArticleTitle(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    title.setTitle(stringBuilder.toString());
                    book.setArticleTitle(title.getAllFields());
                }
                return;
            case "StartPage":
                if (mainTag.equals("article")) {
                    article.setStartPage(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setStartPage(stringBuilder.toString());
                }
                return;
            case "EndPage":
                if (mainTag.equals("article")) {
                    article.setEndPage(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setEndPage(stringBuilder.toString());
                }
                return;
            case "MedlinePgn":
                if (mainTag.equals("article")) {
                    article.setMedlinePgn(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setMedlinePgn(stringBuilder.toString());
                }
                return;
            case "ELocationID":
                if (mainTag.equals("article")) {
                    articleELocationID.seteLocationID(stringBuilder.toString());
                    articleELocationID.setArticle(article);
                    articleELocationIDList.add(articleELocationID);
                }
                if (mainTag.equals("book")) {
                    bookELocationID.seteLocationID(stringBuilder.toString());
                    bookELocationID.setBook(book);
                    bookELocationIDList.add(bookELocationID);
                }
                return;
            case "Abstract":
                anAbstract.setAbstractTextList(abstractTextList);
                if (mainTag.equals("article")) {
                    article.setAbstractText(anAbstract.toString());
                    article.setCopyrightInformation(anAbstract.getCopyrightInformation());
                }
                if (mainTag.equals("book")) {
                    book.setAbstractText(anAbstract.toString());
                    book.setCopyrightInformation(anAbstract.getCopyrightInformation());
                }
                return;
            case "AbstractText":
                abstractText.setAbstractText(stringBuilder.toString());
                abstractTextList.add(abstractText);
                return;
            case "CopyrightInformation":
                anAbstract.setCopyrightInformation(stringBuilder.toString());
                return;
            case "Author":
            case "Investigator":
                if (!affiliationList.isEmpty()) {
                    author.setAffiliationList(affiliationList);
                }
                if (author.getIdentifierList() == null && !identifierList.isEmpty()) {
                    author.setIdentifierList(identifierList);
                }
                if (mainTag.equals("article")) {
                    articleAuthor.setRole(authorRole);
                    checkArticleAuthor();
                }
                if (mainTag.equals("book")) {
                    bookAuthor.setRole(authorRole);
                    checkBookAuthor();
                }
                return;
            case "LastName":
                author.setLastName(stringBuilder.toString());
                return;
            case "ForeName":
                author.setForeName(stringBuilder.toString());
                return;
            case "Initials":
                author.setInitials(stringBuilder.toString());
                return;
            case "Suffix":
                author.setSuffix(stringBuilder.toString());
                return;
            case "CollectiveName":
                author.setCollectiveName(stringBuilder.toString());
                return;
            case "AffiliationInfo":
                affiliation.setIdentifierList(identifierList);
                affiliation.setId();
                if (!affiliationMainList.containsKey(affiliation.getId())) {
                    affiliationMainList.put(affiliation.getId(), affiliation);
                    affiliationList.add(affiliation);
                } else {
                    affiliationList.add(affiliationMainList.get(affiliation.getId()));
                }
                return;
            case "Affiliation":
                affiliation.setAffiliation(stringBuilder.toString());
                return;
            case "Identifier":
                identifier.setIdentifier(stringBuilder.toString());
                if (!identifierMainList.containsKey(identifier.getIdentifier())) {
                    identifierMainList.put(identifier.getIdentifier(), identifier);
                    identifierList.add(identifier);
                } else {
                    identifierList.add(identifierMainList.get(identifier.getIdentifier()));
                }
                return;
            case "Language":
                language.setLanguage(stringBuilder.toString());
                if (!languageMainList.containsKey(language.getLanguage())) {
                    languageMainList.put(language.getLanguage(), language);
                    languageList.add(language);
                } else {
                    languageList.add(languageMainList.get(language.getLanguage()));
                }
                return;
            case "DataBankList":
                article.setAccessionNumberList(accessionNumberList);
                return;
            case "DataBankName":
                dataBankName = stringBuilder.toString();
                return;
            case "AccessionNumber":
                accessionNumber.setAccessionNumber(stringBuilder.toString());
                accessionNumber.setDataBankName(dataBankName);
                if (!accessionNumberMainList.containsKey(accessionNumber.getAccessionNumber())) {
                    accessionNumberMainList.put(accessionNumber.getAccessionNumber(), accessionNumber);
                    accessionNumberList.add(accessionNumber);
                } else {
                    accessionNumberList.add(accessionNumberMainList.get(accessionNumber.getAccessionNumber()));
                }
                return;
            case "GrantList":
                if (mainTag.equals("article")) {
                    article.setArticleGrantList(articleGrantList);
                }
                if (mainTag.equals("book")) {
                    book.setBookGrantList(bookGrantList);
                }
                return;
            case "Grant":
                if (!grantAgencyMainList.containsKey(grantAgency.hashCode())) {
                    grantAgencyMainList.put(grantAgency.hashCode(), grantAgency);
                    if (mainTag.equals("article")) {
                        articleGrant.setGrantAgency(grantAgency);
                    }
                    if (mainTag.equals("book")) {
                        bookGrant.setGrantAgency(grantAgency);
                    }
                } else {
                    if (mainTag.equals("article")) {
                        articleGrant.setGrantAgency(grantAgencyMainList.get(grantAgency.hashCode()));
                    }
                    if (mainTag.equals("book")) {
                        bookGrant.setGrantAgency(grantAgencyMainList.get(grantAgency.hashCode()));
                    }
                }
                if (mainTag.equals("article")) {
                    articleGrant.setArticle(article);
                    articleGrantList.add(articleGrant);
                }
                if (mainTag.equals("book")) {
                    bookGrant.setBook(book);
                    bookGrantList.add(bookGrant);
                }
                return;
            case "GrantID":
                if (mainTag.equals("article")) {
                    articleGrant.setGrantID(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    bookGrant.setGrantID(stringBuilder.toString());
                }
                return;
            case "Acronym":
                grantAgency.setAcronym(stringBuilder.toString());
                return;
            case "Agency":
                grantAgency.setAgency(stringBuilder.toString());
                return;
            case "Country":
                if (ancestorTag.equals("Grant")) {
                    grantAgency.setCountry(stringBuilder.toString());
                }
                if (ancestorTag.equals("MedlineJournalInfo")) {
                    journal.setCountry(stringBuilder.toString());
                }
                return;
            case "PublicationTypeList":
                article.setPublicationTypeList(publicationTypeList);
                return;
            case "PublicationType":
                publicationType.setName(stringBuilder.toString());
                if (!publicationTypeMainList.containsKey(publicationType.getUI())) {
                    publicationTypeMainList.put(publicationType.getUI(), publicationType);
                    publicationTypeList.add(publicationType);
                } else {
                    publicationTypeList.add(publicationTypeMainList.get(publicationType.getUI()));
                }
                return;
            case "VernacularTitle":
                if (mainTag.equals("article")) {
                    article.setVernacularTitle(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setVernacularTitle(stringBuilder.toString());
                }
                return;
            case "ArticleDate":
                articleDate.setDate(date.toString());
                articleDate.setArticle(article);
                articleDateList.add(articleDate);
                return;
            case "MedlineJournalInfo":
                articleJournal.setArticle(article);
                if (!journalMainList.containsKey(journal.getISOAbbreviation())) {
                    articleJournal.setJournal(journal);
                    journalMainList.put(journal.getISOAbbreviation(), journal);
                } else {
                    articleJournal.setJournal(journalMainList.get(journal.getISOAbbreviation()));
                }
                article.setArticleJournalList(new ArrayList<>(Collections.singletonList(articleJournal)));
                return;
            case "NlmUniqueID":
                journal.setNlmUniqueID(stringBuilder.toString());
                return;
            case "ChemicalList":
                article.setChemicalList(chemicalList);
                return;
            case "Chemical":
                if (!chemicalMainList.containsKey(chemical.getUI())) {
                    chemicalMainList.put(chemical.getUI(), chemical);
                    chemicalList.add(chemical);
                } else {
                    chemicalList.add(chemicalMainList.get(chemical.getUI()));
                }
                return;
            case "RegistryNumber":
                chemical.setRegistryNumber(stringBuilder.toString());
                return;
            case "NameOfSubstance":
                chemical.setNameOfSubstance(stringBuilder.toString());
                return;
            case "SupplMeshList":
                article.setSupplMeshNameList(supplMeshNameList);
                return;
            case "SupplMeshName":
                supplMeshName.setName(stringBuilder.toString());
                if (!supplMeshNameMainList.containsKey(supplMeshName.getUI())) {
                    supplMeshNameMainList.put(supplMeshName.getUI(), supplMeshName);
                    supplMeshNameList.add(supplMeshName);
                } else {
                    supplMeshNameList.add(supplMeshNameMainList.get(supplMeshName.getUI()));
                }
                return;
            case "CitationSubset":
                citationSubset.setCitationSubset(stringBuilder.toString());
                citationSubset.setArticle(article);
                citationSubsetList.add(citationSubset);
                return;
            case "CommentsCorrectionsList":
                article.setCommentsCorrectionsList(commentsCorrectionsList);
                return;
            case "CommentsCorrections":
                commentsCorrections.setArticle(article);
                commentsCorrectionsList.add(commentsCorrections);
                return;
            case "RefSource":
                commentsCorrections.setRefSource(stringBuilder.toString());
                return;
            case "Note":
                commentsCorrections.setNote(stringBuilder.toString());
                return;
            case "GeneSymbolList":
                article.setGeneSymbolList(geneSymbolList);
                return;
            case "GeneSymbol":
                geneSymbol.setGeneSymbol(stringBuilder.toString());
                if (!geneSymbolMainList.containsKey(geneSymbol.getGeneSymbol())) {
                    geneSymbolMainList.put(geneSymbol.getGeneSymbol(), geneSymbol);
                    geneSymbolList.add(geneSymbol);
                } else {
                    geneSymbolList.add(geneSymbolMainList.get(geneSymbol.getGeneSymbol()));
                }
                return;
            case "MeshHeadingList":
                article.setMeshHeadingList(meshHeadingList);
                return;
            case "DescriptorName":
            case "QualifierName":
                meshHeading.setName(stringBuilder.toString());
                if (!meshHeadingMainList.containsKey(meshHeading.getUI())) {
                    meshHeadingMainList.put(meshHeading.getUI(), meshHeading);
                    meshHeadingList.add(meshHeading);
                } else {
                    meshHeadingList.add(meshHeadingMainList.get(meshHeading.getUI()));
                }
                return;
            case "PersonalNameSubject":
                articleAuthor.setRole(authorRole);
                checkArticleAuthor();
                return;
            case "OtherID":
                otherID.setOtherID(stringBuilder.toString());
                otherID.setArticle(article);
                otherIDList.add(otherID);
                return;
            case "OtherAbstract":
                anAbstract.setAbstractTextList(abstractTextList);
                otherAbstract.setAbstractText(anAbstract.toString());
                otherAbstract.setCopyrightInformation(anAbstract.getCopyrightInformation());
                otherAbstract.setArticle(article);
                otherAbstractList.add(otherAbstract);
                return;
            case "Keyword":
                keyword.setKeyword(stringBuilder.toString());
                if (!keywordMainList.containsKey(keyword.getKeyword())) {
                    keywordMainList.put(keyword.getKeyword(), keyword);
                    keywordList.add(keyword);
                } else {
                    keywordList.add(keywordMainList.get(keyword.getKeyword()));
                }
                return;
            case "CoiStatement":
                article.setCoiStatement(stringBuilder.toString());
                return;
            case "SpaceFlightMission":
                spaceFlightMission.setSpaceFlightMission(stringBuilder.toString());
                if (!spaceFlightMissionMainList.containsKey(spaceFlightMission.getSpaceFlightMission())) {
                    spaceFlightMissionMainList.put(spaceFlightMission.getSpaceFlightMission(), spaceFlightMission);
                    spaceFlightMissionList.add(spaceFlightMission);
                } else {
                    spaceFlightMissionList.add(spaceFlightMissionMainList.get(spaceFlightMission.getSpaceFlightMission()));
                }
                return;
            case "GeneralNote":
                generalNote.setNote(stringBuilder.toString());
                generalNote.setArticle(article);
                generalNoteList.add(generalNote);
                return;
            case "PubmedData":
                article.setArticleReferenceList(articleReferenceList);
                return;
            case "History":
                if (mainTag.equals("article")) {
                    article.setArticleHistoryList(articleHistoryList);
                }
                if (mainTag.equals("book")) {
                    book.setBookHistoryList(bookHistoryList);
                }
                return;
            case "PubMedPubDate":
                if (mainTag.equals("article")) {
                    articleHistory.setDate(date.toString());
                    articleHistory.setArticle(article);
                    articleHistoryList.add(articleHistory);
                }
                if (mainTag.equals("book")) {
                    bookHistory.setDate(date.toString());
                    bookHistory.setBook(book);
                    bookHistoryList.add(bookHistory);
                }
                return;
            case "PublicationStatus":
                if (mainTag.equals("article")) {
                    article.setPublicationStatus(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    book.setPublicationStatus(stringBuilder.toString());
                }
                return;
            case "ArticleIdList":
                if (!ancestorTag.equals("ReferenceList")) {
                    if (mainTag.equals("article")) {
                        article.setArticleIDList(articleIDList);
                    }
                    if (mainTag.equals("book")) {
                        book.setArticleIDList(articleIDList);
                    }
                }
                return;
            case "ArticleId":
                articleID.setArticleId(stringBuilder.toString());
                if (articleIDMainList.containsKey(articleID.hashCode())) {
                    articleIDList.add(articleIDMainList.get(articleID.hashCode()));
                } else {
                    articleIDMainList.put(articleID.hashCode(), articleID);
                    articleIDList.add(articleID);
                }
                return;
            case "ObjectList":
                if (mainTag.equals("article")) {
                    article.setArticleObjectList(articleObjectList);
                }
                if (mainTag.equals("book")) {
                    book.setBookObjectList(bookObjectList);
                }
                return;
            case "Param":
                if (mainTag.equals("article")) {
                    articleObject.setText(stringBuilder.toString());
                    articleObject.setType(articleObjectType);
                    articleObject.setArticle(article);
                    articleObjectList.add(articleObject);
                }
                if (mainTag.equals("book")) {
                    bookObject.setText(stringBuilder.toString());
                    bookObject.setType(articleObjectType);
                    bookObject.setBook(book);
                    bookObjectList.add(bookObject);
                }
                return;
            case "ReferenceList":
                if (referenceTitle != null) {
                    if (referenceTitle.contains("|")) {
                        referenceTitle = referenceTitle.substring(0, referenceTitle.lastIndexOf("|"));
                    } else {
                        referenceTitle = null;
                    }
                }
                return;
            case "Reference":
                if (mainTag.equals("article")) {
                    if (articleIDList != null) {
                        articleReference.setArticleIDList(articleIDList);
                    }
                    if (referenceTitle != null) {
                        articleReference.setTitle(referenceTitle);
                    }
                    articleReference.setArticle(article);
                    articleReferenceList.add(articleReference);
                }
                if (mainTag.equals("book")) {
                    if (articleIDList != null) {
                        bookReference.setArticleIDList(articleIDList);
                    }
                    if (referenceTitle != null) {
                        bookReference.setTitle(referenceTitle);
                    }
                    bookReference.setBook(book);
                    bookReferenceList.add(bookReference);
                }
                return;
            case "Citation":
                if (mainTag.equals("article")) {
                    articleReference.setCitation(stringBuilder.toString());
                }
                if (mainTag.equals("book")) {
                    bookReference.setCitation(stringBuilder.toString());
                }
                return;
            case "BookDocument":
                if (!bookAuthorList.isEmpty())  {
                    book.setBookAuthorList(new ArrayList<>(bookAuthorList.values()));
                }
                if (!bookELocationIDList.isEmpty()) {
                    book.setBookELocationIDList(bookELocationIDList);
                }
                if (!isbnList.isEmpty()) {
                    book.setIsbnList(isbnList);
                }
                if (!locationLabelList.isEmpty()) {
                    book.setLocationLabelList(locationLabelList);
                }
                if (!publicationTypeList.isEmpty()) {
                    book.setPublicationTypeList(publicationTypeList);
                }
                if (!keywordList.isEmpty()) {
                    book.setKeywordList(keywordList);
                }
                if (!languageList.isEmpty()) {
                    book.setLanguageList(languageList);
                }
                if (!itemList.isEmpty()) {
                    book.setItemList(itemList);
                }
                if (!bookReferenceList.isEmpty()) {
                    book.setBookReferenceList(bookReferenceList);
                }
                if (!articleIDList.isEmpty()) {
                    book.setArticleIDList(articleIDList);
                }
                bookMainList.add(book);
                return;
            case "Publisher":
                if (!publisherMainList.containsKey(publisher.hashCode())) {
                    book.setPublisherList(Collections.singletonList(publisher));
                    publisherMainList.put(publisher.hashCode(), publisher);
                } else {
                    book.setPublisherList(Collections.singletonList(publisherMainList.get(publisher.hashCode())));
                }
                return;
            case "PublisherName":
                publisher.setName(stringBuilder.toString());
                return;
            case "PublisherLocation":
                publisher.setLocation(stringBuilder.toString());
                return;
            case "BookTitle":
                title.setTitle(stringBuilder.toString());
                book.setBookTitle(title.getAllFields());
                return;
            case "CollectionTitle":
                title.setTitle(stringBuilder.toString());
                book.setCollectionTitle(title.getAllFields());
                return;
            case "BeginningDate":
                book.setBeginningDate(date.toString());
                return;
            case "EndingDate":
                book.setEndingDate(date.toString());
                return;
            case "VolumeTitle":
                book.setVolumeTitle(stringBuilder.toString());
                return;
            case "Edition":
                book.setEdition(stringBuilder.toString());
                return;
            case "Isbn":
                isbn.setIsbn(stringBuilder.toString());
                isbn.setBook(book);
                isbnList.add(isbn);
                return;
            case "Medium":
                book.setMedium(stringBuilder.toString());
                return;
            case "ReportNumber":
                book.setReportNumber(stringBuilder.toString());
                return;
            case "LocationLabel":
                locationLabel.setLocation(stringBuilder.toString());
                if (ancestorTag.equals("Sections")) {
                    if (sectionLocation == null) {
                        if (locationLabel.getType() != null) {
                            sectionLocation = locationLabel.getType() + ":";
                        }
                    } else {
                        sectionLocation = sectionLocation.concat(" | ");
                        sectionLocation = sectionLocation.concat(locationLabel.getType());
                    }
                    sectionLocation = sectionLocation.concat(stringBuilder.toString());
                    return;
                }
                locationLabelList.add(locationLabel);
                return;
            case "Sections":
                book.setSectionList(sectionList);
                return;
            case "Section":
                Section section = new Section();
                section.setTitle(sectionTitle);
                section.setLocationLabel(sectionLocation);
                section.setBook(book);
                sectionList.add(section);
                if (sectionTitle != null) {
                    if (sectionTitle.contains("|")) {
                        sectionTitle = sectionTitle.substring(0, sectionTitle.lastIndexOf("|"));
                    } else {
                        sectionTitle = null;
                    }
                }
                if (locationLabel.getLocationLabelKeyLocation() != null) {
                    if (sectionLocation.contains("|")) {
                        sectionLocation = sectionLocation.substring(0, sectionLocation.lastIndexOf("|"));
                    } else {
                        sectionLocation = null;
                    }
                }
                return;
            case "SectionTitle":
                if (sectionTitle == null) {
                    sectionTitle = stringBuilder.toString();
                } else {
                    sectionTitle = sectionTitle.concat(" | ").concat(stringBuilder.toString());
                }
                return;
            case "ContributionDate":
                book.setContributionDate(date.toString());
                return;
            case "Item":
                item.setItem(stringBuilder.toString());
                item.setType(itemType);
                if (itemMainList.containsKey(item.hashCode())) {
                    itemList.add(itemMainList.get(item.hashCode()));
                } else {
                    itemMainList.put(item.hashCode(), item);
                    itemList.add(item);
                }
                return;
            case "PubmedBookData":
                book.setArticleIDList(articleIDList);
                return;
            default:
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        stringBuilder.append(new String(ch, start, length));
    }

    private void checkArticleAuthor() {
        articleAuthor.setArticle(article);
        author.setAuthorID((long) author.hashCode());

        if (!authorMainList.containsKey(author.getAuthorID())) {
            authorMainList.put(author.getAuthorID(), author);
            articleAuthor.setAuthor(author);
        } else {
            articleAuthor.setAuthor(authorMainList.get(author.getAuthorID()));
        }
        if (!articleAuthorList.containsKey(articleAuthor.hashCode())) {
            articleAuthorList.put(articleAuthor.hashCode(), articleAuthor);
        }
    }

    private void checkBookAuthor() {
        bookAuthor.setBook(book);
        author.setAuthorID((long) author.hashCode());

        if (!authorMainList.containsKey(author.getAuthorID())) {
            authorMainList.put(author.getAuthorID(), author);
            bookAuthor.setAuthor(author);
        } else {
            bookAuthor.setAuthor(authorMainList.get(author.getAuthorID()));
        }
        if (!bookAuthorList.containsKey(bookAuthor.hashCode())) {
            bookAuthorList.put(bookAuthor.hashCode(), bookAuthor);
        }
    }

    private List<Article> getArticleMainList() {
        return articleMainList;
    }

    private List<Journal> getJournalList() {
        return new ArrayList<>(journalMainList.values());
    }

    private List<Language> getLanguageMainList() {
        return new ArrayList<>(languageMainList.values());
    }

    private List<AccessionNumber> getAccessionNumberMainList() {
        return new ArrayList<>(accessionNumberMainList.values());
    }

    private List<GrantAgency> getGrantsMainList() {
        return new ArrayList<>(grantAgencyMainList.values());
    }

    private List<PublicationType> getPublicationTypeMainList() {
        return new ArrayList<>(publicationTypeMainList.values());
    }

    private List<Chemical> getChemicalMainList() {
        return new ArrayList<>(chemicalMainList.values());
    }

    private List<SupplMeshName> getSupplMeshNameMainList() {
        return new ArrayList<>(supplMeshNameMainList.values());
    }

    private List<GeneSymbol> getGeneSymbolMainList() {
        return new ArrayList<>(geneSymbolMainList.values());
    }

    private List<MeshHeading> getMeshHeadingMainList() {
        return new ArrayList<>(meshHeadingMainList.values());
    }

    private List<Keyword> getKeywordMainList() {
        return new ArrayList<>(keywordMainList.values());
    }

    private List<SpaceFlightMission> getSpaceFlightMissionMainList() {
        return new ArrayList<>(spaceFlightMissionMainList.values());
    }

    private List<Author> getAuthorMainList() {
        return new ArrayList<>(authorMainList.values());
    }

    private List<Affiliation> getAffiliationMainList() {
        return new ArrayList<>(affiliationMainList.values());
    }

    private List<Identifier> getIdentifierMainList() {
        return new ArrayList<>(identifierMainList.values());
    }

    private List<ArticleID> getArticleIDMainList() {
        return new ArrayList<>(articleIDMainList.values());
    }

    private List<Book> getBookMainList() {
        return bookMainList;
    }

    private List<Item> getItemMainList() {
        return new ArrayList<>(itemMainList.values());
    }

    private List<Publisher> getPublisherMainList() {
        return new ArrayList<>(publisherMainList.values());
    }

    private List<DeleteCitation> getDeleteCitationList() {
        return deleteCitationList;
    }

    public HandlerContainer getHandlerOutput() {
        HandlerContainer handlerContainer = new HandlerContainer();
        handlerContainer.setArticleMainList(getArticleMainList());
        handlerContainer.setJournalMainList(getJournalList());
        handlerContainer.setLanguageMainList(getLanguageMainList());
        handlerContainer.setAccessionNumberMainList(getAccessionNumberMainList());
        handlerContainer.setGrantAgencyMainList(getGrantsMainList());
        handlerContainer.setPublicationTypeMainList(getPublicationTypeMainList());
        handlerContainer.setChemicalMainList(getChemicalMainList());
        handlerContainer.setSupplMeshNameMainList(getSupplMeshNameMainList());
        handlerContainer.setGeneSymbolList(getGeneSymbolMainList());
        handlerContainer.setMeshHeadingMainList(getMeshHeadingMainList());
        handlerContainer.setKeywordMainList(getKeywordMainList());
        handlerContainer.setSpaceFlightMissionList(getSpaceFlightMissionMainList());
        handlerContainer.setAuthorMainList(getAuthorMainList());
        handlerContainer.setAffiliationMainList(getAffiliationMainList());
        handlerContainer.setIdentifierMainList(getIdentifierMainList());
        handlerContainer.setArticleIDMainList(getArticleIDMainList());
        handlerContainer.setBookMainList(getBookMainList());
        handlerContainer.setItemMainList(getItemMainList());
        handlerContainer.setPublisherMainList(getPublisherMainList());
        handlerContainer.setDeleteCitationList(getDeleteCitationList());
        return handlerContainer;
    }

}
