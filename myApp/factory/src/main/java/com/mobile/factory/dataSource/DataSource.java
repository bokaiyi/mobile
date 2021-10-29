package com.mobile.factory.dataSource;

/**
 * Data source, call back after receiving the server data
 */
public interface DataSource {
    /**
     * callback interface, the client will make a callback after successfully receiving the data
     */
    interface SuccessCallback<T> {
        void onSuccess(T t);
    }

    /**
     * callback interface, the client will make a callback after failing to accept the data,
     * and only need to return an error.
     */
    interface FailCallback {
        void onFail(int error);
    }

    /**
     * main interface，both have
     * @param <T>
     */
    interface Callback<T> extends FailCallback, SuccessCallback<T>{

    }

}
