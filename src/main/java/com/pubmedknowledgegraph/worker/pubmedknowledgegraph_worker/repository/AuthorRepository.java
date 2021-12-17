package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
