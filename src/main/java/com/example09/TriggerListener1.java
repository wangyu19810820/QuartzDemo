package com.example09;

import org.quartz.listeners.TriggerListenerSupport;

public class TriggerListener1 extends TriggerListenerSupport {

    @Override
    public String getName() {
        return "job1_to_job2";
    }
}
