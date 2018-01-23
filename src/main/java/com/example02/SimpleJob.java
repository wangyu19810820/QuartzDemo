package com.example02;

import com.example01.SimpleExample;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SimpleJob implements Job {

    private static Logger _log = LogManager.getLogger(SimpleExample.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob says: " + jobKey + " execute at "
                + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
