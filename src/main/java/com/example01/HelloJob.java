package com.example01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

import static java.lang.Thread.sleep;

public class HelloJob implements Job {

    private static Logger _log = LogManager.getLogger(SimpleExample.class);

    public HelloJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        while (true) {
            // Say Hello to the World and display the date/time
            _log.info("Hello World! - " + new Date());
            try {
                sleep(550);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
