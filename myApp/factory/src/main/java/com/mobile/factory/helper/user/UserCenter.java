package com.mobile.factory.helper.user;

import com.mobile.factory.model.db.identity.UserIdentity;

public interface UserCenter {
    void dispatch(UserIdentity... cards);
}
