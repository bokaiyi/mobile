package com.mobile.factory.present.user.contact;

import com.mobile.factory.helper.user.UserHelper;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.db.identity.UserIdentity;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class FollowPresentImpl implements FollowPresent.Presenter, DataSource.Callback<UserIdentity> {
    private FollowPresent.View view;

    public FollowPresent.View getView() {
        return view;
    }

    public void setView(FollowPresent.View view) {
        this.view = view;
    }

    /**
     * Constructor，pass a view
     *
     * @param view
     */
    public FollowPresentImpl(FollowPresent.View view) {
        setView(view);
        view.setPresenter(this); // set presenter back
    }

    /**
     * request follow
     * @param followId
     */
    @Override
    public void requestFollow(String followId) {
        start();
        UserHelper.followUser(followId, this);
    }

    @Override
    public void start() {
        // if not null，show loading
        if (view != null) {
            view.loading();
        }
    }

    @Override
    public void finish() {
        if (view != null) {
            view.setPresenter(null);
        }
        setView(null);
    }

    @Override
    public void onSuccess(UserIdentity userIdentity) {
        if(getView() != null){
            // make sure all actions are in main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.followSuccess(userIdentity);
                }
            });
        }
    }

    @Override
    public void onFail(int error) {
        if(getView() != null){
            // make sure all actions are in main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.followFail(error);
                }
            });
        }
    }
}
