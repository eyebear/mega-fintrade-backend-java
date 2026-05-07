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

import com.ao.portfolio.service.StrategySignalCsvImportService;

@Configuration
public class StrategySignalBatchConfig {

    @Bean
    public Job strategySignalImportJob(
            JobRepository jobRepository,
            Step strategySignalImportStep,
            BatchJobLoggingListener batchJobLoggingListener
    ) {
        return new JobBuilder("strategySignalImportJob", jobRepository)
                .listener(batchJobLoggingListener)
                .start(strategySignalImportStep)
                .build();
    }

    @Bean
    public Step strategySignalImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            StrategySignalCsvImportService strategySignalCsvImportService
    ) {
        return new StepBuilder("strategySignalImportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int rowsImported = strategySignalCsvImportService.importDefaultFile();

                    contribution.getStepExecution()
                            .getExecutionContext()
                            .putInt("strategySignalRowsImported", rowsImported);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}