package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys.GrantAgencyKey;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.GrantAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantAgencyRepository extends JpaRepository<GrantAgency, GrantAgencyKey> {
}
