package com.cookbook.job_store_tx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

import java.util.Date;

import static java.lang.Thread.sleep;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StoreTxJob implements Job {

    private static Logger _log = LogManager.getLogger(StoreTxJob.class);

    public StoreTxJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            int value = context.getMergedJobDataMap().getInt("value");
            _log.info("StoreTxJob! - {} - {}", new Date(), value);
            value++;
            context.getJobDetail().getJobDataMap().put("value", value);
        } catch (Exception e) {
            JobExecutionException e1 = new JobExecutionException();
            throw e1;
        }
    }
}
