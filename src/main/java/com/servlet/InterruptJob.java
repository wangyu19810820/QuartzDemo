package com.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public class InterruptJob implements InterruptableJob {

    private static Logger _log = LogManager.getLogger(ExceptionJob.class);
    private boolean interrupt = false;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        _log.info("InterruptJob interrupt");
        interrupt = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        while (true) {
            if (interrupt) {
                return;
            }
            _log.info("InterruptJob execute");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
