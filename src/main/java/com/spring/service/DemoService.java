package com.spring.service;

import com.spring.model.JobModel;
import com.spring.model.SysUser;

import java.util.Map;

public interface DemoService {

    void exec(Map<String, String> map);
    void addJob(JobModel jobModel, SysUser sysUser);
}
