package com.mobile.myApp.activities;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.myApp.R;
import com.mobile.myApp.fragments.account.UpdateInfoFragment;
import com.mobile.myApp.tools.ImgSelector;
import com.mobile.util.app.Activity;

/**
 *
 */
public class UserActivity extends Activity {
    private Fragment curFragment;

    /**
     * the entrance of the activity
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, UserActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.user_activity;
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();
        // have to put here the correspending selector
        curFragment = new UpdateInfoFragment(new ImgSelector(UserActivity.this,
                UserActivity.this));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.userinfo_container, curFragment)
                .commit();
    }

    // receive the result of image selector
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        curFragment.onActivityResult(requestCode, resultCode, data);
    }
}
