package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util;


import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsertData {

    @Autowired
    public InsertData() {
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertJournal(JournalRepository journalRepository, List<Journal> journalList) {
        journalRepository.saveAll(journalList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertLanguage(LanguageRepository languageRepository, List<Language> languageList) {
        languageRepository.saveAll(languageList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertAccessionNumber(AccessionNumberRepository accessionNumberRepository, List<AccessionNumber> accessionNumberList) {
        accessionNumberRepository.saveAll(accessionNumberList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertGrantAgency(GrantAgencyRepository grantAgencyRepository, List<GrantAgency> grantAgencyList) {
        grantAgencyRepository.saveAll(grantAgencyList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertPublicationType(PublicationTypeRepository publicationTypeRepository, List<PublicationType> publicationTypeList) {
        publicationTypeRepository.saveAll(publicationTypeList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertChemical(ChemicalRepository chemicalRepository, List<Chemical> chemicalList) {
        chemicalRepository.saveAll(chemicalList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertSupplMeshName(SupplMeshNameRepository supplMeshNameRepository, List<SupplMeshName> supplMeshNameList) {
        supplMeshNameRepository.saveAll(supplMeshNameList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertGeneSymbol(GeneSymbolRepository geneSymbolRepository, List<GeneSymbol> geneSymbolList) {
        geneSymbolRepository.saveAll(geneSymbolList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertMeshHeading(MeshHeadingRepository meshHeadingRepository, List<MeshHeading> meshHeadingList) {
        meshHeadingRepository.saveAll(meshHeadingList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertKeyword(KeywordRepository keywordRepository, List<Keyword> keywordList) {
        keywordRepository.saveAll(keywordList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertSpaceFlightMission(SpaceFlightMissionRepository spaceFlightMissionRepository, List<SpaceFlightMission> spaceFlightMissionList) {
        spaceFlightMissionRepository.saveAll(spaceFlightMissionList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertArticleID(ArticleIDRepository articleIDRepository, List<ArticleID> articleIDList) {
        articleIDRepository.saveAll(articleIDList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertItem(ItemRepository itemRepository, List<Item> itemList) {
        itemRepository.saveAll(itemList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertPublisher(PublisherRepository publisherRepository, List<Publisher> publisherList) {
        publisherRepository.saveAll(publisherList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertIdentifier(IdentifierRepository identifierRepository, List<Identifier> identifierList) {
        identifierRepository.saveAll(identifierList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertAffiliation(AffiliationRepository affiliationRepository, List<Affiliation> affiliationList) {
        affiliationRepository.saveAll(affiliationList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertAuthor(AuthorRepository authorRepository, List<Author> authorList) {
        authorRepository.saveAll(authorList);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void insertTags(TagRepository tagRepository, List<Tag> tagMainList) {
        tagRepository.saveAll(tagMainList);
    }

}
