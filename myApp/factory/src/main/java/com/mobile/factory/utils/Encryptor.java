package com.mobile.factory.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Assistant class, no need to change
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
