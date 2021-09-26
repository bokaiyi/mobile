package com.mobile.myapp.activities;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.myapp.R;
import com.mobile.myapp.fragments.account.UpdateInfoFragment;
import com.mobile.myapp.tools.ImgSelector;
import com.mobile.util.app.Activity;

/**
 * 更新用户信息
 */
public class UserInfoActivity extends Activity {
    private Fragment curFragment;

    /**
     * 显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.userinfo_activity;
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();
        // 一定要把当前对应的selector丢进去
        curFragment = new UpdateInfoFragment(new ImgSelector(UserInfoActivity.this,
                UserInfoActivity.this));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.userinfo_container, curFragment)
                .commit();
    }

    // 接受图片选择器的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        curFragment.onActivityResult(requestCode, resultCode, data);
    }

}
