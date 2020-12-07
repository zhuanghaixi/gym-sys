package com.aaa.entity;


public class ResponseDto {
    /**
     * 失败
     */
    public static final int FAILURE_CODE = 0;

    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 1;

    public static final int NO_LOGIN = 2;

    public static final int INVALID_CODE = 3;

    private int status;
    private String message;
    private Object data;

    public ResponseDto() {

    }

    public ResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
