package com.mobile.util.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 本项目中所有fragment的基类
 */
public class Fragment extends android.support.v4.app.Fragment {
    protected View rootView;
    public int layoutId;
    protected Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        checkBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            layoutId = getContentLayoutId();
            rootView = inflater.inflate(layoutId, container, false);
            initialWidget(rootView);
        } else if (rootView.getParent() != null) {
            // 如果是从上级返回的，把当前的view删掉
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化数据
        initialData();
    }

    // 确认bundle是否初始化成功
    protected boolean checkBundle(Bundle bundle) {
        return true;
    }

    // 获取界面资源id，必须复写
    protected int getContentLayoutId() {
        return -1;
    }

    // 初始化控件
    protected void initialWidget(View view) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    // 初始化数据
    protected void initialData() {

    }

    // 点击返回时触发，告诉parent是否有拦截
    public boolean canBack() {
        return true;
    }
}
