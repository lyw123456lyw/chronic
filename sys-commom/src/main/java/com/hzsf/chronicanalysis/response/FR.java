package com.hzsf.chronicanalysis.response;

import com.hzsf.chronicanalysis.response.enums.ResponseStatus;

public class FR<T> extends R{
    public FR(Integer code, String message, Object data) {
        super(code, message, data);
    }

    public static <T>  R<T> fail(T... data){
        if (null == data){
            return new R(ResponseStatus.FAILURE.getStatus(),ResponseStatus.FAILURE.getMsg());
        }else{
            return new R(ResponseStatus.FAILURE.getStatus(),ResponseStatus.FAILURE.getMsg(),data);
        }
    }
}
