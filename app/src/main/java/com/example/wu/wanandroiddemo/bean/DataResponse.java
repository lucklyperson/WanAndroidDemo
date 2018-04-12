package com.example.wu.wanandroiddemo.bean;

/**
 * Created by lw on 2018/1/19.
 */

public class DataResponse<T> {
    private int errorCode;
    private Object errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg=" + errorMsg +
                ", data=" + data +
                '}';
    }
}
