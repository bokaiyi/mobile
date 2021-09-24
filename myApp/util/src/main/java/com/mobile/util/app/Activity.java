package com.mobile.util.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 本项目中所有activity的基类，加了返回处理判断
 */
public class Activity extends AppCompatActivity {
    protected Unbinder unbinder;
    protected int layoutId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialWindow();
        if (checkBundle(getIntent().getExtras())) {
            layoutId = getContentLayoutId();
            setContentView(layoutId);
            initialWidget();
            initialData();
        } else {
            finish();
        }

    }

    // 初始化窗口
    protected void initialWindow() {

    }

    // 确认bundle是否初始化成功，可以复写
    protected boolean checkBundle(Bundle bundle) {
        return true;
    }

    // 获取界面资源id，必须复写！！
    protected int getContentLayoutId() {
        return -1;
    }

    // 初始化控件
    protected void initialWidget() {
        unbinder = ButterKnife.bind(this);
    }

    // 初始化数据
    protected void initialData() {

    }

    // 点击导航栏的返回
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    // 手机back键
    @Override
    public void onBackPressed() {
        // 如果有fragment拦截了返回，return
        @SuppressLint("RestrictedApi")
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            for (android.support.v4.app.Fragment fragment : fragments) {
                if(fragment instanceof Fragment && !((com.mobile.util.app.Fragment) fragment).canBack()){
                    return;
                }
            }
        }

        finish();
        super.onBackPressed();
    }
}
