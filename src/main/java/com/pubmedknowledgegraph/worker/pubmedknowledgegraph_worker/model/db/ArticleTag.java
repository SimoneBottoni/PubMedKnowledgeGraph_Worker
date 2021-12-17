package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys.ArticleTagKey;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity(name = "article_tag")
public class ArticleTag implements Persistable<ArticleTagKey> {

    @EmbeddedId
    private ArticleTagKey articleTagKey;

    @Column(columnDefinition="TEXT")
    private String triggerInfo;
    @Column(columnDefinition="TEXT")
    private String positionalInfo;
    @Column(columnDefinition="TEXT")
    private String treeCodes;

    public ArticleTag() {
        this.articleTagKey = new ArticleTagKey();
    }

    public ArticleTag(Article article, Tag tag, String position, String score, String triggerInfo, String positionalInfo, String treeCodes) {
        this.articleTagKey = new ArticleTagKey();
        this.articleTagKey.setArticle(article);
        this.articleTagKey.setTag(tag);
        this.articleTagKey.setPosition(position);
        this.articleTagKey.setScore(score);
        this.triggerInfo = triggerInfo;
        this.positionalInfo = positionalInfo;
        this.treeCodes = treeCodes;
    }

    public ArticleTag(Article article, Tag tag, String position, String score, String triggerInfo, String positionalInfo) {
        this.articleTagKey = new ArticleTagKey();
        this.articleTagKey.setArticle(article);
        this.articleTagKey.setTag(tag);
        this.articleTagKey.setPosition(position);
        this.articleTagKey.setScore(score);
        this.triggerInfo = triggerInfo;
        this.positionalInfo = positionalInfo;
    }

    @JsonIgnore
    public Article getArticle() {
        return articleTagKey.getArticle();
    }

    public void setArticle(Article article) {
        this.articleTagKey.setArticle(article);
    }

    public Tag getTag() {
        return articleTagKey.getTag();
    }

    public void setTag(Tag tag) {
        this.articleTagKey.setTag(tag);
    }

    public String getPosition() {
        return articleTagKey.getPosition();
    }

    public void setPosition(String position) {
        this.articleTagKey.setPosition(position);
    }

    public String getScore() {
        return articleTagKey.getScore();
    }

    public void setScore(String score) {
        this.articleTagKey.setScore(score);
    }

    public String getTriggerInfo() {
        return triggerInfo;
    }

    public void setTriggerInfo(String triggerInfo) {
        this.triggerInfo = triggerInfo;
    }

    public String getPositionalInfo() {
        return positionalInfo;
    }

    public void setPositionalInfo(String positionalInfo) {
        this.positionalInfo = positionalInfo;
    }

    public String getTreeCodes() {
        return treeCodes;
    }

    public void setTreeCodes(String treeCodes) {
        this.treeCodes = treeCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleTag that = (ArticleTag) o;

        return articleTagKey.equals(that.articleTagKey);
    }

    @Override
    public int hashCode() {
        return articleTagKey.hashCode();
    }

    @Transient
    private boolean isNew = true;

    @JsonIgnore
    @Override
    public ArticleTagKey getId() {
        return articleTagKey;
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
