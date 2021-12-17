package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Article;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util.InsertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InsertService {

    private final AccessionNumberRepository accessionNumberRepository;
    private final AffiliationRepository affiliationRepository;
    private final ArticleIDRepository articleIDRepository;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ChemicalRepository chemicalRepository;
    private final GeneSymbolRepository geneSymbolRepository;
    private final GrantAgencyRepository grantAgencyRepository;
    private final IdentifierRepository identifierRepository;
    private final ItemRepository itemRepository;
    private final JournalRepository journalRepository;
    private final KeywordRepository keywordRepository;
    private final LanguageRepository languageRepository;
    private final MeshHeadingRepository meshHeadingRepository;
    private final PublicationTypeRepository publicationTypeRepository;
    private final PublisherRepository publisherRepository;
    private final SpaceFlightMissionRepository spaceFlightMissionRepository;
    private final SupplMeshNameRepository supplMeshNameRepository;
    private final TagRepository tagRepository;

    private final InsertData insertData;

    @Autowired
    public InsertService(AccessionNumberRepository accessionNumberRepository, AffiliationRepository affiliationRepository, ArticleIDRepository articleIDRepository, ArticleRepository articleRepository, AuthorRepository authorRepository, BookRepository bookRepository, ChemicalRepository chemicalRepository, GeneSymbolRepository geneSymbolRepository, GrantAgencyRepository grantAgencyRepository, IdentifierRepository identifierRepository, ItemRepository itemRepository, JournalRepository journalRepository, KeywordRepository keywordRepository, LanguageRepository languageRepository, MeshHeadingRepository meshHeadingRepository, PublicationTypeRepository publicationTypeRepository, PublisherRepository publisherRepository, SpaceFlightMissionRepository spaceFlightMissionRepository, SupplMeshNameRepository supplMeshNameRepository, TagRepository tagRepository, InsertData insertData) {
        this.accessionNumberRepository = accessionNumberRepository;
        this.affiliationRepository = affiliationRepository;
        this.articleIDRepository = articleIDRepository;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.chemicalRepository = chemicalRepository;
        this.geneSymbolRepository = geneSymbolRepository;
        this.grantAgencyRepository = grantAgencyRepository;
        this.identifierRepository = identifierRepository;
        this.itemRepository = itemRepository;
        this.journalRepository = journalRepository;
        this.keywordRepository = keywordRepository;
        this.languageRepository = languageRepository;
        this.meshHeadingRepository = meshHeadingRepository;
        this.publicationTypeRepository = publicationTypeRepository;
        this.publisherRepository = publisherRepository;
        this.spaceFlightMissionRepository = spaceFlightMissionRepository;
        this.supplMeshNameRepository = supplMeshNameRepository;
        this.tagRepository = tagRepository;
        this.insertData = insertData;
    }

    public void insertLists(HandlerContainer handlerContainer, List<Tag> tagMainList) {
        insertData.insertJournal(journalRepository, handlerContainer.getJournalMainList());
        insertData.insertLanguage(languageRepository, handlerContainer.getLanguageMainList());
        insertData.insertAccessionNumber(accessionNumberRepository, handlerContainer.getAccessionNumberMainList());
        insertData.insertGrantAgency(grantAgencyRepository, handlerContainer.getGrantAgencyMainList());
        insertData.insertPublicationType(publicationTypeRepository, handlerContainer.getPublicationTypeMainList());
        insertData.insertChemical(chemicalRepository, handlerContainer.getChemicalMainList());
        insertData.insertSupplMeshName(supplMeshNameRepository, handlerContainer.getSupplMeshNameMainList());
        insertData.insertGeneSymbol(geneSymbolRepository, handlerContainer.getGeneSymbolList());
        insertData.insertMeshHeading(meshHeadingRepository, handlerContainer.getMeshHeadingMainList());
        insertData.insertKeyword(keywordRepository, handlerContainer.getKeywordMainList());
        insertData.insertSpaceFlightMission(spaceFlightMissionRepository, handlerContainer.getSpaceFlightMissionList());
        insertData.insertArticleID(articleIDRepository, handlerContainer.getArticleIDMainList());
        insertData.insertItem(itemRepository, handlerContainer.getItemMainList());
        insertData.insertPublisher(publisherRepository, handlerContainer.getPublisherMainList());
        insertData.insertIdentifier(identifierRepository, handlerContainer.getIdentifierMainList());
        insertData.insertAffiliation(affiliationRepository, handlerContainer.getAffiliationMainList());
        insertData.insertAuthor(authorRepository, handlerContainer.getAuthorMainList());
        insertData.insertTags(tagRepository, tagMainList);
    }

    public void insertArticles(boolean isUpdate, List<Article> articleList) {
        List<Article> articlesToInsert = new ArrayList<>();
        for (Article article : articleList) {
            List<String> articlePMIDs = isUpdate ? getPMIDs("Article", article.getPmid()) : new ArrayList<>();
            long update = Collections.frequency(articlePMIDs, article.getPmid());
            article.setUpdate(String.valueOf(update));
            articlesToInsert.add(article);
        }
        articleRepository.saveAll(articlesToInsert);
    }

    public void insertBooks(boolean isUpdate, List<Book> bookList) {
        List<Book> booksToInsert = new ArrayList<>();
        for (Book book : bookList) {
            List<String> bookPMIDs = isUpdate ? getPMIDs("Book", book.getPmid()) : new ArrayList<>();
            long update = Collections.frequency(bookPMIDs, book.getPmid());
            book.setUpdate(String.valueOf(update));
            booksToInsert.add(book);
        }
        bookRepository.saveAll(booksToInsert);
    }

    private List<String> getPMIDs(String table, String pmid) {
        if (table.equals("Article")) {
            return articleRepository.getPmidByPmid(pmid);
        } else {
            return bookRepository.getPmidByPmid(pmid);
        }
    }

}
