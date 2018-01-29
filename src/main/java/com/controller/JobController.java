package com.controller;

import com.example02.SimpleJob;
import com.spring.model.JobModel;
import com.spring.model.SysUser;
import com.spring.service.DemoService;
import org.apache.commons.beanutils.BeanUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private DemoService demoService;

    // job操作页面
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return "add";
    }

    // 添加job
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addSubmit(@RequestParam Map<String,String> map) throws Exception {
//        try {
//            addCronJob(map);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
        addJobByService(map);
        return "add";
    }

    // 删除job
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteSubmit(@RequestParam Map<String,String> map) throws Exception {
        deleteJobByService(map);
        return "add";
    }

    // 更新job触发条件
    @RequestMapping(value = "updateTrigger", method = RequestMethod.POST)
    public String updateTriggerSubmit(@RequestParam Map<String,String> map) throws Exception {
        updateTriggerByService(map);
        return "add";
    }

    private void updateTriggerByService(Map<String,String> map) throws Exception {
        JobModel jobModel = new JobModel();
        BeanUtils.populate(jobModel, map);
        demoService.updateTrigger(jobModel);
    }

    private void deleteJobByService(Map<String,String> map) throws Exception {
        JobModel jobModel = new JobModel();
        BeanUtils.populate(jobModel, map);
        demoService.deleteJob(jobModel);
    }

    private void addJobByService(Map<String,String> map) throws Exception {
        JobModel jobModel = new JobModel();
        BeanUtils.populate(jobModel, map);

        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setName("aa");
        demoService.addJob(jobModel, sysUser);
    }

    private void addCronJob(Map<String,String> map) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity((String)map.get("name"), (String)map.get("group"))
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger1", "group1")
                                        .withSchedule(CronScheduleBuilder.cronSchedule((String)map.get("cronTriggerExpr")))
                                        .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
