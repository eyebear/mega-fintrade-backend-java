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

import com.ao.portfolio.service.BacktestResultCsvImportService;

@Configuration
public class BacktestResultBatchConfig {

    @Bean
    public Job backtestResultImportJob(
            JobRepository jobRepository,
            Step backtestResultImportStep,
            BatchJobLoggingListener batchJobLoggingListener
    ) {
        return new JobBuilder("backtestResultImportJob", jobRepository)
                .listener(batchJobLoggingListener)
                .start(backtestResultImportStep)
                .build();
    }

    @Bean
    public Step backtestResultImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            BacktestResultCsvImportService backtestResultCsvImportService
    ) {
        return new StepBuilder("backtestResultImportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int rowsImported = backtestResultCsvImportService.importDefaultFile();

                    contribution.getStepExecution()
                            .getExecutionContext()
                            .putInt("backtestResultRowsImported", rowsImported);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}