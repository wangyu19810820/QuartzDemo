package com.cookbook.job_store_tx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * job和trigger可以保存到数据库中，配置参考quartz官方文档，官方demo
 * job的实现类上需要有@PersistJobDataAfterExecution注解
 * 需要通过context.getJobDetail().getJobDataMap().put("value", value);保存数据
 * 而不能通过context.getMergedJobDataMap().put("value", value);保存数据
 * 才能将job参数保存到数据库中
 * Simple触发器和Cron触发器都能保存job，触发器，job参数到数据库中
 */
public class JobStoreTXExample {

    public static void main(String[] args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory("quartz1.properties");
        Scheduler scheduler = factory.getScheduler();
        scheduler.clear();
        JobDetail jobDetail = JobBuilder.newJob(StoreTxJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        jobDetail.getJobDataMap().put("value", 1);
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
                                        .startNow()
                                        .build();
        Date fd = scheduler.scheduleJob(jobDetail, trigger);
        System.out.println(fd);

        scheduler.start();

        try {
            Thread.sleep(12000 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.shutdown();
    }
}
