package com.mobile.factory.present.account;

/**
<<<<<<< HEAD
 * 这里没有用basic那个抽离，改了结构的
 */
public interface LoginPresent {
    interface Presenter {
        // 发起登录
        void requestLogin(String phone, String password);

        // 参数验证
=======
 * Change structure from basic
 */
public interface LoginPresent {
    interface Presenter {
        // Request login
        void requestLogin(String phone, String password);

        // Check input
>>>>>>> 16b0d4c (fix bugs-Final version)
        boolean checkInput(String input);

        void start();

        void finish();

    }

    interface View {
<<<<<<< HEAD
        // 设置一个presenter
=======
        // Set a presenter
>>>>>>> 16b0d4c (fix bugs-Final version)
        void setPresenter(Presenter presenter);

        void LoginSuccess();

        void LoginFail(int error);

        void loading();
    }
}
