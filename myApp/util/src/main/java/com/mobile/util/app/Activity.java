package com.mobile.util.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.FractionRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
<<<<<<< HEAD
 * 本项目中所有activity的基类，加了返回处理判断
=======
 * The base class of all activity in this project, adding return processing judgment
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class Activity extends AppCompatActivity {
    protected Unbinder unbinder;
    protected int layoutId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 设置状态栏背景透明
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

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

<<<<<<< HEAD
    // 初始化窗口
=======
    // initialize window
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected void initialWindow() {

    }

<<<<<<< HEAD
    // 确认bundle是否初始化成功，可以复写
=======
    //Confirm whether the bundle is initialized successfully and can be override
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected boolean checkBundle(Bundle bundle) {
        return true;
    }

<<<<<<< HEAD
    // 获取界面资源id，必须复写！！
=======
    // get content layout id, must override!!!
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected int getContentLayoutId() {
        return -1;
    }

<<<<<<< HEAD
    // 初始化控件
=======
    // initialize widget
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected void initialWidget() {
        unbinder = ButterKnife.bind(this);
    }

<<<<<<< HEAD
    // 初始化数据
=======
    // initialize data
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected void initialData() {

    }

<<<<<<< HEAD
    // 点击导航栏的返回
=======
    // return by click navigation bar
>>>>>>> 16b0d4c (fix bugs-Final version)
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

<<<<<<< HEAD
    // 手机back键
    @Override
    public void onBackPressed() {
        // 如果有fragment拦截了返回，return
=======
    //  the phone's back button
    @Override
    public void onBackPressed() {
        // If a fragment intercepts the return, return
>>>>>>> 16b0d4c (fix bugs-Final version)
        @SuppressLint("RestrictedApi")
        List<androidx.fragment.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if(fragment instanceof Fragment && !((com.mobile.util.app.Fragment) fragment).canBack()){
                    return;
                }
            }
        }

        finish();
        super.onBackPressed();
    }
}
