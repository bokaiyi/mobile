package com.mobile.myapp;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.FrameLayout;

import com.mobile.util.app.Activity;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;


/**
 * MainActivity，继承我们自己写的util里的activity
 */
public class MainActivity extends Activity {
    @BindView(R.id.main_appbar)
    View appBar;

    @BindView(R.id.main_content)
    FrameLayout content;

    @BindView(R.id.main_bottom)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.main_float)
    FloatActionButton floatActionButton;


    /**
     * MainActivity 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.main_activity;
    }
}
