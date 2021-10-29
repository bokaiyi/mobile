package com.mobile.factory.present.user.contact;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mobile.factory.helper.user.UserHelper;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.entity.User_Table;
import com.mobile.factory.model.db.identity.UserIdentity;
import com.mobile.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresentImpl implements ContactsPresent.Presenter {
    private ContactsPresent.View view;

    public ContactsPresent.View getView() {
        return view;
    }

    public void setView(ContactsPresent.View view) {
        this.view = view;
    }

    public ContactsPresentImpl(ContactsPresent.View view) {
        setView(view);
        view.setPresenter(this);
    }

    @Override
    public void start() {
        // if not null，show loading
        if (view != null) {
            view.loading();
        }

        // Load network data
        UserHelper.requestContacts(new DataSource.Callback<List<UserIdentity>>() {
            @Override
            public void onFail(int error) {
                view.requestFail(error);
            }

            @Override
            public void onSuccess(List<UserIdentity> userIdentities) {
                List<User> userList = new ArrayList<>();
                // Save to local database
                for (UserIdentity userIdentity : userIdentities) {
                    User user = userIdentity.build();
                    userList.add(user);
                    user.save();
                }

                Log.e("finish request", String.valueOf(userList.size()));

                // There is a problem here. If the content of the database is
                // less than the previous interface, it will crash.
                // The Internet says that Android currently has no solution, only a catch is added.
                // Schedule data changes
                calculateDiff(userList, view.getAdapter().getItemList());
            }
        });
    }

    /**
     * Compare the differences in data and notify the interface to refresh
     * @param newUserList
     * @param oldUserList
     */
    private void calculateDiff(List<User> newUserList, List<User> oldUserList) {
        DiffUiDataCallback<User> userDiffUiDataCallback = new DiffUiDataCallback<>(oldUserList, newUserList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(userDiffUiDataCallback);

        // update data
        view.getAdapter().updateDataList(newUserList);

        diffResult.dispatchUpdatesTo(view.getAdapter());
        view.notifyViewWhenDataChanged();
    }

    @Override
    public void finish() {
        if (view != null) {
            view.setPresenter(null);
        }
        setView(null);
    }

}
