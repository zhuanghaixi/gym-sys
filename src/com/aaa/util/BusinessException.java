package com.aaa.util;

/**
 * 处理异常工具类
 * @author Administrator
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

}
