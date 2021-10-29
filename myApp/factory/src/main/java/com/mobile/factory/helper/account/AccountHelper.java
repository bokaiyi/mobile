package com.mobile.factory.helper.account;

<<<<<<< HEAD
import com.mobile.factory.Factory;
import com.mobile.factory.R;
import com.mobile.util.StaticData.AccountData;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.account.LoginModel;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.db.entity.User;
=======
import android.util.Log;

import com.mobile.factory.BaseFactory;
import com.mobile.factory.R;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.model.api.ResponseModel;
import com.mobile.factory.model.api.account.AccountResponseModel;
import com.mobile.factory.model.api.account.LoginModel;
import com.mobile.factory.model.api.account.RegisterModel;
import com.mobile.factory.model.db.entity.User;
import com.raizlabs.android.dbflow.StringUtils;
>>>>>>> 16b0d4c (fix bugs-Final version)

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
<<<<<<< HEAD
 * 账户进行网络请求的类
 */
public class AccountHelper {
    /**
     * 封装注册请求
     *
     * @param registerModel 一个请求model
     * @param callback      处理请求结果的回调
=======
 * Network request for account.
 */
public class AccountHelper {
    /**
     * Encapsulated register request
     *
     * @param registerModel A reauest model
     * @param callback  Callback for processing the request result
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static void register(RegisterModel registerModel, DataSource.Callback<User> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<AccountResponseModel>> responseModelCall = callRemote.register(registerModel);
        request(responseModelCall, callback);
    }

    /**
<<<<<<< HEAD
     * 封装登录请求
     *
     * @param loginModel 一个请求model
     * @param callback   处理请求结果的回调
=======
     * Encapsulated login request
     *
     * @param loginModel A request model
     * @param callback Callback for processing the request result
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static void login(LoginModel loginModel, DataSource.Callback<User> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<AccountResponseModel>> responseModelCall = callRemote.login(loginModel);
        request(responseModelCall, callback);
    }

    /**
<<<<<<< HEAD
     * 封装的请求方法
=======
     * Encapsulatated request
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param responseModelCall
     * @param callback
     */
    public static void request(Call<ResponseModel<AccountResponseModel>> responseModelCall,
                               DataSource.Callback<User> callback) {
<<<<<<< HEAD
        // 发起请求
=======
        // Start a request
>>>>>>> 16b0d4c (fix bugs-Final version)
        responseModelCall.enqueue(new Callback<ResponseModel<AccountResponseModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<AccountResponseModel>> call,
                                   Response<ResponseModel<AccountResponseModel>> response) {
<<<<<<< HEAD
                // 拿response的body
                ResponseModel<AccountResponseModel> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // 如果是个成功的请求
                    AccountResponseModel responseResult = responseModel.getResponse_result();
                    User user = responseResult.getUser();
                    // TODO：没验证设备绑定，后面加了个推之后再加上
                    // 如果已经绑定，直接返回即可
                    // 拿用户并写入客户端的数据库，然后把当前user的id和token存储一下
                    user.save();
                    AccountData.save(responseResult);
                    callback.onSuccess(user);
                } else {
                    // 如果失败了，处理失败的提示
                    int error = Factory.transferResponseErrorCode(responseModel);
=======
                // The body to take response
                ResponseModel<AccountResponseModel> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // If it is a successful request
                    AccountResponseModel responseResult = responseModel.getResponse_result();
                    User user = responseResult.getUser();
                    // Take the user and write it into the client's database,
                    // and then store the id and token of the current user
                    user.save();
                    AccountData.save(responseResult);
                    // TODO: ！！！important，uid will change after uninstall
                    if (responseResult.isBindService()) {
                        // If the previously bound one is different from the current one,
                        // you have to re-bind
                        if(!AccountData.getPushId().equals(responseResult.getPushId())){
                            bindPushId(callback);
                        }
                        callback.onSuccess(user);
                    } else {
                        bindPushId(callback);
                    }
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(responseModel);
>>>>>>> 16b0d4c (fix bugs-Final version)
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AccountResponseModel>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }

<<<<<<< HEAD
=======
    /**
     * Bind device id
     *
     * @param callback
     */
    public static void bindPushId(DataSource.Callback<User> callback) {
        String pushId = AccountData.getPushId();
        if (StringUtils.isNullOrEmpty(pushId)) {
            Log.e("push id wrong", "NO push ID");
            return;
        }
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<AccountResponseModel>> responseModelCall =
                callRemote.bind(pushId, AccountData.getToken());
        responseModelCall.enqueue(new Callback<ResponseModel<AccountResponseModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<AccountResponseModel>> call, Response<ResponseModel<AccountResponseModel>> response) {
                ResponseModel<AccountResponseModel> body = response.body();
                if (body.getCode() == 1) {
                    AccountResponseModel responseResult = body.getResponse_result();
                    User user = responseResult.getUser();
                    user.save();
                    if(callback != null){
                        callback.onSuccess(user);
                    }
                } else {
                    // If it fails, handle the failure prompt
                    int error = BaseFactory.transferResponseErrorCode(body);
                    if(callback != null){
                        callback.onFail(error);
                    }
                    Log.e("bind fail", body.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AccountResponseModel>> call, Throwable t) {
                if(callback != null){
                    callback.onFail(R.string.data_network_error);
                }
                Log.e("bind fail", "network fail");
            }
        });

    }

>>>>>>> 16b0d4c (fix bugs-Final version)
}
