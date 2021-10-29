package com.mobile.factory.present.account;

/**
<<<<<<< HEAD
 * 这里没有用basic那个抽离，改了结构的
=======
 * Change structure from the basic
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public interface RegisterPresent {

    interface Presenter {
<<<<<<< HEAD
        // 发起注册
        void requestRegister(String phone, String name, String password);

        // 参数验证
=======
        // Request register
        void requestRegister(String phone, String name, String password);

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

        void registerSuccess();

        void registerFail(int error);

        void loading();
    }


}
