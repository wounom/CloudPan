package com.wounom.cloudpan.utils;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 14:44
 */

public class EmailUtil {
    public static String CreateCode(){
        String code = (Math.random() + "").substring(2, 8);
        return code;
    }




}
