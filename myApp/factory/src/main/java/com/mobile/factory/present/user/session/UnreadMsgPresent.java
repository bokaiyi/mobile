package com.mobile.factory.present.user.session;

import com.mobile.factory.model.db.entity.Message;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.present.user.contact.ContactsPresent;
import com.mobile.util.widget.recycler.Adapter;

/**
 * show unread message
 */
public interface UnreadMsgPresent {
    interface Presenter {
        // search contact in start() drirectly
        void start();

        void finish();

    }

    interface View {
        // get to adapter first，then refresh
        Adapter<Message> getAdapter();

        // adapter data changed then notify view to refresh
        void notifyViewWhenDataChanged();

        void requestFail(int error);

        // set a presenter
        void setPresenter(UnreadMsgPresent.Presenter presenter);

        void loading();
    }
}
