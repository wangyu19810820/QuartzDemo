package com.spring.model;

public class JobModel {

    private String name;
    private String group;
    private String cronTriggerExpr;

    public JobModel() {
    }

    public JobModel(String name, String group, String cronTriggerExpr) {
        this.name = name;
        this.group = group;
        this.cronTriggerExpr = cronTriggerExpr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCronTriggerExpr() {
        return cronTriggerExpr;
    }

    public void setCronTriggerExpr(String cronTriggerExpr) {
        this.cronTriggerExpr = cronTriggerExpr;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", cronTriggerExpr='" + cronTriggerExpr + '\'' +
                '}';
    }
}
