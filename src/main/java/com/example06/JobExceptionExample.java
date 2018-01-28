package com.example06;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

// 出错后，job捕获异常并且重新抛出JobExecutionException就可以控制后续处理过程
// e2.setRefireImmediately(true);可以立即重新执行该job
// e2.setUnscheduleFiringTrigger(true);取消此job的当前触发器
// e2.setUnscheduleAllTriggers(true);取消此job的所有触发器
public class JobExceptionExample {

  private static Logger log = LogManager.getLogger(JobExceptionExample.class);

  public void run() throws Exception {

    log.info("------- Initializing ----------------------");

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();

    log.info("------- Initialization Complete ------------");

    log.info("------- Scheduling Jobs -------------------");

    // jobs can be scheduled before start() has been called

    // get a "nice round" time a few seconds in the future...
    Date startTime = nextGivenSecondDate(null, 15);

    // badJob1 运行间隔10秒无限触发，出错后会立即重试
    // badJob1 内部是第一次执行会有除0的异常，后续执行并不会有异常发生
    JobDetail job = newJob(BadJob1.class).withIdentity("badJob1", "group1")
                                          .usingJobData("denominator", "0")
                                          .build();

    SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
                                        .startAt(startTime)
                                        .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
                                        .build();

    Date ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
             + trigger.getRepeatInterval() / 1000 + " seconds");

    // badJob2 运行间隔10秒无限触发，出错后会放弃所有触发
    // badJob2 内部是第一次执行会有除0的异常，后续执行并不会有异常发生
    job = newJob(BadJob2.class).withIdentity("badJob2", "group1").storeDurably().build();
    sched.addJob(job, false);

    trigger = newTrigger().withIdentity("trigger2", "group1")
                          .startAt(startTime)
                          .withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever())
                          .forJob(job)
                          .build();

    ft = sched.scheduleJob(trigger);
    log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
             + trigger.getRepeatInterval() / 1000 + " seconds");
    trigger = newTrigger().withIdentity("trigger3", "group1")
            .startAt(startTime)
            .forJob(job)
            .withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever())
            .build();

    ft = sched.scheduleJob(trigger);
    log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
            + trigger.getRepeatInterval() / 1000 + " seconds");

    log.info("------- Starting Scheduler ----------------");

    // jobs don't start firing until start() has been called...
    sched.start();

    log.info("------- Started Scheduler -----------------");

    try {
      // sleep for 30 seconds
      Thread.sleep(30L * 1000L);
    } catch (Exception e) {
      //
    }

    log.info("------- Shutting Down ---------------------");

    sched.shutdown(false);

    log.info("------- Shutdown Complete -----------------");

    SchedulerMetaData metaData = sched.getMetaData();
    log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
  }

  public static void main(String[] args) throws Exception {

    JobExceptionExample example = new JobExceptionExample();
    example.run();
  }

}
