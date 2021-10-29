package com.mobile.util.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

<<<<<<< HEAD
=======
import com.mobile.util.widget.occupy.PlaceHolderView;

>>>>>>> 16b0d4c (fix bugs-Final version)
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
<<<<<<< HEAD
 * 本项目中所有fragment的基类
=======
 * The base class of all fragments in this project
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class Fragment extends androidx.fragment.app.Fragment {
    protected View rootView;
    public int layoutId;
    protected Unbinder unbinder;
<<<<<<< HEAD
=======
    public PlaceHolderView placeHolderView; // place holder view

    protected boolean isFirstInitial = true; // Determine if it is the first initialization,
    // otherwise the data will be reloaded every time

    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.placeHolderView = placeHolderView;
    }
>>>>>>> 16b0d4c (fix bugs-Final version)

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
<<<<<<< HEAD
            // 如果是从上级返回的，把当前的view删掉
=======
            // If it is returned from the parent, delete the current view
>>>>>>> 16b0d4c (fix bugs-Final version)
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
<<<<<<< HEAD
        // 初始化数据
        initialData();
    }

    // 确认bundle是否初始化成功
=======
        // initialize data
        if (isFirstInitial) {
            isFirstInitial = false;
            initialFirstData();
        }
        initialData();

    }

    //Confirm whether the bundle is initialized successfully
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected boolean checkBundle(Bundle bundle) {
        return true;
    }

<<<<<<< HEAD
    // 获取界面资源id，必须复写！！
=======
    // get the content layout id, must override!!!
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected int getContentLayoutId() {
        return -1;
    }

<<<<<<< HEAD
    // 初始化控件
=======
    // initialize widget
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected void initialWidget(View view) {
        unbinder = ButterKnife.bind(this, rootView);
    }

<<<<<<< HEAD
    // 初始化数据
=======
    // initialize data
>>>>>>> 16b0d4c (fix bugs-Final version)
    protected void initialData() {

    }

<<<<<<< HEAD
    // 点击返回时触发，告诉parent是否有拦截
=======
    // Initialize the first data
    protected void initialFirstData() {

    }

    // Triggered when you click to return,
    // tell the parent if there is an interception
>>>>>>> 16b0d4c (fix bugs-Final version)
    public boolean canBack() {
        return true;
    }

}
