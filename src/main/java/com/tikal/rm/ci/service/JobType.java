package com.tikal.rm.ci.service;

public enum JobType {


    JAVA("java.job.name"), NODE("java.node.name");


    private String jobName;

    JobType(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }
}
