package com.example09;

import com.example01.SimpleExample;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

public class SimpleJob1 implements Job {

    private static Logger _log = LogManager.getLogger(SimpleJob1.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob1 says: " + jobKey + " executing at " + new Date());
    }
}
