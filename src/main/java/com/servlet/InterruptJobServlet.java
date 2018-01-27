package com.servlet;

import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InterruptJobServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("InterruptJobServlet");
        try {
            String key = QuartzInitializerListener.QUARTZ_FACTORY_KEY;
            ServletContext ctx = request.getServletContext();
            StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(key);
            Scheduler scheduler = factory.getScheduler();
            scheduler.interrupt(JobKey.jobKey("job1", "group1"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
