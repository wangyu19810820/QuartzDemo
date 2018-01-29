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

    // 当与监听器相关联的 Trigger 被触发，Job 上的 execute() 方法将要被执行时，Scheduler 就调用这个方法
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        _log.info("MyTriggerListener triggerFired key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }

    // 在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法。
    // TriggerListener 给了一个选择去否决 Job 的执行。
    // 假如这个方法返回 true，这个 Job 将不会为此次 Trigger 触发而得到执行。
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        _log.info("MyTriggerListener vetoJobExecution key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
        return false;
    }

    // Scheduler 调用这个方法是在 Trigger 错过触发时
    // 应当保持这个方法尽量的小
    @Override
    public void triggerMisfired(Trigger trigger) {
        _log.info("MyTriggerListener triggerMisfired key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }

    // Trigger 被触发并且完成了 Job 的执行时，Scheduler 调用这个方法
    @Override
    public void triggerComplete(Trigger trigger,
                                JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        _log.info("MyTriggerListener triggerComplete key:{}, group:{}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }
}
