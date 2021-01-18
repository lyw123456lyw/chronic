package com.hzsf.chronicanalysis.response.exception;

import com.hzsf.chronicanalysis.response.enums.ResponseStatus;

/**
 * 自定义异常
 */
public class BusinessException  extends RuntimeException{

    private String msg;

    private int code;

    public BusinessException(){
        this.code = ResponseStatus.FAILURE.getStatus();
    }

    public BusinessException(String msg,Throwable cause){
        super(msg,cause);
        this.msg = msg;
    }

    public BusinessException(String msg,int code,Throwable cause){
        super(msg,cause);
        this.msg = msg;
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
