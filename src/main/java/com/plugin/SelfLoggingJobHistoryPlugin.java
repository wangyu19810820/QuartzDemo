package com.plugin;

import com.example01.SimpleExample;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;

public class SelfLoggingJobHistoryPlugin implements SchedulerPlugin, JobListener {

    private static Logger _log = LogManager.getLogger(SelfLoggingJobHistoryPlugin.class);

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        _log.info("SelfLoggingJobHistoryPlugin jobToBeExecuted");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        _log.info("SelfLoggingJobHistoryPlugin jobWasExecuted");
    }

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        this.name = name;
        scheduler.getListenerManager().addJobListener(this, EverythingMatcher.allJobs());
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
