package com.myApp.net.push.response;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.identity.UserIdentity;
import com.mysql.cj.util.StringUtils;

/**
<<<<<<< HEAD
 * 和账户有关的返回
=======
 * Account response
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class AccountResponse {
    @Expose
    private UserIdentity userIdentity;
    @Expose
    private String token;
    @Expose
<<<<<<< HEAD
    private boolean isBindService; // 是否绑定手机了
=======
    private boolean isBindService; // mobile bind
    @Expose
    private String pushId;  // bind pushId

>>>>>>> 16b0d4c (fix bugs-Final version)

    public AccountResponse(User user) {
        this.userIdentity = new UserIdentity(user);
        this.token = user.getToken();
<<<<<<< HEAD
        // 只要不为空就是绑定手机了
=======
        this.pushId = user.getPushId();
        // is bind if not empty
>>>>>>> 16b0d4c (fix bugs-Final version)
        this.isBindService = !StringUtils.isNullOrEmpty(user.getPushId());
    }

    public UserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
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
<<<<<<< HEAD
=======

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
>>>>>>> 16b0d4c (fix bugs-Final version)
}
