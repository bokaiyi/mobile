package com.mobile.myApp.fragments.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobile.factory.present.account.LoginPresent;
import com.mobile.factory.present.account.LoginPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * page for login
 */
public class LoginFragment extends Fragment implements LoginPresent.View {
    private LoginPresent.Presenter loginPresent;
    private ViewTransfer viewTransfer; // used for view swapping

    // bind lint
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_phone)
    net.qiujuer.genius.ui.widget.EditText input_phone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_password)
    net.qiujuer.genius.ui.widget.EditText input_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.go_register)
    FrameLayout go_register_button;

    // presenter initiailize, return instance
    protected LoginPresent.Presenter initialPresenter() {

        return new LoginPresentImpl(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // initilize presenter
        viewTransfer = (ViewTransfer) context;
        loginPresent = initialPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setPresenter(LoginPresent.Presenter presenter) {
        this.loginPresent = presenter;
    }

    /**
     * transfer to register
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.go_register)
    void onGoLoginClick() {
        // transfer
        viewTransfer.transfer();
    }

    /**
     * login submit
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = input_phone.getText().toString();
        String password = input_password.getText().toString();
        loginPresent.requestLogin(phone, password);
    }

    @Override
    public void LoginSuccess() {
        Toast.makeText(getContext(), "Login succeed!", Toast.LENGTH_SHORT).show();

        // go to main and end current activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void LoginFail(int error) {
        if (placeHolderView != null) {
            placeHolderView.triggerError(error);
        } else {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }

        // stop loading and enable input
        loading.stop();
        input_phone.setEnabled(true);
        input_password.setEnabled(true);
        submit_button.setEnabled(true);
    }

    @Override
    public void loading() {
        if (placeHolderView != null) {
            placeHolderView.triggerLoading();
        } else {
            Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();
        }

        // start loading and disable input
        loading.start();
        input_phone.setEnabled(false);
        input_password.setEnabled(false);
        submit_button.setEnabled(false);
    }

}
