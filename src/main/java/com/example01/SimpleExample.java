package com.example01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Quartz通常由作业内容，作业触发器，调度器三部分组成（也可手动触发，example02有例子）
 * 调度器由SchedulerFactory的getScheduler()获得
 * 作业内容由JobBuilder的build获得
 * 作业触发器由TriggerBuilder的build获得
 * 调度器的scheduleJob()配置作业和触发器
 * 调度器的start()开始运行调度，满足触发条件就执行作业。shutdown()结束调度，作业不会再被触发。
 *
 * evenMinuteDate,指定时间的下一个整分钟
 */
public class SimpleExample {

    private static Logger log = LogManager.getLogger(SimpleExample.class);

    public void run() throws Exception {
        log.info("------- Initializing ----------------------");
        // init SchedulerFactory, Scheduler, runTime
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        Date date = DateBuilder.evenMinuteDate(new Date());

        log.info("------- Initialization Complete -----------");
        log.info("------- Scheduling Job  -------------------");
        // JobDetail, Trigger, scheduleJob
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                                         .withIdentity("trigger1", "group1")
//                                         .startAt(DateBuilder.futureDate(2, DateBuilder.IntervalUnit.MINUTE))     // 两分钟后触发
                                         .startAt(date)
                                         .build();
        scheduler.scheduleJob(jobDetail, trigger1);

        log.info("------- Started Scheduler -----------------");
        // start, shutdown after 15 seconds
        scheduler.start();
        try {
            Thread.sleep(65 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
        log.info("------- Started shutdown -----------------");
        try {
            Thread.sleep(3 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.run();
    }
}
