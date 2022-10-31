package com.example.officeutils.bean

import com.alibaba.fastjson.annotation.JSONField

/**
 * 返回参数 body 处理，转文件
 */
data class Base64ToFile(
    @JSONField(name = "data")
    var data: DataDTO? = null
) {


    class DataDTO(
        @JSONField(name = "id")
        var id: String? = null,

        @JSONField(name = "status")
    var status: String? = null,

    @JSONField(name = "created_at")
    var createdAt: String? = null,

    @JSONField(name = "runMode")
    var runMode: String? = null,

    @JSONField(name = "tasks")
    var tasks: List<TasksDTO>? = null,

    @JSONField(name = "message")
    var message: String? = null
    ) {

        class TasksDTO(
            @JSONField(name = "id")
            var id: String? = null,

            @JSONField(name = "name")
        var name: String? = null,

        @JSONField(name = "job_id")
        var jobId: String? = null,

        @JSONField(name = "status")
        var status: String? = null,

        @JSONField(name = "operation")
        var operation: String? = null,

        @JSONField(name = "created_at")
        var createdAt: String? = null,

        @JSONField(name = "urlArray")         // base64文件
        var urlArray: List<String>? = null,

        @JSONField(name = "depends_on_task_ids")
        var dependsOnTaskIds: List<String>? = null,

        @JSONField(name = "input")
        var input: List<String>? = null,

        @JSONField(name = "output_format")
        var outputFormat: String? = null
        ) {
        }
    }
}