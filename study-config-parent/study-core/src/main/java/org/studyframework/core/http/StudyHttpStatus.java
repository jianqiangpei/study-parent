package org.studyframework.core.http;

public class StudyHttpStatus {

    public final static String $200_SUCCESS = "200";            //请求成功

    public final static String $404_NOT_FOUND = "404";          //请求失败，资源不存在

    public final static String $405_METHOD_NOT_ALLOWED = "405";     //服务请求异常，方法不被允许

    public final static String $500_FAIL = "500";               //请求失败，系统异常

    public final static String $600_USER_TOKEN_ERROR = "600";   //用户令牌错误

    public final static String $601_PARAMETER_ERROR = "601";    //参数缺失

    public final static String $602_PARAMETER_BEYOND_LIMIT = "602";    //参数超出范围



    public final static String $610_PERMISSION_ACCESS_DENIED = "610";   //权限不足，拒绝访问


    public final static String $690_UNKNOWN_EXCEPTION = "690";  //未知错误


    public final static String $700_SERVICE_REQUEST_ERROR = "700";      //微服务请求失败

    public final static String $999_SERVER_FREQUENTLY_ACCESSED = "999";     //访问频繁

}
