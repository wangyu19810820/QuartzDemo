package com.controller;

import com.example02.SimpleJob;
import com.spring.service.DemoService;
import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.Map;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addSubmit(@RequestParam Map<String,String> map) {
        try {
            addCronJob(map);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "add";
    }

    public void addCronJob(Map<String,String> map) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity((String)map.get("name"), (String)map.get("group"))
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger1", "group1")
                                        .withSchedule(CronScheduleBuilder.cronSchedule((String)map.get("trigger")))
                                        .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
