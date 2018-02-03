package com.cookbook.trigger_listener;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class TriggerListenerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("TriggerListenerJob begin");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
//        try {
//            int i = 0;
//            int j = 10 / i;
//        } catch (Exception e) {
//            JobExecutionException e2 = new JobExecutionException(e);
//            e2.setRefireImmediately(true);
//            throw e2;
//        }
        System.out.println("TriggerListenerJob end");
    }
}
