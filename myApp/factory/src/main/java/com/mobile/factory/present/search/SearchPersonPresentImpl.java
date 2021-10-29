package com.mobile.factory.present.search;


import com.mobile.factory.helper.user.UserHelper;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.db.identity.UserIdentity;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

public class SearchPersonPresentImpl implements SearchPresent.Presenter, DataSource.Callback<List<UserIdentity>> {
    // search person view
    private SearchPresent.PersonView personView;
    // save a call，avoid conflict
    private Call call;

    public SearchPresent.PersonView getPersonView() {
        return personView;
    }

    public void setPersonView(SearchPresent.PersonView personView) {
        this.personView = personView;
    }

    /**
     * Constructor
     *
     * @param personView pass in a search person view
     */
    public SearchPersonPresentImpl(SearchPresent.PersonView personView) {
        setPersonView(personView);
        personView.setPresenter(this); // set presenter back
    }

    /**
     * The network request of the search group is implemented here
     *
     * @param query
     */
    @Override
    public void search(String query) {
        start();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }

        call = UserHelper.search(query, this);

    }

    @Override
    public void start() {
        if (personView != null) {
            personView.loading();
        }
    }

    @Override
    public void finish() {
        if (personView != null) {
            personView.setPresenter(null);
        }
        setPersonView(null);
    }

    /**
     * Search success
     * @param userIdentities
     */
    @Override
    public void onSuccess(List<UserIdentity> userIdentities) {
        if(getPersonView() != null){
            // make sure all actions are on main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    personView.searchSuccess(userIdentities);
                }
            });
        }
    }

    /**
     * Search fail
     * @param error
     */
    @Override
    public void onFail(int error) {
        if(getPersonView() != null){
            // make sure all actions are on main thread
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    personView.searchFail(error);
                }
            });
        }
    }
}
