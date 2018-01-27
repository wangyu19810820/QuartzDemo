package com.example04;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    private static Logger log = LogManager.getLogger(ColorJob.class);

    public static final String FAVORITE_COLOR = "favority color";
    public static final String EXECUTION_COUNT = "count";

    private int _count = 1;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
        log.info("ColorJob:{} key:{} execute at {}", this.hashCode(), jobKey, new Date());
        log.info("favorite color is {}", favoriteColor);
        log.info("execution count (from job map) is {}", count);
        log.info("execution count (from job member variable) is {}", _count);

        count++;
        data.put(EXECUTION_COUNT, count);

        _count++;
    }
}
