package com.servlet;

import com.example01.HelloJob;
import com.example02.SimpleJob;
import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AddJobServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String s = request.getParameter("param");
        System.out.println(s);
        try {
//            String key = "org.quartz.impl.StdSchedulerFactory.KEY";
            String key = QuartzInitializerListener.QUARTZ_FACTORY_KEY;
            ServletContext ctx = request.getServletContext();
            StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(key);
            Scheduler scheduler = factory.getScheduler();
//            addInterruptJob(scheduler);
            addCronJob(scheduler);
        } catch (SchedulerException e) {
        }
    }

    public void addCronJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger1", "group1")
                                        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                                        .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void addInterruptJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(InterruptJob.class)
                                        .withIdentity("job1", "group1")
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger1", "group1")
                                        .startNow()
                                        .build();
        jobDetail.getJobDataMap().put("param", "value1");
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
