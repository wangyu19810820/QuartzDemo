package com.example04;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * JobDetail和Trigger都可以向Job传递参数
 * 两者传递的参数重名了，trigger的优先级高
 * Job中通过getMergedJobDataMap接收所有参数
 */
public class DumbJobExample {

    public void run() throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(DumbJob.class)
                .withIdentity("job1", "group1")
                .build();
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4))
                .build();
        jobDetail.getJobDataMap().put("jobSays", "red");
        jobDetail.getJobDataMap().put("myFloatValue", 3);
        trigger.getJobDataMap().put("jobSays", "green");
        trigger.getJobDataMap().put("myFloatValue", 2);
        trigger.getJobDataMap().put("state", new ArrayList<>());
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) throws Exception {
        DumbJobExample example = new DumbJobExample();
        example.run();
    }
}
