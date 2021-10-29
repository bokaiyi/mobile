package com.mobile.factory;

import android.content.Context;

import com.mobile.factory.StaticData.AccountData;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class Application extends android.app.Application {
    private static Application application;

    private static Context context;


    /**
     * Load the database and the persistent token!! Don't change
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        AccountData.load(this);

        Application.context = getApplicationContext();
        com.igexin.sdk.PushManager.getInstance().initialize(this);
    }


    public static Application getApp() {
        return application;
    }

    public static Context getAppContext() {
        return Application.context;
    }
}
