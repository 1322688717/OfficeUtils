package com.example.officeutils.bean;

import com.google.gson.annotations.SerializedName;

public class BeanDPFWord {

    @SerializedName("jobid")
    private String jobid;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("query_url")
    private String queryUrl;

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }
}
