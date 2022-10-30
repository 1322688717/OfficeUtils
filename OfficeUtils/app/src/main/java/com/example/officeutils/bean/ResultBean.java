package com.example.officeutils.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


    public  class ResultBean {
        @JSONField(name = "id")
        public String id;
        @JSONField(name = "status")
        public String status;
        @JSONField(name = "created_at")
        public String createdAt;
        @JSONField(name = "runMode")
        public String runMode;
        @JSONField(name = "tasks")
        public List<TasksDTO> tasks;
        @JSONField(name = "message")
        public String message;

        public ResultBean(String id, String status, String createdAt, String runMode, List<TasksDTO> tasks, String message) {
            this.id = id;
            this.status = status;
            this.createdAt = createdAt;
            this.runMode = runMode;
            this.tasks = tasks;
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getRunMode() {
            return runMode;
        }

        public void setRunMode(String runMode) {
            this.runMode = runMode;
        }

        public List<TasksDTO> getTasks() {
            return tasks;
        }

        public void setTasks(List<TasksDTO> tasks) {
            this.tasks = tasks;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static class TasksDTO {
            @JSONField(name = "id")
            public String id;
            @JSONField(name = "name")
            public String name;
            @JSONField(name = "job_id")
            public String jobId;
            @JSONField(name = "status")
            public String status;
            @JSONField(name = "operation")
            public String operation;
            @JSONField(name = "created_at")
            public String createdAt;
            @JSONField(name = "urlArray")
            public List<String> urlArray;
            @JSONField(name = "depends_on_task_ids")
            public List<String> dependsOnTaskIds;
            @JSONField(name = "input")
            public List<String> input;
            @JSONField(name = "output_format")
            public String outputFormat;

            public TasksDTO(String id, String name, String jobId, String status, String operation, String createdAt, List<String> urlArray, List<String> dependsOnTaskIds, List<String> input, String outputFormat) {
                this.id = id;
                this.name = name;
                this.jobId = jobId;
                this.status = status;
                this.operation = operation;
                this.createdAt = createdAt;
                this.urlArray = urlArray;
                this.dependsOnTaskIds = dependsOnTaskIds;
                this.input = input;
                this.outputFormat = outputFormat;
            }

            public List<String> getUrlArray() {
                return urlArray;
            }

            public void setUrlArray(List<String> urlArray) {
                this.urlArray = urlArray;
            }
        }
    }

