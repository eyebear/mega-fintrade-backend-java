package com.ao.portfolio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchJobLoggingListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobLoggingListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Starting batch job: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        if (jobExecution.getStatus().isUnsuccessful()) {
            logger.error("Batch job failed: {}", jobName);

            jobExecution.getFailureExceptions().forEach(exception ->
                    logger.error("Batch job failure reason: {}", exception.getMessage(), exception)
            );
        } else {
            logger.info("Batch job completed successfully: {}", jobName);
        }
    }
}