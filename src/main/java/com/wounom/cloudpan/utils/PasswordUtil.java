package com.wounom.cloudpan.utils;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 19:52
 */
public class PasswordUtil {
    public static String md5Pwd(String password,String salt) {
        return SecureUtil.md5(password+salt);
    }

    public static String createSalt(){
        return RandomUtil.randomString(6);
    }
}
