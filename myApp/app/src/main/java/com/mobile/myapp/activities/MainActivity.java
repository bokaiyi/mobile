package com.mobile.myApp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.myApp.R;
import com.mobile.myApp.fragments.main_page.ContactsFragment;
import com.mobile.myApp.fragments.main_page.HomeFragment;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.util.app.Activity;
import com.mobile.factory.model.db.entity.User;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.qiujuer.genius.ui.widget.FloatActionButton;


import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * MainActivity，inherit the activity from util
 */
public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_appbar)
    View appBar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_content)
    FrameLayout content;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_bottom)
    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_float)
    FloatActionButton floatActionButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_portrait)
    CircleImageView portraitView;


    protected HomeFragment homeFragment = new HomeFragment();
    protected ContactsFragment contactsFragment = new ContactsFragment();
    // avoid repeatly adding fragments
    private int count_frags = 0;

    private int navigation = 0; // determine which navigation, 1 for home, 2 for contact，3 for group

    /**
     * MainActivity the entrance of all activity
     *
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean checkBundle(Bundle bundle) {
        if (AccountData.userInfoFinished()) {
            return super.checkBundle(bundle);
        }
        UserActivity.show(this);
        return false; // main activity will close later
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();

        // the background of navigation bar
        Glide.with(this).load(R.drawable.background)
                .into(new ViewTarget<View, GlideDrawable>(appBar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

        // set on navigation item selected
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        // TODO：!!! follow this way to take portrait, or it will be null
        User user = AccountData.getUser();
        NetworkHelper.setPortrait(portraitView, user, this);
    }

    @Override
    protected int getContentLayoutId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return R.layout.main_activity;
    }

    @Override
    protected void initialData() {
        super.initialData();

        // enter home page initially
        Menu menu = bottomNavigationView.getMenu();
        menu.performIdentifierAction(R.id.navigation_home, 0);
        navigation = 1;
        count_frags += 1;
        hideFloat();
    }

    /**
     * on click search
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_search)
    void onClickSearch() {
        if (navigation == 3) {
            // TODO: group searching activity
            SearchActivity.show(this, 2);
        } else if (navigation == 1 || navigation == 2) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("SEARCH_TYPE", 1);
            this.startActivityForResult(intent, 1);
        } else {
            SearchActivity.show(this, 0);
        }
    }

    /**
     * on click float（add contact person）
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_float)
    void onClickFloat() {
        Log.e("clicked search", String.valueOf(navigation));
        // determine group or person currently
        if (navigation == 3) {
            // TODO: add group activity

        } else if (navigation == 1 || navigation == 2) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("SEARCH_TYPE", 1);
            this.startActivityForResult(intent, 1);
        } else {
            SearchActivity.show(this, 0);
        }
    }

    /**
     * on click portrait
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_portrait)
    void onClickPortrait() {
        UserInfoActivity.show(this, AccountData.getUser());
    }

    /**
     * on click logout
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.logout)
    void onClickQuit() {
        // clear the local dataBase
        SQLite.delete()
                .from(User.class)
                .execute();
        AccountActivity.show(this);
        this.finish();
    }

    /**
     * 切换导航栏监听
     *
     * @param item
     * @return
     */
    @SuppressLint({"CommitTransaction", "SetTextI18n", "RestrictedApi"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_home) {
            if (count_frags == 0) {
                // if this is the first time, add fragment
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, homeFragment)
                        .commit();
                count_frags += 1;
            } else {
                // or replace the current fragmant
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, homeFragment)
                        .commit();
            }
            navigation = 1;
            hideFloat();
        } else if (item.getItemId() == R.id.navigation_contacts) {
            if (count_frags == 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, contactsFragment)
                        .commit();
                count_frags += 1;
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, contactsFragment)
                        .commit();
            }
            navigation = 2;
            showFloat();
//        } else if (item.getItemId() == R.id.navigation_groups) {
//            if (count_frags == 0) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_content, groupsFragment)
//                        .commit();
//                count_frags += 1;
//            } else {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_content, groupsFragment)
//                        .commit();
//            }
//            navigation = 3;
//            showFloat();
        } else {
            return false;
        }


        if (getSupportFragmentManager().getFragments() != null) {
            Log.e("Fragment Number", String.valueOf(getSupportFragmentManager().getFragments().size()));
        }
        return true;
    }


    /**
     * only show float in groups and contacts activity
     */
    public void showFloat() {
        floatActionButton.animate().rotation(360)
                .translationY(-180)
                .setDuration(300)
                .start();
    }

    /**
     * hide float in other activities
     */
    public void hideFloat() {
        floatActionButton.animate().rotation(-360)
                .translationY(180)
                .setDuration(300)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // refresh the data after searching
        if(requestCode == 1){
            contactsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
