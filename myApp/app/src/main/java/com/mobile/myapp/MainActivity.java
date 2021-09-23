package com.mobile.myapp;


import android.annotation.SuppressLint;
import android.widget.TextView;

import com.mobile.util.app.Activity;

import butterknife.BindView;

/**
 * MainActivity，继承我们自己写的util里的activity
 */
public class MainActivity extends Activity {
    @SuppressLint("NonConstantResourceId")
    // 获取layout里的控件
    @BindView(R.id.text_test)
    TextView testView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.main_activity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialWidget() {
        super.initialWidget();
        // 可以在这里对控件进行修改
        testView.setText("Test");
    }
}
