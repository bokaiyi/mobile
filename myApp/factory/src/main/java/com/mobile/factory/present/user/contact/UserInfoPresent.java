package com.mobile.factory.present.user.contact;

import com.mobile.factory.model.db.identity.UserIdentity;

import java.util.List;

public interface UserInfoPresent {
    interface Presenter {
//        // Get current user information
//        User getUser();

        // get number of follows for the user
        void getFollows(String userId);

        void start();

        void finish();

    }

    interface View {
        // set a presenter
        void setPresenter(UserInfoPresent.Presenter presenter);

        void getSuccess(List<UserIdentity> userList);

        void getFail(int error);

//        // can chat or not
//        void canStartChat(boolean allowChat);
//
//        //update follow status
//        void setFollowStatus(boolean isFollow);

        void loading();
    }
}
