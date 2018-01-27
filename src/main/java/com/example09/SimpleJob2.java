package com.example09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

public class SimpleJob2 {

    private static Logger _log = LogManager.getLogger(SimpleJob2.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob2 says: " + jobKey + " executing at " + new Date());
    }
}
