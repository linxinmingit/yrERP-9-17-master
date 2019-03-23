package com.yr.entitys.bo.orderBO;

import java.util.Random;

/**
 * 随机数工具类
 */
public class RandomUtil {  
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";  
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** 
     * 返回一个定长的随机字符串(只包含大小写字母、数字) 
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }  

    /** 
     * 返回一个定长的随机纯字母字符串(只包含大小写字母) 
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String generateMixString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));  
        }  
        return sb.toString();  
    }  


    /** 
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母) 
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String generateUpperString(int length) {  
        return generateMixString(length).toUpperCase();  
    }
}
