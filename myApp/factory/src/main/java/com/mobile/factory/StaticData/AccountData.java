package com.mobile.factory.StaticData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mobile.factory.Application;
import com.mobile.factory.model.api.account.AccountResponseModel;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.entity.User_Table;
import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Storage account persistent data
 */
public class AccountData {
    private static String token;
    private static String userId;
    private static String userAccount;
    private static String pushId;

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        AccountData.pushId = pushId;
    }

    /**
     * Persist user data
     *
     * @param context
     */
    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString("token", token).apply();
        sp.edit().putString("userId", userId).apply();
        sp.edit().putString("pushId", pushId).apply();
    }

    /**
     * Persist user information
     *
     * @param accountResponseModel
     */
    public static void save(AccountResponseModel accountResponseModel) {
        AccountData.userId = accountResponseModel.getUser().getId();
        AccountData.userAccount = accountResponseModel.getUser().getPhone();
        setToken(accountResponseModel.getToken());
    }

    /**
     * Load persistent information
     *
     * @param context
     * @return Get the token and userid, the default is empty
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        AccountData.token = sp.getString("token", "");
        AccountData.userId = sp.getString("userId", "");
        AccountData.pushId = sp.getString("pushId", "");
    }

    public static String getToken() {
        return token;
    }

    /**
     * If the token is not empty, it is already logged in
     *
     * @return
     */
    public static boolean isLogin() {
        return !StringUtils.isNullOrEmpty(AccountData.token);
    }

    /**
     * Whether it has been bound to pushid
     * @return
     */
    public static boolean isBind() {
        return !StringUtils.isNullOrEmpty(AccountData.pushId);
    }


    /**
     * Set up and store token
     *
     * @param token
     */
    public static void setToken(String token) {
        AccountData.token = token;
        AccountData.save(Application.getApp());
    }

    /**
     * Get current user
     */
    public static User getUser() {
        User user = new User();
        if (!StringUtils.isNullOrEmpty(userId)) {
            user = SQLite.select().from(User.class).where(User_Table.id.eq(userId)).querySingle();
        }
        return user;
    }

    /**
     * Determine whether user information is complete
     *
     * @return
     */
    public static boolean userInfoFinished() {
        User user = getUser();
        if (user != null) {
            if(user.getId() != null && user.getDescription() != null){
                Log.e("User", user.getId());
                Log.e("User", user.getDescription());
            }
            return !StringUtils.isNullOrEmpty(user.getDescription())
                    && user.getSex() != 0
                    && !StringUtils.isNullOrEmpty(user.getPortrait());
        }
        return false;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        AccountData.userId = userId;
    }

    public static String getUserAccount() {
        return userAccount;
    }

    public static void setUserAccount(String userAccount) {
        AccountData.userAccount = userAccount;
    }
}
