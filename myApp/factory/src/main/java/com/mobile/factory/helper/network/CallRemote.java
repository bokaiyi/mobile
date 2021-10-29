package com.mobile.factory.helper.network;

<<<<<<< HEAD
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.account.LoginModel;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.api.user.UserUpdateInfoModel;
import com.mobile.util.model.db.identity.UserIdentity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * 请求
 */
public interface CallRemote {
    /**
     * 注册请求
     *
     * @param registerModel 传入一个registerModel
     * @return 返回一个ResponseModel<AccountResponseModel>
=======
import com.mobile.factory.model.api.ResponseModel;
import com.mobile.factory.model.api.account.AccountResponseModel;
import com.mobile.factory.model.api.account.LoginModel;
import com.mobile.factory.model.api.account.RegisterModel;
import com.mobile.factory.model.api.user.UserUpdateInfoModel;
import com.mobile.factory.model.db.identity.MessageIdentity;
import com.mobile.factory.model.db.identity.UserIdentity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Request
 */
public interface CallRemote {
    /**
     * Register request
     *
     * @param registerModel pass one registerModel
     * @return return a ResponseModel<AccountResponseModel>
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    @POST("account/register")
    Call<ResponseModel<AccountResponseModel>> register(@Body RegisterModel registerModel);

    /**
<<<<<<< HEAD
     * 登录请求
     *
     * @param loginModel 传入一个loginModel
     * @return 返回一个ResponseModel<AccountResponseModel>
=======
     * Login request
     *
     * @param loginModel Pass a loginModel
     * @return Return a ResponseModel<AccountResponseModel>
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    @POST("account/login")
    Call<ResponseModel<AccountResponseModel>> login(@Body LoginModel loginModel);

    /**
<<<<<<< HEAD
     * 更新用户信息请求
     * @param userUpdateInfoModel
     * @param token token要设置在header里
=======
     * Update user information request
     *
     * @param userUpdateInfoModel
     * @param token   The token should be set in the header
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    @PUT("user/updateInfo")
    Call<ResponseModel<UserIdentity>> updateUserInfo(@Body UserUpdateInfoModel userUpdateInfoModel,
<<<<<<< HEAD
                                            @Header("token") String token);

=======
                                                     @Header("token") String token);

    /**
     * Get user according to the name
     *
     * @param token
     * @param name
     * @return
     */
    @GET("user/find/{name}")
    Call<ResponseModel<List<UserIdentity>>> searchUser(@Header("token") String token,
                                                       @Path("name") String name);

    /**
     * Follow a person
     *
     * @param token
     * @param followId
     * @return
     */
    @PUT("user/follow/{followId}")
    Call<ResponseModel<UserIdentity>> followUser(@Header("token") String token,
                                                 @Path("followId") String followId);

    /**
     * Get the contact list
     * @param token
     * @return
     */
    @GET("user/contacts")
    Call<ResponseModel<List<UserIdentity>>> findContacts(@Header("token") String token);

    /**
     * Get contact list through id
     * @param token
     * @return
     */
    @GET("user/contacts/{userId}")
    Call<ResponseModel<List<UserIdentity>>> findContactsByID(@Header("token") String token,
                                                             @Path("userId") String userId);

    @GET("user/{userId}")
    Call<ResponseModel<UserIdentity>> uFind(@Path("userId") String userId,
                                            @Header("token") String token);

    /**
     * Bind device
     * @param token
     * @return
     */
    @POST("account/bind/{pushId}")
    Call<ResponseModel<AccountResponseModel>> bind(@Path("pushId") String pushId,
                                                   @Header("token") String token);

    /**
     * Get message list
     * @param receiverId
     * @param token
     * @return
     */
    @GET("message/{receiverId}")
    Call<ResponseModel<List<MessageIdentity>>> getHistory(@Path("receiverId") String receiverId,
                                                          @Header("token") String token);

    /**
     * Get unread message
     * @param token
     * @return
     */
    @GET("message/unread")
    Call<ResponseModel<List<MessageIdentity>>> getUnread(@Header("token") String token);

>>>>>>> 16b0d4c (fix bugs-Final version)
}
