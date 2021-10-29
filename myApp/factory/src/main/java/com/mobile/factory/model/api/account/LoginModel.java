package com.mobile.factory.model.api.account;

import com.google.gson.annotations.Expose;

/**
 * Encapsulation of login request
 */
public class LoginModel {
    @Expose
    private String accountNo;  // phone number
    @Expose
    private String pwd;  // password
    @Expose
    private String pushId; // binding device

    public LoginModel(String accountNo, String pwd) {
        this(accountNo, pwd, null);
    }

    public LoginModel(String accountNo, String pwd, String pushId) {
        this.accountNo = accountNo;
        this.pwd = pwd;
        this.pushId = pushId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
