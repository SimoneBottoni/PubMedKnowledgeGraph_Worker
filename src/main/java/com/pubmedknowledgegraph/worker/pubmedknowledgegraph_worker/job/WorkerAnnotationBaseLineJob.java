package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.job;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service.ParseAnnotateAfterService;
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

@Configuration
public class WorkerAnnotationBaseLineJob {

    @Value("${data.folder.baseline}")
    private String pubMedFolder = "PubMed/BaseLine/";

    private final WebApplicationContext context;
    private final RemotePartitioningWorkerStepBuilderFactory remotePartitioningWorkerStepBuilderFactory;

    @Autowired
    public WorkerAnnotationBaseLineJob(WebApplicationContext context, RemotePartitioningWorkerStepBuilderFactory remotePartitioningWorkerStepBuilderFactory) {
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

    @Bean(name = "workerAnnotationBaseLineStep")
    public Step workerStep() {
        return this.remotePartitioningWorkerStepBuilderFactory.get("workerAnnotationBaseLineStep")
                .inputChannel(requests())
                .outputChannel(replies())
                .tasklet(task(null))
                .build();
    }

    @Bean(name = "workerAnnotationBaseLineTask")
    @StepScope
    public Tasklet task(@Value("#{stepExecutionContext[fileName]}") String fileName) {
        return (stepContribution, chunkContext) -> {
            ParseAnnotateAfterService parseAnnotateAfterService = (ParseAnnotateAfterService) context.getBean("parseAnnotateAfterService");
            parseAnnotateAfterService.setPubMedFile(fileName.substring(0, fileName.lastIndexOf(".")));
            parseAnnotateAfterService.run();
            return RepeatStatus.FINISHED;
        };
    }
}
