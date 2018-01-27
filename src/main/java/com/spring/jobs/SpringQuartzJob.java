package com.spring.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SpringQuartzJob extends QuartzJobBean {

    private static Logger _log = LogManager.getLogger(SpringQuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        String paramString = context.getMergedJobDataMap().getString("param");
//        int param = Integer.parseInt(paramString);
        int param = context.getMergedJobDataMap().getInt("param");
        _log.info("SpringQuartzJob execute - {}", param);
        param++;
        context.getJobDetail().getJobDataMap().put("param", param);
    }
}
