package com.cookbook.standby;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class StandbyModeExample {

    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(StandbyModeJob.class)
                                        .withIdentity("job1")
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger")
                                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                                           .withIntervalInSeconds(1)
                                                                           .repeatForever())
                                        .startAt(new Date())
                                        .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("scheduler standby");
        scheduler.standby();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("scheduler start");
        scheduler.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        scheduler.shutdown();

        System.out.println("scheduler close");
    }
}
