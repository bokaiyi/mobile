package com.mobile.myApp.activities;

import android.content.Context;
import android.content.Intent;

import com.mobile.myApp.R;
import com.mobile.myApp.fragments.account.LoginFragment;
import com.mobile.myApp.fragments.account.RegisterFragment;
import com.mobile.myApp.fragments.account.ViewTransfer;
import com.mobile.util.app.Activity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.ImageView;

import butterknife.BindView;

public class AccountActivity extends Activity implements ViewTransfer {
    private Fragment curFragment;
    private Fragment loginFragment;
    private Fragment registerFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;

    /**
     * The entrance of the account activity
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.account_activity;
    }


    @Override
    protected void initialWidget() {
        super.initialWidget();

        // initialize Fragment，loginFragment is loginFragment
        curFragment = loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, curFragment)
                .commit();

    }

    /**
     * tranfer interface
     */
    @Override
    public void transfer() {
        Fragment fragment;
        if (curFragment == loginFragment) {
            if (registerFragment == null) {
                registerFragment = new RegisterFragment();
            }
            fragment = registerFragment;
        } else {
            fragment = loginFragment;
        }

        curFragment = fragment;
        // change display
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, curFragment)
                .commit();
    }

}
