package com.example.officeutils.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 提交的表单
 */
public class JsonToFrom {
    public String webHook;
    public String tag;
    public TasksDTO tasks;

    public JsonToFrom(String webHook, String tag, TasksDTO tasks) {
        this.webHook = webHook;
        this.tag = tag;
        this.tasks = tasks;
    }

    public String getWebHook() {
        return webHook;
    }

    public void setWebHook(String webHook) {
        this.webHook = webHook;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TasksDTO getTasks() {
        return tasks;
    }

    public void setTasks(TasksDTO tasks) {
        this.tasks = tasks;
    }

    public static class TasksDTO {
        public ConvertFileDTO convertFile;
        public ImportFileDTO importFile;
        public ExportResultDTO exportResult;

        public TasksDTO(ConvertFileDTO convertFile, ImportFileDTO importFile, ExportResultDTO exportResult) {
            this.convertFile = convertFile;
            this.importFile = importFile;
            this.exportResult = exportResult;
        }

        public ImportFileDTO getImportFile() {
            return importFile;
        }

        public void setImportFile(ImportFileDTO importFile) {
            this.importFile = importFile;
        }

        public static class ConvertFileDTO {
            public List<String> input;
            public String outputFormat;
            public String operation;

            public ConvertFileDTO(List<String> input, String outputFormat, String operation) {
                this.input = input;
                this.outputFormat = outputFormat;
                this.operation = operation;
            }

            public List<String> getInput() {
                return input;
            }

            public void setInput(List<String> input) {
                this.input = input;
            }

            public String getOutputFormat() {
                return outputFormat;
            }

            public void setOutputFormat(String outputFormat) {
                this.outputFormat = outputFormat;
            }

            public String getOperation() {
                return operation;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }
        }

        public static class ImportFileDTO {
            public String operation;
            public String url;

            public ImportFileDTO(String operation, String url) {
                this.operation = operation;
                this.url = url;
            }

            public String getOperation() {
                return operation;
            }

            public String getUrl() {
                return url;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ExportResultDTO {
            public List<String> input;
            public String operation;

            public ExportResultDTO(List<String> input, String operation) {
                this.input = input;
                this.operation = operation;
            }
        }
    }
}
