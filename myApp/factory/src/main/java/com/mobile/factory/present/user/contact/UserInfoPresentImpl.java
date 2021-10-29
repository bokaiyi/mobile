package com.mobile.factory.present.user.contact;

import com.mobile.factory.helper.user.UserHelper;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.db.identity.UserIdentity;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class UserInfoPresentImpl implements UserInfoPresent.Presenter, DataSource.Callback<List<UserIdentity>>{
    private UserInfoPresent.View view;

    public UserInfoPresent.View getView() {
        return view;
    }

    public void setView(UserInfoPresent.View view) {
        this.view = view;
    }

    public UserInfoPresentImpl(UserInfoPresent.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getFollows(String userId) {
        start();
        // TODO
        UserHelper.requestContactsOfAUser(userId, this);
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
    public void onSuccess(List<UserIdentity> userIdentities) {
        if(getView() != null){
            // make sure actions are in main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.getSuccess(userIdentities);
                }
            });
        }
    }

    @Override
    public void onFail(int error) {
        if(getView() != null){
            //  make sure actions are in main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.getFail(error);
                }
            });
        }
    }
}
