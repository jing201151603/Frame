package com.jing.dao_lib.util;

/**
 * author: 陈永镜 .
 * date: 2017/3/31 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class ExceptionUtils {

    public static void throwExcep(String msg) {
        throw new RuntimeException(msg);
    }

}
