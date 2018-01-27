package com.example04;

import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;

public class DumbJob implements Job {

    String jobSays;
    Integer myFloatValue;
    ArrayList state;

    public DumbJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getMergedJobDataMap();
        state.add(new Date());
        System.out.println("instance:" + this.hashCode() + "key:" + key + " of Dombjob says: "
                + jobSays + ", and value is: " + myFloatValue);
        System.out.println();
    }

    public String getJobSays() {
        return jobSays;
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public ArrayList getState() {
        return state;
    }

    public void setState(ArrayList state) {
        this.state = state;
    }

    public Integer getMyFloatValue() {
        return myFloatValue;
    }

    public void setMyFloatValue(Integer myFloatValue) {
        this.myFloatValue = myFloatValue;
    }
}
