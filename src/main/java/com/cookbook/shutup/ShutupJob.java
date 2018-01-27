package com.cookbook.shutup;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ShutupJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("ShutupJob start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("ShutupJob end");
    }
}
