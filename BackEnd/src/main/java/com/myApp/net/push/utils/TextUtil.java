package com.myApp.net.push.utils;

import com.myApp.net.push.provider.GsonProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

public class TextUtil {
    /**
<<<<<<< HEAD
     * 计算一个字符串的MD5信息
     *
     * @param str 字符串
     * @return MD5值
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
=======
     * generate MD5 value of string
     *
     * @param str stirng
     * @return MD5 value
     */
    public static String getMD5(String str) {
        try {
            // generate a MD5 digest
            MessageDigest md = MessageDigest.getInstance("MD5");
            // calculate md5
            md.update(str.getBytes());
            
>>>>>>> 16b0d4c (fix bugs-Final version)
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
<<<<<<< HEAD
     * 对一个字符串进行Base64编码
     *
     * @param str 原始字符串
     * @return 进行Base64编码后的字符串
=======
     * base64 encoder
     *
     * @param str string
     * @return string after base64 encoding
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static String encodeBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
<<<<<<< HEAD
     * 把任意类的实例转换为Json字符串
     *
     * @param obj Object
     * @return Json字符串
=======
     * any instance to Json 
     *
     * @param obj Object
     * @return Json 
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static String toJson(Object obj) {
        return GsonProvider.getGson().toJson(obj);
    }
}

