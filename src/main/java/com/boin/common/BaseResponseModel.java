package com.boin.common;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BaseResponseModel<T> {

    private String code;

    private String message;

    private T data;

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

