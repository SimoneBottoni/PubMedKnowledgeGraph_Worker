package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Article;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.AnnotationMetaData;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.FailedFile;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.FileMetaData;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.AnnotationMetaDataRepository;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.FailedFileRepository;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.FileMetaDataRepository;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.MetaDataRepository;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseAnnotateService {

    private final AnnotationMetaDataRepository annotationMetaDataRepository;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final MetaDataRepository metaDataRepository;
    private final FailedFileRepository failedFileRepository;

    private final ParserService parserService;
    private final AnnotatorService annotatorService;
    private final DisambiguationService disambiguationService;
    private final InsertService insertService;
    private final DeleteService deleteService;

    private final Logger logger = LogManager.getRootLogger();

    private HandlerContainer handlerContainer;
    private List<Tag> tagMainList;

    private File file;
    private boolean isUpdate;

    private boolean annotationEnabled;

    @Autowired
    public ParseAnnotateService(AnnotationMetaDataRepository annotationMetaDataRepository, FileMetaDataRepository fileMetaDataRepository, MetaDataRepository metaDataRepository, FailedFileRepository failedFileRepository, ParserService parserService, AnnotatorService annotatorService, DisambiguationService disambiguationService, InsertService insertService, DeleteService deleteService) {
        this.annotationMetaDataRepository = annotationMetaDataRepository;
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.metaDataRepository = metaDataRepository;
        this.failedFileRepository = failedFileRepository;
        this.parserService = parserService;
        this.annotatorService = annotatorService;
        this.disambiguationService = disambiguationService;
        this.insertService = insertService;
        this.deleteService = deleteService;
    }

    public void setPubMedFile(boolean isUpdate, File file) {
        Util util = new Util();
        this.file = util.gunZipUpdate(file);
        this.isUpdate = isUpdate;
        getAnnotationEnabledCheck();
    }

    public void run() {
        try {
            callParserService();
            addFileName();
            callAnnotatorService();
            //callDisambiguationService();
            logger.info("Start data entry for " + file.getName());
            callInsertService();
            callDeleteService();
            logger.info("Data entry completed for " + file.getName());
        } catch (Exception e) {
            logger.info(e);
            updateFailedTable(e.toString());
        }
        updateMetadataTable();
    }

    private void callParserService() {
        logger.info("Parsing " + file.getName());
        handlerContainer = parserService.parse(file.getPath());
        logger.info("Parsed " + file.getName());
    }

    private void addFileName() {
        for (Article article : handlerContainer.getArticleMainList()) {
            article.setFileName(file.getName() + ".gz");
        }
        for (Book book : handlerContainer.getBookMainList()) {
            book.setFileName(file.getName() + ".gz");
        }
    }

    private void callAnnotatorService() {
        if (annotationEnabled) {
            logger.info("Annotating " + file.getName());
            tagMainList = annotatorService.Annotate(file.getName(), handlerContainer.getArticleMainList(), handlerContainer.getBookMainList());
            logger.info("Annotated " + file.getName());
        } else {
            tagMainList = new ArrayList<>();
        }
    }

    private void callDisambiguationService() {
        disambiguationService.solveDisambiguation(handlerContainer.getArticleMainList());
    }

    private void callInsertService() {
        insertService.insertLists(handlerContainer, tagMainList);
        insertService.insertArticles(isUpdate, handlerContainer.getArticleMainList());
        insertService.insertBooks(isUpdate, handlerContainer.getBookMainList());
    }

    private void callDeleteService() {
        deleteService.deleteArticlesOrBooks(handlerContainer.getDeleteCitationList());
    }

    private void updateFailedTable(String exception) {
        FailedFile failedFile = new FailedFile();
        failedFile.setFileName(file.getName() + ".gz");
        failedFile.setType(isUpdate ? "update" : "baseline");
        failedFile.setException(exception);
        failedFileRepository.save(failedFile);
    }

    private void updateMetadataTable() {
        if (isUpdate) {
            metaDataRepository.updateLastUpdateFileComputed(file.getName() + ".gz");
        } else {
            metaDataRepository.updateLastBaselineFileComputed(file.getName() + ".gz");
        }
        fileMetaDataRepository.save(new FileMetaData(file.getName() + ".gz"));
        if (annotationEnabled) {
            annotationMetaDataRepository.save(new AnnotationMetaData(file.getName() + ".gz"));
        }
        deleteFiles();
    }

    private void deleteFiles() {
        logger.info("Deleting gz file..");
        File gzFile = new File(file.getPath() + ".gz");
        if (gzFile.exists()) {
            gzFile.delete();
        }
        logger.info("Deleting xml file..");
        file.delete();
    }

    private void getAnnotationEnabledCheck() {
        if (isUpdate) {
            annotationEnabled = true;
            return;
        }
        annotationEnabled = Boolean.parseBoolean(metaDataRepository.getAnnotationEnabledCheck());
    }

}
