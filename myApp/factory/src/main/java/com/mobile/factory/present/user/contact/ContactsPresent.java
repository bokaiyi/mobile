package com.mobile.factory.present.user.contact;

import com.mobile.factory.model.db.entity.User;
import com.mobile.util.widget.recycler.Adapter;

/**
 * show the contact interface
 * base class is not written yet！！
 */
public interface ContactsPresent {
    interface Presenter {
        // search in start() directly
        void start();

        void finish();

    }

    interface View {
        // get to adapter，then refresh
        Adapter<User> getAdapter();

        // Notify the view to refresh after the adapter data is updated
        void notifyViewWhenDataChanged();

        void requestFail(int error);

        // set a presenter
        void setPresenter(ContactsPresent.Presenter presenter);

        void loading();
    }
}
