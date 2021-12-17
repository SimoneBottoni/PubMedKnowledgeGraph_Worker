package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Article;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.AnnotationMetaData;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.FailedFile;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util.InsertData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseAnnotateAfterService {

    private final AnnotationMetaDataRepository annotationMetaDataRepository;
    private final FailedFileRepository failedFileRepository;

    private final AnnotatorService annotatorService;
    private final InsertData insertData;

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final BookRepository bookRepository;

    private final Logger logger = LogManager.getRootLogger();

    private List<Tag> tagMainList;

    private String fileName;

    private List<Article> articlesToAnnotate;
    private List<Book> booksToAnnotate;

    @Autowired
    public ParseAnnotateAfterService(AnnotationMetaDataRepository annotationMetaDataRepository, FailedFileRepository failedFileRepository, AnnotatorService annotatorService, InsertData insertData, TagRepository tagRepository, ArticleRepository articleRepository, BookRepository bookRepository) {
        this.annotationMetaDataRepository = annotationMetaDataRepository;
        this.failedFileRepository = failedFileRepository;
        this.annotatorService = annotatorService;
        this.insertData = insertData;
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
        this.bookRepository = bookRepository;
    }

    public void setPubMedFile(String fileName) {
        this.fileName = fileName;
    }

    public void run() {
        try {
            loadArticlesAndBooks();
            callAnnotatorService();
            logger.info("Start data entry for " + fileName);
            callInsertService();
            callUpdateService();
            logger.info("Data entry completed for " + fileName);
        } catch (Exception e) {
            logger.info(e);
            updateFailedTable(e.toString());
        }
        updateMetadataTable();
    }

    private void loadArticlesAndBooks() {
        logger.info("Loading data " + fileName);
        articlesToAnnotate = new ArrayList<>();
        articlesToAnnotate.addAll(articleRepository.getArticlesByFileName(fileName + ".gz"));
        booksToAnnotate = new ArrayList<>();
        booksToAnnotate.addAll(bookRepository.getBooksByFileName(fileName + ".gz"));
        logger.info("Loaded data " + fileName);
    }

    private void callAnnotatorService() {
        logger.info("Annotating " + fileName);
        tagMainList = annotatorService.Annotate(fileName, articlesToAnnotate, booksToAnnotate);
        logger.info("Annotated " + fileName);
    }

    private void callInsertService() {
        insertData.insertTags(tagRepository, tagMainList);
    }

    private void callUpdateService() {
        articleRepository.saveAll(articlesToAnnotate);
        bookRepository.saveAll(booksToAnnotate);
    }

    private void updateFailedTable(String exception) {
        FailedFile failedFile = new FailedFile();
        failedFile.setFileName(fileName + ".gz");
        failedFile.setType("annotation");
        failedFile.setException(exception);
        failedFileRepository.save(failedFile);
    }

    private void updateMetadataTable() {
        annotationMetaDataRepository.save(new AnnotationMetaData(fileName + ".gz"));
    }

}
