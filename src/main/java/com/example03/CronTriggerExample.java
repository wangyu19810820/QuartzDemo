package com.example03;

import com.example02.SimpleJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class CronTriggerExample {

    private static Logger log = LogManager.getLogger(CronTriggerExample.class);

    public void run() throws Exception {
        log.info("------- Initializing -------------------");
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        log.info("------- Initialization Complete --------");
        log.info("------- Scheduling Jobs ----------------");
        // 8点到17点，每2分钟的15秒
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity("trigger1", "group1")
                                            .withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 8-17 * * ?"))
                                            .build();
        Date fireDate = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireDate);

        // 周一到周五，早上10点35分
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job2", "group1")
                              .build();
        trigger = TriggerBuilder.newTrigger()
                                .withIdentity("trigger2", "group1")
                                .withSchedule(CronScheduleBuilder.cronSchedule("0 0,35 10am ? * MON-FRI"))
                                .build();
        fireDate = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireDate);

        log.info("------- Started Scheduler -----------------");
        scheduler.start();
        try {
            Thread.sleep(2 * 60 * 1000);
        } catch (InterruptedException e) {
        }
        scheduler.shutdown();

        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Execute {} jobs", metaData.getNumberOfJobsExecuted());
    }

    private void logJobAndTriggle(JobDetail jobDetail, CronTrigger trigger, Date fireTime) {
        log.info("{} will run at:{} and repeat based on expression: {}",
                jobDetail.getKey(), fireTime, trigger.getCronExpression());
    }

    public static void main(String[] args) throws Exception {
        CronTriggerExample example = new CronTriggerExample();
        example.run();
    }
}
