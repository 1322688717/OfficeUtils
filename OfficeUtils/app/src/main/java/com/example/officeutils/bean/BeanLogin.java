package com.example.officeutils.bean;

import com.google.gson.annotations.SerializedName;

public class BeanLogin {

    @SerializedName("code")
    private Integer code;
    @SerializedName("user")
    private UserDTO user;
    @SerializedName("token")
    private String token;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserDTO {
        @SerializedName("page")
        private Integer page;
        @SerializedName("limit")
        private Integer limit;
        @SerializedName("id")
        private Integer id;
        @SerializedName("username")
        private String username;
        @SerializedName("truename")
        private String truename;
        @SerializedName("password")
        private String password;
        @SerializedName("phone")
        private String phone;
        @SerializedName("sex")
        private String sex;
        @SerializedName("email")
        private String email;
        @SerializedName("city")
        private String city;
        @SerializedName("description")
        private String description;
        @SerializedName("photo")
        private String photo;
        @SerializedName("lastLoginTime")
        private Object lastLoginTime;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Object getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(Object lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }
    }
}
