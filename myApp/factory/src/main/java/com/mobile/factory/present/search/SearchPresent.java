package com.mobile.factory.present.search;

import com.mobile.factory.model.db.identity.UserIdentity;

import java.util.List;

public interface SearchPresent {
    interface Presenter {
        // search a string
        void search(String query);

        void start();

        void finish();

    }

    interface PersonView {
        // Set a presenter
        void setPresenter(SearchPresent.Presenter presenter);
        
        void searchSuccess(List<UserIdentity> userIdentityList);

        void searchFail(int error);

        void loading();
    }

//    interface GroupView {
//        // Set a presenter
//        void setPresenter(SearchPresent.Presenter presenter);
//
//        void searchSuccess(List<GroupIdentity> groupIdentityList);
//
//        void searchFail(int error);
//
//        void loading();
//    }
}
