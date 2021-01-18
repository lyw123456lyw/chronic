package com.hzsf.chronicanalysis.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用返回结果
 * @param <T>
 */
@ToString
@Getter
@Setter
public class R<T> extends AbstractBR{
    private T data;

    public R(Integer code, String message, T data){
        this.setCode(code);
        this.setMsg(message);
        this.data = data;
    }

    public R(Integer code, String message){
        this.setCode(code);
        this.setMsg(message);
    }

    public R(){
    }
}
