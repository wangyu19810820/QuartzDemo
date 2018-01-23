package com.example02;

import com.example02.SimpleJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 代码构建作业，触发器。打印作业将要执行的时间。作业执行时打印作业名（key），实际执行时间。
 * 1.指定时间执行作业（job1，job2，job5）
 * 2.指定时间执行作业，间隔指定时间后继续执行一定次数的作业。一个作业可以指定多个触发器（job3）
 * 3.指定时间执行作业，间隔指定时间后继续执行作业，并永远循环下去（job6）
 * 4.在scheduler开始运行之后，仍然能够新建作业，配置作业触发器，触发作业（job7）
 * 5.添加作业到scheduler中，不配置触发器，手动触发作业执行（job8）
 * 6.作业触发后，仍能改变作业的触发器，重新规划作业的执行时间，执行间隔（job7）
 *
 * 时间处理
 * 1.DateBuilder.nextGivenSecondDate(null, 15)：指定时间后，下一个整15秒的时间点
 * 2.DateBuilder.nextGivenMinuteDate(null, 15)：指定时间后，下一个整15分的时间点
 * 3.DateBuilder.futureDate(2, DateBuilder.IntervalUnit.MINUTE)：当前时间的2分钟后
 */
public class SimpleExample {

    private static Logger log = LogManager.getLogger(com.example01.SimpleExample.class);

    public void run() throws Exception {
        log.info("------- Initializing ----------------------");
        // 构建SchedulerFactory, Schedule
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        // 当前时间之后，整15秒的时间点（0秒，15秒，30秒，45秒）
        Date startDate = DateBuilder.nextGivenSecondDate(null, 15);

        log.info("------- Initialization Complete -----------");
        log.info("------- Scheduling Job  -------------------");
        // job1, job2, 在指定时间触发一次
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                                                             .withIdentity("triggle1", "group1")
                                                             .startAt(startDate)
                                                             .build();
        Date fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job2", "group1")
                              .build();
        trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                                               .withIdentity("triggle2", "group1")
                                               .startAt(startDate)
                                               .build();
        fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        // job3 运行6次，间隔10秒
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job3", "group1")
                              .build();
        trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                                               .withIdentity("triggle3", "group1")
                                               .startAt(startDate)
                                               .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5))
                                               .build();
        fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        // job3 间隔70秒后再运行2次
        trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                                               .withIdentity("triggle3", "group2")
                                               .startAt(startDate)
                                               .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(70).withRepeatCount(2))
                                               .forJob(jobDetail)
                                               .build();
        fireTime = scheduler.scheduleJob(trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        // job4运行状况同job1，group1，不重复写了
        // job5, 在2分钟后运行一次
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job5", "group1")
                              .build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                                                .withIdentity("triggle5", "group1")
                                                .startAt(DateBuilder.futureDate(2, DateBuilder.IntervalUnit.MINUTE))
                                                .build();
        fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        // job6，每隔30秒，一直运行下去
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job6", "group1")
                              .build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                                                .withIdentity("triggle6", "group1")
                                                .startAt(startDate)
                                                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever())
                                                .build();
        fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        log.info("------- Started Scheduler -----------------");
        // 运行Schedule
        scheduler.start();

        // job7，在scheduler开始运行之后，仍然能运行, 设置为间隔10秒
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job7", "group1")
                              .build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                                                .withIdentity("triggle7", "group1")
                                                .startAt(startDate)
                                                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                                                .build();
        fireTime = scheduler.scheduleJob(jobDetail, trigger);
        logJobAndTriggle(jobDetail, trigger, fireTime);

        // job8, 手动触发
        jobDetail = JobBuilder.newJob(SimpleJob.class)
                              .withIdentity("job8", "group1")
                              .storeDurably()
                              .build();
        scheduler.addJob(jobDetail, true);
        scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

        try {
            Thread.sleep(30L * 1000L);
        } catch (Exception e) {
        }
        log.info("------- Waiting 30 seconds... --------------");
        log.info("------- Rescheduling... --------------------");
        trigger = TriggerBuilder.newTrigger()
                                .withIdentity("trigger7", "group1")
                                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).withRepeatCount(2))
                                .build();
        fireTime = scheduler.rescheduleJob(trigger.getKey(), trigger);
        log.info("job7 rescheduled to run at: " + fireTime);

        // 主线程休息5分钟，观察打印结果
        try {
            Thread.sleep(2 * 60 * 1000L);
        } catch (Exception e) {
        }

        log.info("------- Shutdown Complete -----------------");
        // 关闭Schedule，显示元信息
        scheduler.shutdown();
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Execute {} jobs", metaData.getNumberOfJobsExecuted());
    }

    private void logJobAndTriggle(JobDetail jobDetail, SimpleTrigger trigger, Date fireTime) {
        log.info("{} will run at:{} and repeat:{} times, every {} seconds",
                jobDetail.getKey(), fireTime, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);
    }

    public static void main(String[] args) throws Exception {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.run();
    }
}
