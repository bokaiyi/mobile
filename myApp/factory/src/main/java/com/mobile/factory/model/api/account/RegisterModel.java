package com.mobile.factory.model.api.account;

import com.google.gson.annotations.Expose;

/**
 * Registration request model, consistent with the parameters corresponding to the interface
 */
public class RegisterModel {
    @Expose
    private String accountNo;  // phone number
    @Expose
    private String pwd;  // password
    @Expose
    private String name;  // name
    @Expose
    private String pushId; // binding device

    public RegisterModel(String accountNo, String pwd, String name) {
        this(accountNo, pwd, name, null);
    }

    public RegisterModel(String accountNo, String pwd, String name, String pushId) {
        this.accountNo = accountNo;
        this.pwd = pwd;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
