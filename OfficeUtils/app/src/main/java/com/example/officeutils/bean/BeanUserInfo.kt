import com.google.gson.annotations.SerializedName

class BeanUserInfo {
    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("code")
    var code: Int? = null

    @SerializedName("data")
    var data: DataDTO? = null

    class DataDTO {
        @SerializedName("records")
        var records: List<RecordsDTO>? = null

        @SerializedName("total")
        var total: Int? = null

        @SerializedName("size")
        var size: Int? = null

        @SerializedName("current")
        var current: Int? = null

        @SerializedName("orders")
        var orders: List<*>? = null

        @SerializedName("searchCount")
        var searchCount: Boolean? = null

        @SerializedName("pages")
        var pages: Int? = null

        class RecordsDTO {
            @SerializedName("page")
            var page: Int? = null

            @SerializedName("limit")
            var limit: Int? = null

            @SerializedName("id")
            var id: Int? = null

            @SerializedName("username")
            var username: String? = null

            @SerializedName("truename")
            var truename: String? = null

            @SerializedName("password")
            var password: String? = null

            @SerializedName("phone")
            var phone: String? = null

            @SerializedName("sex")
            var sex: String? = null

            @SerializedName("email")
            var email: String? = null

            @SerializedName("city")
            var city: String? = null

            @SerializedName("description")
            var description: Any? = null

            @SerializedName("photo")
            var photo: String? = null

            @SerializedName("lastLoginTime")
            var lastLoginTime: Any? = null
        }
    }
}