package com.mobile.factory.model.api.account;

import com.mobile.factory.model.db.entity.User;

/**
 * The interface returns to the account information !!
 * The name must be consistent
 */
public class AccountResponseModel {
    private User userIdentity;
    private String token;
    private boolean isBindService; // Bind phone or not
    private String pushId;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public String toString() {
        return "AccountResponseModel{" +
                "userIdentity=" + userIdentity +
                ", token='" + token + '\'' +
                ", isBindService=" + isBindService +
                ", pushId='" + pushId + '\'' +
                '}';
    }

    public User getUser() {
        return userIdentity;
    }

    public void setUser(User user) {
        this.userIdentity = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBindService() {
        return isBindService;
    }

    public void setBindService(boolean bindService) {
        isBindService = bindService;
    }


}
