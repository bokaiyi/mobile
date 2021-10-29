package com.mobile.myApp.fragments.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobile.factory.present.account.RegisterPresent;
import com.mobile.factory.present.account.RegisterPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * register page
 */
public class RegisterFragment extends Fragment implements RegisterPresent.View {
    private ViewTransfer viewTransfer; // used for transfer
    private RegisterPresent.Presenter registerPresent; // used for request and present

    // Lint bind
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_phone)
    net.qiujuer.genius.ui.widget.EditText input_phone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_password)
    net.qiujuer.genius.ui.widget.EditText input_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_name)
    net.qiujuer.genius.ui.widget.EditText input_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.go_login)
    FrameLayout go_login_button;

    // presenter initialize，return instance
    protected RegisterPresent.Presenter initialPresenter() {

        return new RegisterPresentImpl(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // initialize and transfer presenter
        viewTransfer = (ViewTransfer) context;
        registerPresent = initialPresenter();
    }

    /**
     * register submit
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = input_phone.getText().toString();
        String name = input_name.getText().toString();
        String password = input_password.getText().toString();
        registerPresent.requestRegister(phone, name, password);
    }

    /**
     * transfer to login
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.go_login)
    void onGoLoginClick() {
        // transfer
        viewTransfer.transfer();
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(getContext(), "Register succeed!", Toast.LENGTH_SHORT).show();

        // transfer to main and end current activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void registerFail(int error) {
        if (placeHolderView != null) {
            placeHolderView.triggerError(error);
        } else {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }

        // stop loading and enable input
        loading.stop();
        input_phone.setEnabled(true);
        input_password.setEnabled(true);
        input_name.setEnabled(true);
        submit_button.setEnabled(true);
    }

    @Override
    public void loading() {
        if (placeHolderView != null) {
            placeHolderView.triggerLoading();
        } else {
            Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();
        }

        // start loading and stop input
        loading.start();
        input_phone.setEnabled(false);
        input_password.setEnabled(false);
        input_name.setEnabled(false);
        submit_button.setEnabled(false);
    }

    @Override
    public void setPresenter(RegisterPresent.Presenter presenter) {
        this.registerPresent = presenter;
    }
}
