package com.plugin;

import com.example09.SimpleJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 插件的例子
 * 要在quartz3.properties中配置插件，插件要实现initialize，start，shutdown方法
 * 官方插件，一个是记录日志插件，一个从xml中加载job的插件，一个是关闭jvm前执行schedule的shutdown的插件
 */
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
