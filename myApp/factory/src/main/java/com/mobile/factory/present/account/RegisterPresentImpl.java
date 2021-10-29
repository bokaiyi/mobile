package com.mobile.factory.present.account;

import com.mobile.factory.R;
<<<<<<< HEAD
import com.mobile.util.data.DataSource;
import com.mobile.factory.helper.account.AccountHelper;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.db.entity.User;
=======
import com.mobile.factory.dataSource.DataSource;
import com.mobile.factory.helper.account.AccountHelper;
import com.mobile.factory.model.api.account.RegisterModel;
import com.mobile.factory.model.db.entity.User;
>>>>>>> 16b0d4c (fix bugs-Final version)
import com.raizlabs.android.dbflow.StringUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
<<<<<<< HEAD
 * 没有写basePresenter基类，直接用就行
 */
public class RegisterPresentImpl implements RegisterPresent.Presenter {
    // 当前presenter的视图
=======
 * no base basePresenter，use directly
 */
public class RegisterPresentImpl implements RegisterPresent.Presenter {
    // current presenter view
>>>>>>> 16b0d4c (fix bugs-Final version)
    private RegisterPresent.View my_view;

    public RegisterPresent.View getMy_view() {
        return my_view;
    }

    public void setMy_view(RegisterPresent.View my_view) {
        this.my_view = my_view;
    }

    /**
<<<<<<< HEAD
     * 构造器，传递进来一个view
=======
     * Contructor，pass in a view
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param view
     */
    public RegisterPresentImpl(RegisterPresent.View view) {
        setMy_view(view);
<<<<<<< HEAD
        view.setPresenter(this); // 把presenter设置回去
    }

    /**
     * 发起注册请求
=======
        view.setPresenter(this); // set presenter back
    }

    /**
     * Request register
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @param phone
     * @param name
     * @param password
     */
    @Override
    public void requestRegister(String phone, String name, String password) {
        start();

<<<<<<< HEAD
        // 拿到当前view
        RegisterPresent.View cur_view = getMy_view();

        // 检查参数
=======
        // get current view
        RegisterPresent.View cur_view = getMy_view();

        // check input
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (!checkInput(phone)) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_mobile);
        } else if (!checkInput(password) || password.length() < 6) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_password);
        } else if (!checkInput(name)) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_name);
        } else {
<<<<<<< HEAD
            // 发起网络请求
            AccountHelper.register(new RegisterModel(phone, password, name), new DataSource.Callback<User>() {
                @Override
                public void onFail(int error) {
                    // 注册失败，返回一个错误码
=======
            // request network
            AccountHelper.register(new RegisterModel(phone, password, name), new DataSource.Callback<User>() {
                @Override
                public void onFail(int error) {
                    // register fail，return an error
>>>>>>> 16b0d4c (fix bugs-Final version)
                    if (cur_view != null) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                cur_view.registerFail(error);
                            }
                        });
                    }
                }

                @Override
                public void onSuccess(User user) {
<<<<<<< HEAD
                    // 注册成功，回送一个用户
                    // 调用注册成功即可
=======
                    // register successful，return an user
                    // call registerSuccess()
>>>>>>> 16b0d4c (fix bugs-Final version)
                    if (cur_view != null) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                cur_view.registerSuccess();
                            }
                        });
                    }
                }
            });

        }
    }

    /**
     * 检查参数是否正确(不为空就行了，没写持久化那个正则)
     *
     * @param input
     */
    @Override
    public boolean checkInput(String input) {
        return !StringUtils.isNullOrEmpty(input);
    }

    @Override
    public void start() {
<<<<<<< HEAD
        // 如果不为空，显示loading
=======
        // If not null，show loading
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (my_view != null) {
            my_view.loading();
        }
    }

    @Override
    public void finish() {
<<<<<<< HEAD
        // 设置presenter和当前view为空
=======
        // Set presenter and current view null
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (my_view != null) {
            my_view.setPresenter(null);
        }
        setMy_view(null);
    }
}
