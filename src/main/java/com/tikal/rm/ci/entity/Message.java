package com.tikal.rm.ci.entity;

import java.io.Serializable;

public class Message implements Serializable {

    private String githubUrl ;
    private String jenkinsUrl ;
    private String status ;

    public Message(String githubUrl, String jenkinsUrl, String status) {
        this.githubUrl = githubUrl;
        this.jenkinsUrl = jenkinsUrl;
        this.status = status;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
