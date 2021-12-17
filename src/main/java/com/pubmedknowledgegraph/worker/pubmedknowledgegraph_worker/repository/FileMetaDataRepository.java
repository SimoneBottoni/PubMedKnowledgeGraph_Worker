package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.metadata.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {

    @Query("SELECT fm.fileName FROM FileMetaData fm")
    List<String> getAll();

}
