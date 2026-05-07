package com.ao.portfolio.service;

import java.time.LocalDateTime;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.BatchRunSummary;

@Service
public class QuantBatchRunService {

    private final JobLauncher jobLauncher;
    private final Job riskMetricImportJob;
    private final Job backtestResultImportJob;
    private final Job strategySignalImportJob;
    private final Job portfolioEquityImportJob;

    public QuantBatchRunService(
            JobLauncher jobLauncher,
            Job riskMetricImportJob,
            Job backtestResultImportJob,
            Job strategySignalImportJob,
            Job portfolioEquityImportJob
    ) {
        this.jobLauncher = jobLauncher;
        this.riskMetricImportJob = riskMetricImportJob;
        this.backtestResultImportJob = backtestResultImportJob;
        this.strategySignalImportJob = strategySignalImportJob;
        this.portfolioEquityImportJob = portfolioEquityImportJob;
    }

    public BatchRunSummary runAllQuantImportJobs() {
        LocalDateTime startedAt = LocalDateTime.now();

        try {
            JobExecution riskMetricExecution = runJob(riskMetricImportJob);
            JobExecution backtestResultExecution = runJob(backtestResultImportJob);
            JobExecution strategySignalExecution = runJob(strategySignalImportJob);
            JobExecution portfolioEquityExecution = runJob(portfolioEquityImportJob);

            LocalDateTime finishedAt = LocalDateTime.now();

            String overallStatus = determineOverallStatus(
                    riskMetricExecution,
                    backtestResultExecution,
                    strategySignalExecution,
                    portfolioEquityExecution
            );

            return new BatchRunSummary(
                    overallStatus,
                    startedAt,
                    finishedAt,
                    riskMetricExecution.getStatus().toString(),
                    backtestResultExecution.getStatus().toString(),
                    strategySignalExecution.getStatus().toString(),
                    portfolioEquityExecution.getStatus().toString()
            );
        } catch (Exception exception) {
            LocalDateTime finishedAt = LocalDateTime.now();

            return new BatchRunSummary(
                    "FAILED: " + exception.getMessage(),
                    startedAt,
                    finishedAt,
                    "UNKNOWN",
                    "UNKNOWN",
                    "UNKNOWN",
                    "UNKNOWN"
            );
        }
    }

    private JobExecution runJob(Job job) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("runTimestamp", System.currentTimeMillis())
                .toJobParameters();

        return jobLauncher.run(job, jobParameters);
    }

    private String determineOverallStatus(
            JobExecution riskMetricExecution,
            JobExecution backtestResultExecution,
            JobExecution strategySignalExecution,
            JobExecution portfolioEquityExecution
    ) {
        boolean allCompleted =
                riskMetricExecution.getStatus().isUnsuccessful() == false
                        && backtestResultExecution.getStatus().isUnsuccessful() == false
                        && strategySignalExecution.getStatus().isUnsuccessful() == false
                        && portfolioEquityExecution.getStatus().isUnsuccessful() == false;

        return allCompleted ? "COMPLETED" : "FAILED";
    }
}