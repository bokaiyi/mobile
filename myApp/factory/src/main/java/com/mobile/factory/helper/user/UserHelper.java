package com.mobile.factory.helper.user;

<<<<<<< HEAD
import com.mobile.factory.Factory;
import com.mobile.factory.R;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.util.StaticData.AccountData;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.user.UserUpdateInfoModel;
import com.mobile.util.model.db.entity.User;
import com.mobile.util.model.db.identity.UserIdentity;
=======

import com.mobile.factory.BaseFactory;
import com.mobile.factory.R;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.api.ResponseModel;
import com.mobile.factory.model.api.user.UserUpdateInfoModel;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.entity.User_Table;
import com.mobile.factory.model.db.identity.UserIdentity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
>>>>>>> 16b0d4c (fix bugs-Final version)

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
<<<<<<< HEAD
 * 封装用户请求的类
 */
public class UserHelper {
=======
 * Encapsulate user requests
 */
public class UserHelper {

    /**
     * Get contacts from userid
     * @param userId
     * @param callback
     */
    public static void requestContactsOfAUser(String userId, DataSource.Callback<List<UserIdentity>> callback){
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<List<UserIdentity>>> responseModelCall =
                callRemote.findContactsByID(AccountData.getToken(), userId);
        responseModelCall.enqueue(new Callback<ResponseModel<List<UserIdentity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserIdentity>>> call, Response<ResponseModel<List<UserIdentity>>> response) {
                ResponseModel<List<UserIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<UserIdentity> response_result = body.getResponse_result();
                    callback.onSuccess(response_result);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }


    /**
     * Get contacts
     * @param callback
     * @return
     */
    public static void requestContacts(DataSource.Callback<List<UserIdentity>> callback){
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<List<UserIdentity>>> responseModelCall = callRemote.findContacts(AccountData.getToken());
        responseModelCall.enqueue(new Callback<ResponseModel<List<UserIdentity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserIdentity>>> call, Response<ResponseModel<List<UserIdentity>>> response) {
                ResponseModel<List<UserIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<UserIdentity> response_result = body.getResponse_result();
                    callback.onSuccess(response_result);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });

    }



    /**
     * Follow a user
     *
     * @param followId
     * @param callback
     */
    public static void followUser(String followId, DataSource.Callback<UserIdentity> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<UserIdentity>> responseModelCall = callRemote.followUser(AccountData.getToken(), followId);
        responseModelCall.enqueue(new Callback<ResponseModel<UserIdentity>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserIdentity>> call, Response<ResponseModel<UserIdentity>> response) {
                ResponseModel<UserIdentity> body = response.body();
                if (body.getCode() == 1) {
                    UserIdentity userIdentity = body.getResponse_result();
                    //Save
                    BaseFactory.getUserCenter().dispatch(userIdentity);

                    callback.onSuccess(userIdentity);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserIdentity>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }


    /**
     * Refresh contacts
     */
    public static void refreshContacts(DataSource.Callback<UserIdentity> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);

        callRemote.findContacts(AccountData.getToken())
                .enqueue(new Callback<ResponseModel<List<UserIdentity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<UserIdentity>>> call, Response<ResponseModel<List<UserIdentity>>> response) {
                ResponseModel<List<UserIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<UserIdentity> useridentity = (List<UserIdentity>) body.getResponse_result();
                    if (useridentity == null || useridentity.size() == 0) {
                        return;
                    }
                    //Save
                    UserIdentity[] useridentity1 = useridentity.toArray(new UserIdentity[0]);

                    BaseFactory.getUserCenter().dispatch(useridentity1);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(null);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }


    /**
     * Search contacts
     *
     * @param query
     * @param callback
     * @return
     */
    public static Call search(String query, DataSource.Callback<List<UserIdentity>> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<List<UserIdentity>>> responseModelCall = callRemote.searchUser(AccountData.getToken(), query);
        // Start a request
        responseModelCall.enqueue(new Callback<ResponseModel<List<UserIdentity>>>() {

            @Override
            public void onResponse(Call<ResponseModel<List<UserIdentity>>> call, Response<ResponseModel<List<UserIdentity>>> response) {
                ResponseModel<List<UserIdentity>> body = response.body();
                if (body.getCode() == 1) {
                    List<UserIdentity> userIdentityList = body.getResponse_result();
                    callback.onSuccess(userIdentityList);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<UserIdentity>>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });

        return responseModelCall;
    }


    /**
     * Update user information
     *
     * @param userUpdateInfoModel
     * @param callback
     */
>>>>>>> 16b0d4c (fix bugs-Final version)
    public static void update(UserUpdateInfoModel userUpdateInfoModel,
                              DataSource.Callback<UserIdentity> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<UserIdentity>> responseModelCall = callRemote.updateUserInfo(userUpdateInfoModel, AccountData.getToken());
        request(responseModelCall, callback);
    }

    /**
<<<<<<< HEAD
     * 封装的请求方法
=======
     * Encapsulate the request methos
     *
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @param responseModelCall
     * @param callback
     */
    public static void request(Call<ResponseModel<UserIdentity>> responseModelCall,
<<<<<<< HEAD
                               DataSource.Callback<UserIdentity> callback){
        // 发起请求
=======
                               DataSource.Callback<UserIdentity> callback) {
        // Start a request
>>>>>>> 16b0d4c (fix bugs-Final version)
        responseModelCall.enqueue(new Callback<ResponseModel<UserIdentity>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserIdentity>> call,
                                   Response<ResponseModel<UserIdentity>> response) {
<<<<<<< HEAD
                // 拿response的body
                ResponseModel<UserIdentity> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // 如果是个成功的请求
                    UserIdentity userIdentity = responseModel.getResponse_result();
                    User user = userIdentity.build();
                    // 拿用户并写入客户端的数据库
                    user.save();
                    callback.onSuccess(userIdentity);
                } else {
                    // 如果失败了，处理失败的提示
                    int error = Factory.transferResponseErrorCode(responseModel);
=======
                // The body to take response
                ResponseModel<UserIdentity> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // If it is a successful request
                    UserIdentity userIdentity = responseModel.getResponse_result();
                    User user = userIdentity.build();
                    // Take the user and write to the client's database
                    user.save();
                    callback.onSuccess(userIdentity);
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(responseModel);
>>>>>>> 16b0d4c (fix bugs-Final version)
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserIdentity>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }
<<<<<<< HEAD
=======

    /**
     * Search for a user, prioritize the local cache,
     * and then pull it from the network
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }

    // Find a user's information locally
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }


    // Find a user's information on the Internet
    public static User findFromNet(String id) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        try {
            Response<ResponseModel<UserIdentity>> r = callRemote.uFind(id, AccountData.getToken()).execute();
            UserIdentity useridentity = r.body().getResponse_result();
            if (useridentity != null) {
                User u = useridentity.build();
                // Database storage and notification
                BaseFactory.getUserCenter().dispatch(useridentity);
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
>>>>>>> 16b0d4c (fix bugs-Final version)
}
