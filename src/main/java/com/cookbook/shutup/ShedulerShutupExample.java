package com.cookbook.shutup;

import com.cookbook.standby.StandbyModeJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

// scheduler.shutdown(true);等待job完成后，函数才会返回
// scheduler.shutdown();不等job完成，直接返回
public class ShedulerShutupExample {

    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(StandbyModeJob.class)
                .withIdentity("job1")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger")
                .startAt(new Date())
                .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
//        scheduler.shutdown();
        scheduler.shutdown(true);
        System.out.println("scheduler close");
    }
}
