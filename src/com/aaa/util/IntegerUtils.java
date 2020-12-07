package com.aaa.util;

import org.apache.commons.lang3.StringUtils;

/**
 * int 工具类
 */
public class IntegerUtils {
    /**
     * 处理 servlet string 转 int
     */
    public static Integer ToInteger(String tar){
        Integer res = 0;
        if (StringUtils.isNotBlank(tar)){
            res =  Integer.parseInt(tar);
        }
        return res;
    }
}
