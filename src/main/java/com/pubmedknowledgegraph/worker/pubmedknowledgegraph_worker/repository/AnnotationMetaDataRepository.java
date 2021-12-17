package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.AnnotationMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnotationMetaDataRepository extends JpaRepository<AnnotationMetaData, Long> {

    @Query("SELECT am.fileName FROM AnnotationMetaData am")
    List<String> getAll();

}
