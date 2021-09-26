package com.mobile.util.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 辅助类，不用动
 */
public class Encryptor {
    private BasicTextEncryptor encryptor;

    public Encryptor(){
        encryptor = new BasicTextEncryptor();
        encryptor.setPassword("123456");
    }

    public BasicTextEncryptor getEncryptor(){
        return encryptor;
    }
}
