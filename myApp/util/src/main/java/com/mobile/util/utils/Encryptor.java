package com.mobile.util.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 辅助类，不用动
 */
public class Encryptor {
    private final BasicTextEncryptor encryptor;

    public Encryptor(String pwd){
        encryptor = new BasicTextEncryptor();
        encryptor.setPassword(pwd);
    }

    public BasicTextEncryptor getEncryptor(){
        return encryptor;
    }
}
