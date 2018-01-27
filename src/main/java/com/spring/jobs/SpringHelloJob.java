package com.spring.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import static java.lang.Thread.sleep;

public class SpringHelloJob {

    private static Logger _log = LogManager.getLogger(SpringHelloJob.class);

    public SpringHelloJob() {
    }

    public void execute() {

//        while (true) {
            // Say Hello to the World and display the date/time
            _log.info("SpringHelloJob! - " + new Date());
//            try {
//                sleep(550);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
