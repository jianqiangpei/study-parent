package org.studyframework.core.model;


import org.studyframework.core.http.StudyHttpStatus;

public class ApiResponse<T> {

    private static final String successDesc = "操作成功";

    private String code;

    private String desc;

    private T data;

    private ApiResponse() {}

    private ApiResponse(String code) {
        this.code = code;
    }

    private ApiResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    private ApiResponse(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private ApiResponse(String code, T data, String desc) {
        this.code = code;
        this.data = data;
        this.desc = desc;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(StudyHttpStatus.$200_SUCCESS , data ,successDesc);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(StudyHttpStatus.$200_SUCCESS , successDesc);
    }

    public static <T> ApiResponse<T> ok(T data, String desc) {
        return new ApiResponse<>("200", data,desc);
    }

    public static <T> ApiResponse<T> success(String code, T data) {
        return success(code , data , successDesc);
    }

    public static <T> ApiResponse<T> success(String code, T data, String desc) {
        return new ApiResponse<>(code, data,desc);
    }

    public static <T> ApiResponse<T> error(String code, String desc) {
        return new ApiResponse<>(code,null, desc);
    }

    public static <T> ApiResponse<T> error(String code, T data, String desc) {
        return new ApiResponse<>(code,data, desc);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
