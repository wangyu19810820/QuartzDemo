package com.spring.service.impl;

import com.spring.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    public void exec(Map<String, String> map) {
        System.out.println("DemoService exec");
    }
}
