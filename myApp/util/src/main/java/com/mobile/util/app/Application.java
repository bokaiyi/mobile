package com.mobile.util.app;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class Application extends android.app.Application {
    private static Application application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApp() {
        return application;
    }

}
