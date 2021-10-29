package com.mobile.factory;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mobile.factory.helper.user.UserCenter;
import com.mobile.factory.helper.user.UserDispatcher;
import com.mobile.factory.model.api.ResponseModel;
import com.mobile.factory.model.db.DBExclusionStrategy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BaseFactory {
    private static final String TAG = BaseFactory.class.getSimpleName();
    // Singleton mode
    private static final BaseFactory factory = new BaseFactory();
    //Global thread pool
    private final Executor executor;
    //Global Gson
    private final Gson gson;


    private BaseFactory() {
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                // Set the time format
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                // Database class does not use gson
                .setExclusionStrategies(new DBExclusionStrategy())
                .create();
    }


    public static BaseFactory getFactory() {
        return factory;
    }


    /**
     * Return the global Application
     *
     * @return Application
     */
    public static Application app() {
        return Application.getApp();
    }


    /**
     * Run on Asynchronous method
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        factory.executor.execute(runnable);
    }

    /**
     * Return a general Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return factory.gson;
    }

    public static int transferResponseErrorCode(ResponseModel responseModel) {
        // Do not handle empty model and successful ones
        if (responseModel != null && responseModel.getCode() != 1) {
            if (responseModel.getCode() == ResponseModel.ERROR_UNDEFINED) {
                return com.mobile.util.R.string.data_rsp_error_unknown;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_USER) {
                return com.mobile.util.R.string.data_rsp_error_create_user;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_BIND_SERVICE_FAIL) {
                return com.mobile.util.R.string.data_rsp_error_bind_service;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_LOGIN_FAIL) {
                return com.mobile.util.R.string.data_rsp_error_account_login;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_GROUP) {
                return com.mobile.util.R.string.data_rsp_error_create_group;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_NO_PERMISSION) {
                return com.mobile.util.R.string.data_rsp_error_account_no_permission;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_REGISTER_FAIL) {
                return com.mobile.util.R.string.data_rsp_error_account_register;
            } else if (responseModel.getCode() == ResponseModel.ERROR_BACKEND_SERVICE) {
                return com.mobile.util.R.string.data_rsp_error_service;
            } else if (responseModel.getCode() == ResponseModel.ERROR_WRONG_ACCOUNT_TOKEN) {
                return com.mobile.util.R.string.data_rsp_error_account_token;
            } else if (responseModel.getCode() == ResponseModel.ERROR_GROUP_MEMBER_NOT_FOUND) {
                return com.mobile.util.R.string.data_rsp_error_not_found_group_member;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS_ACCOUNT_EXIST) {
                return com.mobile.util.R.string.data_rsp_error_parameters_exist_account;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS_NAME_EXIST) {
                return com.mobile.util.R.string.data_rsp_error_parameters_exist_name;
            } else if (responseModel.getCode() == ResponseModel.ERROR_GROUP_NOT_FOUND) {
                return com.mobile.util.R.string.data_rsp_error_not_found_group;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_GROUP_MEMBER) {
                return com.mobile.util.R.string.data_rsp_error_create_user;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS) {
                return com.mobile.util.R.string.data_rsp_error_parameters;
            } else if (responseModel.getCode() == ResponseModel.ERROR_USER_UPDATE_INFO_FAIL) {
                return com.mobile.util.R.string.data_account_update_invalid_parameter;
            } else if (responseModel.getCode() == ResponseModel.ERROR_USER_NOT_FOUND) {
                return com.mobile.util.R.string.data_rsp_error_not_found_user;
            }
        }
        return -1;
    }

    /**
     * Get a user center implementation class
     *
     * @return User-center's interface
     */
    public static UserCenter getUserCenter() {

        return UserDispatcher.instance();
    }


}

