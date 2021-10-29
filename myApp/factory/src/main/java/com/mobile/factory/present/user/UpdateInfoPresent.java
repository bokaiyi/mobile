package com.mobile.factory.present.user;

public interface UpdateInfoPresent {
    interface Presenter {
<<<<<<< HEAD
        // 发起更新
        void requestUpdate(String localPortraitPath, String description, boolean isMale);

        // 参数验证
=======
        // request update
        void requestUpdate(String localPortraitPath, String description, boolean isMale);

        // check input
>>>>>>> 16b0d4c (fix bugs-Final version)
        boolean checkInput(String input);

        void start();

        void finish();

    }

    interface View {
<<<<<<< HEAD
        // 设置一个presenter
=======
        // set a presenter
>>>>>>> 16b0d4c (fix bugs-Final version)
        void setPresenter(UpdateInfoPresent.Presenter presenter);

        void UpdateInfoSuccess();

        void UpdateInfoFail(int error);

        void loading();
    }
}
