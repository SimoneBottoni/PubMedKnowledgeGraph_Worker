package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.google.common.collect.Lists;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator.AnnotateArticleOrBook;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator.Annotator;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Article;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class AnnotatorService {

    @Value("${metamaplite.models}")
    private String modelsFolder = "metamaplite/data/models";
    @Value("${metamaplite.index}")
    private String indexFolder = "metamaplite/data/ivf/2020AA/USAbase";
    @Value("${metamaplite.data.specialterms}")
    private String specialterms = "metamaplite/data/specialterms.txt";
    @Value("${metamaplite.config}")
    private String configProperties = "metamaplite/config/metamaplite.properties";

    @Autowired
    public AnnotatorService() {
    }

    public List<Tag> Annotate(String fileName, List<Article> articleList, List<Book> bookList) {
        Set<Tag> tagMainList = new HashSet<>();
        List<Callable<List<Tag>>> callableList = new ArrayList<>();

        for (List<Article> partition : Lists.partition(articleList, 10000)) {
            callableList.add(createCallable(fileName, partition));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<List<Tag>>> futureList;
        try {
            futureList = executorService.invokeAll(callableList);
        } catch (InterruptedException e) {
            futureList = new ArrayList<>();
        }

        if (!bookList.isEmpty()) {
            Annotator annotator = new Annotator(modelsFolder, indexFolder, specialterms, configProperties);
            Callable<List<Tag>> callable = new AnnotateArticleOrBook(fileName, annotator, null, bookList);
            futureList.add(executorService.submit(callable));
        }

        futureList.forEach(tagList -> {
            try {
                tagMainList.addAll(tagList.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return new ArrayList<>(tagMainList);
    }

    private Callable<List<Tag>> createCallable(String fileName, List<Article> articleSubList) {
        Annotator annotator = new Annotator(modelsFolder, indexFolder, specialterms, configProperties);
        return new AnnotateArticleOrBook(fileName, annotator, articleSubList, null);
    }

}
