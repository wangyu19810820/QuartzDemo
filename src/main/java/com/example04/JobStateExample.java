package com.example04;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class JobStateExample {

    private static Logger log = LogManager.getLogger(JobStateExample.class);

    public void run() throws Exception {
        log.info("------- Initializing -------------------");
        SchedulerFactory factory = new StdSchedulerFactory("quartz1.properties");
        Scheduler scheduler = factory.getScheduler();
        Date startDate = DateBuilder.nextGivenSecondDate(null, 10);

        log.info("------- Scheduling Jobs ----------------");
        JobDetail jobDetail = JobBuilder.newJob(ColorJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                                                             .withIdentity("trigger1", "group1")
                                                             .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4))
                                                             .build();
        jobDetail.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "red");
        jobDetail.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        Date schedulerDate1 = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, schedulerDate1);

        JobDetail jobDetail2 = JobBuilder.newJob(ColorJob.class)
                                         .withIdentity("job2", "group1")
                                         .build();
        SimpleTrigger trigger2 = (SimpleTrigger)TriggerBuilder.newTrigger()
                                                              .withIdentity("trigger2", "group1")
                                                              .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                                                              .build();
        jobDetail2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "green");
        jobDetail2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
//        Date schedulerDate2 = scheduler.scheduleJob(jobDetail2, trigger2);
//        logJobAndTriggle(jobDetail2, trigger2, schedulerDate2);

        log.info("------- Starting Scheduler ----------------");
        scheduler.start();
        try {
            Thread.sleep(120L * 1000L);
        } catch (Exception e) {
        }
        scheduler.shutdown();

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        JobStateExample jobStateExample = new JobStateExample();
        jobStateExample.run();
    }

    private void logJobAndTriggle(JobDetail jobDetail, SimpleTrigger trigger, Date fireTime) {
        log.info("{} will run at:{} and repeat:{} times, every {} seconds",
                jobDetail.getKey(), fireTime, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);
    }

}
