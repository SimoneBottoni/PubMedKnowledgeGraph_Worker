package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys.ArticleJournalKey;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity(name = "article_journal")
@AssociationOverrides({
        @AssociationOverride(name = "articleJournalKey.article",  joinColumns = {
                @JoinColumn(name = "pmid" , referencedColumnName = "pmid"),
                @JoinColumn(name = "version" , referencedColumnName = "version"),
                @JoinColumn(name = "update" , referencedColumnName = "update")
        }),
        @AssociationOverride(name = "articleJournalKey.journal", joinColumns = @JoinColumn(name = "isoabbreviation"))
})
public class ArticleJournal implements Persistable<ArticleJournalKey> {

    @EmbeddedId
    private ArticleJournalKey articleJournalKey;

    private String Volume;
    private String Issue;
    private String PubDate;
    private String CitedMedium;

    public ArticleJournal() {
        this.articleJournalKey = new ArticleJournalKey();
    }

    public void setArticle(Article article) {
        this.articleJournalKey.setArticle(article);
    }

    @JsonIgnore
    public Article getArticle() {
        return articleJournalKey.getArticle();
    }

    public void setJournal(Journal journal) {
        this.articleJournalKey.setJournal(journal);
    }

    public Journal getJournal() {
        return articleJournalKey.getJournal();
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getPubDate() {
        return PubDate;
    }

    public void setPubDate(String pubDate) {
        PubDate = pubDate;
    }

    public String getCitedMedium() {
        return CitedMedium;
    }

    public void setCitedMedium(String citedMedium) {
        CitedMedium = citedMedium;
    }

    @Transient
    private boolean isNew = true;

    @JsonIgnore
    @Override
    public ArticleJournalKey getId() {
        return articleJournalKey;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
