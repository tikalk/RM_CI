package com.tikal.rm.ci.entity;

public class Request {
    private String gitUrl ;
    private String command ;

    public Request() {
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
