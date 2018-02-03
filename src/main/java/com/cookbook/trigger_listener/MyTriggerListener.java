package com.cookbook.trigger_listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class MyTriggerListener implements TriggerListener {

    private static Logger _log = LogManager.getLogger(MyTriggerListener.class);

    private String name;

    public MyTriggerListener() {
        this.name = "MyTriggerListener";
    }

    public MyTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    // Job上的execute()方法将要被执行时，包括JobExecutionException的setRefireImmediately产生的重新触发
    // Scheduler都将调用这个方法
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        _log.info("MyTriggerListener triggerFired key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }

    // Job上的execute()方法将要被执行时，包括JobExecutionException的setRefireImmediately产生的重新触发
    // Trigger发起一个投票，决定是否要执行此Job
    // 返回true，Job将不会被执行。返回false，Job将被执行。
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        _log.info("MyTriggerListener vetoJobExecution key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
        return false;
    }

    // Trigger 错过触发时，这个方法被调用,超时时间需要超过阈值
    // 应当保持这个方法尽量的小
    @Override
    public void triggerMisfired(Trigger trigger) {
        _log.info("MyTriggerListener triggerMisfired key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }

    // Job被触发且执行结束后，无论是正常结束，还是抛异常结束，Scheduler都将调用这个方法
    @Override
    public void triggerComplete(Trigger trigger,
                                JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        _log.info("MyTriggerListener triggerComplete key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }
}
