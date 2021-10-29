package com.mobile.factory.helper.message;

import com.mobile.factory.BaseFactory;
import com.mobile.factory.R;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.model.api.ResponseModel;
import com.mobile.factory.model.db.entity.Message;
import com.mobile.factory.model.db.identity.MessageIdentity;
import com.mobile.factory.model.db.identity.UserIdentity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageHelper {
    public static Message findFromLocal(String id) {
        //TODO
        return null;
    }

    /**
     * Get the message list
     * @param receiverId
     * @param callback
     */
    public static void getHistory(String receiverId, DataSource.Callback<List<MessageIdentity>> callback){
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<List<MessageIdentity>>> responseModelCall =
                callRemote.getHistory(receiverId, AccountData.getToken());
        responseModelCall.enqueue(new Callback<ResponseModel<List<MessageIdentity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<MessageIdentity>>> call, Response<ResponseModel<List<MessageIdentity>>> response) {
                ResponseModel<List<MessageIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<MessageIdentity> response_result = body.getResponse_result();
                    callback.onSuccess(response_result);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<MessageIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }

    /**
     * Request all unread messages
     * @param callback
     */
    public static void getUnread(DataSource.Callback<List<MessageIdentity>> callback){
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<List<MessageIdentity>>> responseModelCall = callRemote.getUnread(AccountData.getToken());
        responseModelCall.enqueue(new Callback<ResponseModel<List<MessageIdentity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<MessageIdentity>>> call, Response<ResponseModel<List<MessageIdentity>>> response) {
                ResponseModel<List<MessageIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<MessageIdentity> response_result = body.getResponse_result();
                    callback.onSuccess(response_result);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<MessageIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }
}
