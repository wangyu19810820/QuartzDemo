package com.cookbook.scheduler_listener;

import com.cookbook.trigger_listener.MyTriggerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;

public class MyOtherSchedulerListener extends SchedulerListenerSupport {

    private static Logger _log = LogManager.getLogger(MyOtherSchedulerListener.class);

    @Override
    public void schedulerStarted() {
        _log.info("MyOtherSchedulerListener schedulerStarted");
    }

    @Override
    public void schedulerShutdown() {
        _log.info("MyOtherSchedulerListener schedulerShutdown");
    }

    // Scheduler 在有新的 JobDetail 部署时调用此方法
    @Override
    public void jobScheduled(Trigger trigger) {
        _log.info("MyOtherSchedulerListener jobScheduled key:{}, group: {}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }

    // Scheduler 在有新的 JobDetail卸载时调用此方法，
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        _log.info("MyOtherSchedulerListener jobUnscheduled key:{}, group: {}",
                triggerKey.getName(), triggerKey.getGroup());
    }

    // 当一个 Trigger 来到了再也不会触发的状态时调用这个方法
    @Override
    public void triggerFinalized(Trigger trigger) {
        _log.info("MyOtherSchedulerListener triggerFinalized key:{}, group: {}",
                trigger.getKey().getName(), trigger.getKey().getGroup());
    }
}
