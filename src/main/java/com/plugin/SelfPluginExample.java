package com.plugin;

import com.example09.SimpleJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class SelfPluginExample {

    public static void main(String[] args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory("quartz3.properties");
        Scheduler scheduler = factory.getScheduler();
        JobDetail job1 = newJob(SimpleJob1.class).withIdentity("job1", "group1").build();
        Trigger trigger1 = newTrigger().withIdentity("trigger1", "group1")
                                       .startNow()
                                       .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                                          .withIntervalInSeconds(1)
                                                                          .repeatForever())
                                       .build();
        scheduler.scheduleJob(job1, trigger1);
        scheduler.start();

        Thread.sleep(1000);
        scheduler.shutdown();
    }
}
