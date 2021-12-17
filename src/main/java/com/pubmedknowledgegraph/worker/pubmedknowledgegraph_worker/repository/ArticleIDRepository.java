package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys.ArticleIDKey;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.ArticleID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleIDRepository extends JpaRepository<ArticleID, ArticleIDKey> {
}
