package com.example09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Job监听器的例子
 * Trigger监听器和Scheduler监听器可以参考com.cookbook里面的例子
 */
public class ListenerExample {

    private static Logger log = LogManager.getLogger(Job1Listener.class);

    public void run() throws Exception {
        log.info("------- Initializing ----------------------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        log.info("------- Scheduling Jobs -------------------");
        JobDetail job1 = newJob(SimpleJob1.class).withIdentity("job1", "group1").build();
        Trigger trigger1 = newTrigger().withIdentity("trigger1").startNow().build();

        JobDetail job2 = newJob(SimpleJob1.class).withIdentity("job2", "group1").build();
        Trigger trigger2 = newTrigger().withIdentity("trigger2").startNow().build();

        JobListener listener = new Job1Listener();
//        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job1.getKey());
//        Matcher<JobKey> matcher = GroupMatcher.groupEquals("group1");
//        Matcher<JobKey> matcher = KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1"));
        Matcher<JobKey> matcher = EverythingMatcher.allJobs();
        scheduler.getListenerManager().addJobListener(listener, matcher);

        scheduler.scheduleJob(job1, trigger1);
        scheduler.scheduleJob(job2, trigger2);
        scheduler.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        scheduler.shutdown();
    }

    public static void main(String[] args) throws Exception {
        ListenerExample example = new ListenerExample();
        example.run();
    }
}
