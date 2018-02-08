package com.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SpringInitBean implements InitializingBean {

    public void afterPropertiesSet() throws Exception {
        System.out.println("SpringInitBean");
    }
}
