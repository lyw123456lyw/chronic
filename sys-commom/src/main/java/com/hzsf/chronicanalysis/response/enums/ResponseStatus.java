package com.hzsf.chronicanalysis.response.enums;

public enum ResponseStatus {
    /**
     * 请求成功
     */
    SUCCESS(200,"请求成功"),
    /**
     * 服务器内部错误
     */
    FAILURE(500,"服务器内部错误"),
    /**
     * 没有登录
     */
    UNLOGIN(401,"没有登录"),
    /**
     * 没有权限
     */
    UNAUTH(403,"没有权限");

    private int status;
    private String msg;

    public int getStatus(){
        return status;
    }

    public String getMsg(){
        return msg;
    }

    ResponseStatus(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
