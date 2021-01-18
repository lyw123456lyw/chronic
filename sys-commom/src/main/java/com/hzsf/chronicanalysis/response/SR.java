package com.hzsf.chronicanalysis.response;

import com.hzsf.chronicanalysis.response.enums.ResponseStatus;

/**
 * 成功返回结果
 */
public class SR<T> extends R{

    public SR(int code, String message, T data) {
        super(code, message, data);
    }
    public SR(int code, String message) {
        super(code, message);
    }

    public SR(){
        super();
    }

    public static <T>  SR<T> success(T data){
        if (null == data){
            return new SR(ResponseStatus.SUCCESS.getStatus(),ResponseStatus.SUCCESS.getMsg());
        }else{
            return new SR(ResponseStatus.SUCCESS.getStatus(),ResponseStatus.SUCCESS.getMsg(),data);
        }
    }

}
