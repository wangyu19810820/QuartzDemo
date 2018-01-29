package com.example09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class Job1Listener implements JobListener {

    private static Logger _log = LogManager.getLogger(Job1Listener.class);
    private String name = "job1";

    public Job1Listener() {
        System.out.println("Job1Listener init");
    }

    @Override
    public String getName() {
        return name;
    }

    // Scheduler 在 JobDetail 将要被执行时调用这个方法
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        _log.info("JobExecutionContext says: Job {} Is about to be executed.", context.getJobDetail().getKey());
    }

    // Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        _log.info("Job1Listener says: Job Execution {} was vetoed.", context.getJobDetail().getKey());
    }

    // Scheduler 在 JobDetail 被执行之后调用这个方法
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        _log.info("Job1Listener says: Job {} was executed.", context.getJobDetail().getKey());
    }

    public void setName(String name) {
        this.name = name;
    }


}
