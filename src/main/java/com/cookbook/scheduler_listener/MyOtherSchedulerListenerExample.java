package com.cookbook.scheduler_listener;

import com.example09.SimpleJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyOtherSchedulerListenerExample {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.getListenerManager().addSchedulerListener(new MyOtherSchedulerListener());

        // storeDurably不影响scheduler监听器的triggerFinalized方法被触发
        JobDetail job1 = newJob(SimpleJob1.class).withIdentity("job1", "group1")
                                                  .storeDurably()
                                                  .build();
        Trigger trigger1 = newTrigger().withIdentity("trigger1", "group1")
                                       .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                                          .withIntervalInSeconds(5)
                                                                          .withRepeatCount(1))
                                       .startNow()
                                       .build();
        scheduler.scheduleJob(job1, trigger1);

        scheduler.start();
//        try {
//            Thread.sleep(1 * 1000);
//        } catch (InterruptedException e) {
//        }
//        scheduler.deleteJob(JobKey.jobKey("job1", "group1"));
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
        }
        scheduler.shutdown();
    }
}
