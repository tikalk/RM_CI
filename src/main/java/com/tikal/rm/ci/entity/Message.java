package com.tikal.rm.ci.entity;

import java.io.Serializable;

public class Message implements Serializable {

    private String githubUrl ;
    private String jenkinsUrl ;
    private String status ;
    private String token;

    public Message(String githubUrl, String jenkinsUrl, String status, String token) {
        this.githubUrl = githubUrl;
        this.jenkinsUrl = jenkinsUrl;
        this.status = status;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Message setToken(String token) {
        this.token = token;
        return this;
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
