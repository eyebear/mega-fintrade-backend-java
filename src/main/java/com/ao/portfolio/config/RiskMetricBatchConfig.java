package com.ao.portfolio.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ao.portfolio.service.RiskMetricCsvImportService;

@Configuration
public class RiskMetricBatchConfig {

    @Bean
    public Job riskMetricImportJob(
            JobRepository jobRepository,
            Step riskMetricImportStep,
            BatchJobLoggingListener batchJobLoggingListener
    ) {
        return new JobBuilder("riskMetricImportJob", jobRepository)
                .listener(batchJobLoggingListener)
                .start(riskMetricImportStep)
                .build();
    }

    @Bean
    public Step riskMetricImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            RiskMetricCsvImportService riskMetricCsvImportService
    ) {
        return new StepBuilder("riskMetricImportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int rowsImported = riskMetricCsvImportService.importDefaultFile();

                    contribution.getStepExecution()
                            .getExecutionContext()
                            .putInt("riskMetricRowsImported", rowsImported);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}