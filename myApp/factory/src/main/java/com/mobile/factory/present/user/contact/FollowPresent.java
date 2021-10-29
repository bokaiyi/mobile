package com.mobile.factory.present.user.contact;

import com.mobile.factory.model.db.identity.UserIdentity;

/**
 * Interface for follow
 */
public interface FollowPresent {
    interface Presenter {
        // request follow
        void requestFollow(String followId);

        void start();

        void finish();

    }

    interface View {
        // set a presenter
        void setPresenter(FollowPresent.Presenter presenter);

        void followSuccess(UserIdentity userIdentity);

        void followFail(int error);

        void loading();
    }
}
