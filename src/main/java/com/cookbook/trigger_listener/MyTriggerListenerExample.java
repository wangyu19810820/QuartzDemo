package com.cookbook.trigger_listener;

import com.example09.SimpleJob1;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.EverythingMatcher.allTriggers;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

public class MyTriggerListenerExample {

    public static void main(String[] args) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        JobDetail job1 = newJob(SimpleJob1.class).withIdentity("job1", "group1").build();
        Trigger trigger1 = newTrigger().withIdentity("trigger1", "group1").startNow().build();
        scheduler.scheduleJob(job1, trigger1);

        JobDetail job2 = newJob(SimpleJob1.class).withIdentity("job2", "group1").build();
        Trigger trigger2 = newTrigger().withIdentity("trigger2", "group2").startNow().build();
        scheduler.scheduleJob(job2, trigger2);

        MyTriggerListener listener = new MyTriggerListener("MyTriggerListener");
        scheduler.getListenerManager().addTriggerListener(listener, EverythingMatcher.allTriggers());
        scheduler.getListenerManager().addTriggerListener(
                listener, KeyMatcher.keyEquals(triggerKey("trigger1", "group1")));
        scheduler.getListenerManager().addTriggerListener(
                listener, GroupMatcher.groupEquals("group1"));

        scheduler.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        scheduler.shutdown();
    }
}
