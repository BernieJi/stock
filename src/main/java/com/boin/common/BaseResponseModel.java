package com.boin.common;
import lombok.Data;


@Data
public class BaseResponseModel<T> {

    public String code;

    public String message;

    public T data;

    /*
     *
     *  回應成功
     *
     */
    public void setSuccess(T data){
        this.code = "200";
        this.data = data;
    }

    /*
     *
     *  回應失敗
     *
     */
    public void setFail(String code, String message){
        this.code = code;
        this.message = message;
    }


}

