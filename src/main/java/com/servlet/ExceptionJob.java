package com.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

/**
 * 本例意义不大
 * 运行出错后，抛出JobExecutionException，异常setRefireImmediately(true)
 * 就可以重新运行该Job
 * 具体可参看我抄录的Quartz官方example06的BadJob1,BadJob2,
 * 设计了三种出错逻辑：立即重试，取消触发器，取消所有触发器
 */
public class ExceptionJob implements Job {

    private static Logger _log = LogManager.getLogger(ExceptionJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            exec(context);
        } catch (Exception e) {
            JobExecutionException e2 = new JobExecutionException(e);
            // true 表示 Quartz 会自动取消所有与这个 job 有关的 trigger，从而避免再次运行 job
            e2.setRefireImmediately(true);
            throw e2;
        }
    }

    public void exec(JobExecutionContext context) {
        String value = (String)context.getMergedJobDataMap().get("param");
        _log.info("ExceptionJob step 1:{}", value);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        // 一个异常例子
        int zero = 0;
        int calculation = 4815 / zero;
    }

//    @Override
//    public void interrupt() throws UnableToInterruptJobException {
//        System.out.println("interrupt");
//    }
}
