package com.mobile.factory.present.user.session;

import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.helper.message.MessageHelper;
import com.mobile.factory.helper.user.UserHelper;
import com.mobile.factory.model.db.entity.Message;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.identity.MessageIdentity;
import com.mobile.factory.model.db.identity.UserIdentity;
import com.mobile.factory.present.user.contact.ContactsPresent;
import com.mobile.factory.utils.DiffUiDataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Like contact, data is updated when returning
 */
public class UnreadMsgPresentImpl implements UnreadMsgPresent.Presenter {
    private UnreadMsgPresent.View view;

    public UnreadMsgPresent.View getView() {
        return view;
    }

    public void setView(UnreadMsgPresent.View view) {
        this.view = view;
    }

    public UnreadMsgPresentImpl(UnreadMsgPresent.View view) {
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
        MessageHelper.getUnread(new DataSource.Callback<List<MessageIdentity>>() {
            @Override
            public void onFail(int error) {
                view.requestFail(error);
            }

            @Override
            public void onSuccess(List<MessageIdentity> messageIdentities) {
                List<Message> messageList = new ArrayList<>();
                for (MessageIdentity messageIdentity : messageIdentities) {
                    // If the user already exists in the database at this time, check directly.
                    // If not, you need to send a request and save the data
                    String receiverId = messageIdentity.getReceiverId();
                    User me = UserHelper.findFromLocal(receiverId);
                    String senderId = messageIdentity.getSenderId();
                    User sender = UserHelper.findFromLocal(senderId);
                    if(sender == null){
                        sender = UserHelper.findFromNet(senderId);
                    }
                    messageList.add(messageIdentity.build(sender, me, null));
                }

                Log.e("finish request", String.valueOf(messageList.size()));
                calculateDiff(messageList, view.getAdapter().getItemList());
            }
        });

    }

    /**
     * Compare the differences in data and notify the interface to refresh
     * @param newMessageList
     * @param oldMessageList
     */
    private void calculateDiff(List<Message> newMessageList, List<Message> oldMessageList) {
        DiffUiDataCallback<Message> msgDiffUiDataCallback =
                new DiffUiDataCallback<>(oldMessageList, newMessageList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(msgDiffUiDataCallback);

        // update data
        view.getAdapter().updateDataList(newMessageList);

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
