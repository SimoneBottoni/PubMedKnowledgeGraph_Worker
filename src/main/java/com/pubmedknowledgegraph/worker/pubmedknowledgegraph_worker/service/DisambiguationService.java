package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.authordisambiguation.SolveDisambiguation;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Article;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.ArticleAuthor;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Author;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.DisambiguationAuthor;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.DisambiguationAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisambiguationService {

    private final DisambiguationAuthorRepository disambiguationAuthorRepository;

    @Autowired
    public DisambiguationService(DisambiguationAuthorRepository disambiguationAuthorRepository) {
        this.disambiguationAuthorRepository = disambiguationAuthorRepository;
    }

    public void solveDisambiguation(List<Article> articleList) {
        List<String> PMIDs = new ArrayList<>();
        for(Article article:articleList) {
            PMIDs.add(article.getPmid());
        }
        List<DisambiguationAuthor> disambiguationAuthorList = disambiguationAuthorRepository.getDisambiguationAuthorityByPmids(PMIDs);

        for(Article article:articleList) {
            for (ArticleAuthor articleAuthor:article.getArticleAuthorList()) {
                Author author = articleAuthor.getAuthor();
                SolveDisambiguation solveDisambiguation = new SolveDisambiguation();
                if (author.getAuthorityID() == null ) {
                    String authorityID = solveDisambiguation.getAuthorityID(disambiguationAuthorList, article.getPmid(), author.getForeName(), author.getLastName());
                    author.setAuthorityID(authorityID);
                }
                if (author.getSemanticScholarID() == null ) {
                    String semanticScholarID = solveDisambiguation.getSemanticScholarID(disambiguationAuthorList, article.getPmid(), author.getForeName(), author.getLastName());
                    author.setSemanticScholarID(semanticScholarID);
                }
            }
        }
    }

}
