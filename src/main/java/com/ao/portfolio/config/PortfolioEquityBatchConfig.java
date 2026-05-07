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

import com.ao.portfolio.service.PortfolioEquityCsvImportService;

@Configuration
public class PortfolioEquityBatchConfig {

    @Bean
    public Job portfolioEquityImportJob(
            JobRepository jobRepository,
            Step portfolioEquityImportStep,
            BatchJobLoggingListener batchJobLoggingListener
    ) {
        return new JobBuilder("portfolioEquityImportJob", jobRepository)
                .listener(batchJobLoggingListener)
                .start(portfolioEquityImportStep)
                .build();
    }

    @Bean
    public Step portfolioEquityImportStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            PortfolioEquityCsvImportService portfolioEquityCsvImportService
    ) {
        return new StepBuilder("portfolioEquityImportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int rowsImported = portfolioEquityCsvImportService.importDefaultFile();

                    contribution.getStepExecution()
                            .getExecutionContext()
                            .putInt("portfolioEquityRowsImported", rowsImported);

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}