package com.mobile.myApp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.present.user.contact.UserInfoPresent;
import com.mobile.factory.present.user.contact.UserInfoPresentImpl;
import com.mobile.myApp.R;
import com.mobile.util.app.ToolBarActivity;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.identity.UserIdentity;
import com.mobile.util.widget.PortraitView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.TextView;

import java.util.List;

import butterknife.BindView;

/**
 * get user information
 */
public class UserInfoActivity extends ToolBarActivity implements UserInfoPresent.View {
    private static User my_user;
    private UserInfoPresent.Presenter presenter;

    // TODO：too complex, only done this function in searching activity.
    // private MenuItem follow;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_name)
    TextView name_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_sex)
    ImageView sex_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView portraitView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_follows)
    TextView follows_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_following)
    TextView followings_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_desc)
    TextView desc_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_say_hello)
    Button chat_btn;

    /**
     * the entrance of the activity
     *
     * @param context Context
     */
    public static void show(Context context, User user) {
        my_user = user;
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = initialPresenter();
        presenter.getFollows(my_user.getId());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.userinfo_activity;
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();
        setTitle("");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initialData() {
        super.initialData();

        desc_view.setText(my_user.getDescription());
        name_view.setText(my_user.getName());
        if (my_user.getSex() == User.FEMALE) {
            sex_view.setImageResource(R.drawable.ic_womam);
        }
        NetworkHelper.setPortrait(portraitView, my_user, this);

        // TODO: use one backend interface to search the followings temporarily
        follows_view.setText(0 + " follows");
        followings_view.setText(0 + " followings");
    }

    // presenter initialize，return implementation
    protected UserInfoPresent.Presenter initialPresenter() {
        // tranfer itself, will bind the registered presentor to the current view
        return new UserInfoPresentImpl(this);
    }

    // @SuppressLint("NonConstantResourceId")
    // @OnClick(R.id.btn_say_hello)
    // void onClickChat() {
    // // transfer to chat
    // ChatActivity.show(this, my_user);
    // }

    @Override
    public void setPresenter(UserInfoPresent.Presenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSuccess(List<UserIdentity> userList) {
        Toast.makeText(this, "Request succeed!", Toast.LENGTH_SHORT).show();

        follows_view.setText(userList.size() + " follows");
        followings_view.setText(userList.size() + " followings");
    }

    @Override
    public void getFail(int error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading() {
        // Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show();
    }
}
