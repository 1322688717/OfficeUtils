package com.example.officeutils.bean

import com.alibaba.fastjson2.annotation.JSONField

/**
 * 发送请求的data类
 */
data class JsonToFrom(
    @JSONField(name="tasks")
    var tasks: TasksDTO,
    @JSONField(name="webHook")
    var webHook: String,
    @JSONField(name="tag")
    var tag: String ) {

    class TasksDTO(
        @JSONField(name="JsonRootBean")
        var convertFile: JsonRootBean,
        @JSONField(name="ImportFile")
        var importFile: ImportFileDTO,
        @JSONField(name="ExportResult")
        var exportResult: ExportResultDTO
    ) {

        class JsonRootBean(
            @JSONField(name = "input")
            var input: List<String>,
            @JSONField(name = "output_format")  //  输出类型
            var outputFormat: String,
            @JSONField(name = "operation")
            var operation: String
        )

        class ImportFileDTO(
            @JSONField(name ="url")
            var url: String,                   // 填充前置字符 application/文件类型 拼接 base64
            @JSONField(name = "operation")
            var operation: String)

        class ExportResultDTO(
            @JSONField(name = "input")
            var input: List<String>,
            @JSONField(name = "operation")
            var operation: String)
    }
}

