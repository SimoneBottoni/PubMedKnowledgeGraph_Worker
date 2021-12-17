package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util.Config;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableAdminServer
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableBatchProcessing
@EnableBatchIntegration
public class PubMedKnowledgeGraphWorkerApplication {

    public static void main(String[] args) {
        Config config = new Config();
        config.initConfig();
        SpringApplication.run(PubMedKnowledgeGraphWorkerApplication.class, args);
    }

}
