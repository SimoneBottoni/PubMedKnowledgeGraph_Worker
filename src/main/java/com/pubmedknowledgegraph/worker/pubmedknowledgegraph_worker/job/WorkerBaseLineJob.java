package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.job;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParseAnnotateService;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util.Util;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

@Configuration
public class WorkerBaseLineJob {

    @Value("${data.folder.baseline}")
    private String pubMedFolder = "PubMed/BaseLine/";

    private final WebApplicationContext context;
    private final RemotePartitioningWorkerStepBuilderFactory remotePartitioningWorkerStepBuilderFactory;

    @Autowired
    public WorkerBaseLineJob(WebApplicationContext context, RemotePartitioningWorkerStepBuilderFactory remotePartitioningWorkerStepBuilderFactory) {
        this.context = context;
        this.remotePartitioningWorkerStepBuilderFactory = remotePartitioningWorkerStepBuilderFactory;
    }

    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow(AmqpTemplate amqpTemplate) {
        return IntegrationFlows
                .from(replies())
                .handle(Amqp.outboundAdapter(amqpTemplate).routingKey("replies"))
                .get();
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Amqp.inboundAdapter(connectionFactory, "requests")
                        .configureContainer(c -> c.prefetchCount(1).defaultRequeueRejected(false))
                ).channel(requests())
                .get();
    }

    @Bean(name = "workerBaseLineStep")
    public Step workerStep() {
        return this.remotePartitioningWorkerStepBuilderFactory.get("workerBaseLineStep")
                .inputChannel(requests())
                .outputChannel(replies())
                .tasklet(task(null))
                .build();
    }

    @Bean(name = "workerBaseLineTask")
    @StepScope
    public Tasklet task(@Value("#{stepExecutionContext[fileName]}") String fileName) {
        return (stepContribution, chunkContext) -> {
            Util util = new Util();
            File file = new File(pubMedFolder + File.separator + fileName);
            if (!file.exists()) {
                String check = util.downloadBaseline(pubMedFolder, fileName);
                if (check.equals("")) {
                    throw new Exception("Can't download " + fileName + ".");
                }
            }
            ParseAnnotateService parseAnnotateService = (ParseAnnotateService) context.getBean("parseAnnotateService");
            parseAnnotateService.setPubMedFile(false, file);
            parseAnnotateService.run();
            return RepeatStatus.FINISHED;
        };
    }
}
