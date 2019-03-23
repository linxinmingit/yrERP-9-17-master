package com.yr.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class Md5Utils {
    public static String source = "yirong";
    public static int hashIterations = 1024;

    /**
     * 将密码进行md5加密后返回
     * @param password
     * @return String
     */
    public static String getMd5Password(String password){
        String hashAlgorithmName = "MD5";
        Object salt = new Md5Hash(source);

        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        return result.toString();
    }

}
