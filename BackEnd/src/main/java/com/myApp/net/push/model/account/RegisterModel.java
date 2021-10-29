package com.myApp.net.push.model.account;

import com.google.gson.annotations.Expose;
import com.mysql.cj.util.StringUtils;

/**
<<<<<<< HEAD
 * 注册传递进来的model
=======
 * register model
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class RegisterModel {
    @Expose
    private String name;
    @Expose
    private String pwd;
    @Expose
    private String accountNo;
    @Expose
<<<<<<< HEAD
    private String pushId; // 绑定设备，可以没有
=======
    private String pushId; //might be emtpy
>>>>>>> 16b0d4c (fix bugs-Final version)

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public static boolean isValid(RegisterModel registerModel) {
        return registerModel != null
                && !StringUtils.isNullOrEmpty(registerModel.getAccountNo())
                && !StringUtils.isNullOrEmpty(registerModel.getPwd())
                && !StringUtils.isNullOrEmpty(registerModel.getName());
    }
}
