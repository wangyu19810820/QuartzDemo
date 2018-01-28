package com.spring.service.impl;

import com.example02.SimpleJob;
import com.spring.dao.SysUserDao;
import com.spring.model.JobModel;
import com.spring.model.SysUser;
import com.spring.service.DemoService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private Scheduler scheduler;

    @Transactional
    public void exec(Map<String, String> map) {
        System.out.println("DemoService exec");
    }

    @Transactional
    public void addJob(JobModel jobModel, SysUser sysUser) {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                                        .withIdentity(jobModel.getName(), jobModel.getGroup())
                                        .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("trigger1", "group1")
                                        .withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCronTriggerExpr()))
                                        .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("add job exception");
        }

        sysUserDao.insert(sysUser);
    }
}
