package com.mobile.myapp;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
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
import com.mobile.myapp.fragments.main_page.ContactsFragment;
import com.mobile.myapp.fragments.main_page.GroupsFragment;
import com.mobile.myapp.fragments.main_page.HomeFragment;
import com.mobile.util.app.Activity;
import com.yongchun.library.view.ImageSelectorActivity;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MainActivity，继承我们自己写的util里的activity
 */
public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener{
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


    protected HomeFragment homeFragment = new HomeFragment();
    protected ContactsFragment contactsFragment = new ContactsFragment();
    protected GroupsFragment groupsFragment = new GroupsFragment();
    // 避免一直增加fragments
    private int count_frags = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initialWidget() {
        super.initialWidget();


        // 导航栏背景图
        Glide.with(this).load(R.drawable.background)
                .into(new ViewTarget<View, GlideDrawable>(appBar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

        // 设置底部导航栏回调
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // TODO: 模拟器camera调不起来，后面真机试试
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Matisse.from(MainActivity.this)
//                        .choose(MimeType.ofImage(), false)
//                        .countable(true)
//                        .capture(true)
//                        .captureStrategy(
//                                new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test"))
//                        .maxSelectable(9)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(
//                                getResources().getDimensionPixelSize(R.dimen.len_128))
//                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                        .thumbnailScale(0.85f)
//                        .imageEngine(new GlideEngine())
//                        .setOnSelectedListener((uriList, pathList) -> {
//                            Log.e("onSelected", "onSelected: pathList=" + pathList);
//                        })
//                        .showSingleMediaType(true)
//                        .originalEnable(true)
//                        .maxOriginalSize(10)
//                        .autoHideToolbarOnSingleTap(true)
////                        .setOnCheckedListener(isChecked -> {
////                            Log.e("isChecked", "onCheck: isChecked=" + isChecked);
////                        })
//                        .forResult(23);
                ImageSelectorActivity.start(MainActivity.this, 5, 1,
                        true,true,true);
            }
        });
//
    }
//
//    private UriAdapter mAdapter;
    // TODO：这里改一下结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 23 && resultCode == RESULT_OK) {
//            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
//            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            // do something
        }
    }
//
//    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {
//
//        private List<Uri> mUris;
//        private List<String> mPaths;
//
//        void setData(List<Uri> uris, List<String> paths) {
//            mUris = uris;
//            mPaths = paths;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new UriViewHolder(
//                    LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(UriViewHolder holder, int position) {
//            holder.mUri.setText(mUris.get(position).toString());
//            holder.mPath.setText(mPaths.get(position));
//
//            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
//            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mUris == null ? 0 : mUris.size();
//        }
//
//        static class UriViewHolder extends RecyclerView.ViewHolder {
//
//            private TextView mUri;
//            private TextView mPath;
//
//            UriViewHolder(View contentView) {
//                super(contentView);
//                mUri = (TextView) contentView.findViewById(R.id.uri);
//                mPath = (TextView) contentView.findViewById(R.id.path);
//            }
//        }


    @Override
    protected int getContentLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initialData() {
        super.initialData();

        // app一进来就进home
        Menu menu = bottomNavigationView.getMenu();
        menu.performIdentifierAction(R.id.navigation_home, 0);
        count_frags += 1;
        hideFloat();
    }

    /**
     * 搜索监听事件
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_search)
    void onClickSearch() {

    }

    /**
     * 浮动按钮监听事件（添加联系人）
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_float)
    void onClickFloat() {

    }

    /**
     * 点击头像监听事件
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_portrait)
    void onClickPortrait() {

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
                // 如果是初次操作，添加fragment
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, homeFragment)
                        .commit();
                count_frags += 1;
            } else {
                // 否则把当前fragment替换掉
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, homeFragment)
                        .commit();
            }
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
            showFloat();
        } else if (item.getItemId() == R.id.navigation_groups) {
            if (count_frags == 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, groupsFragment)
                        .commit();
                count_frags += 1;
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, groupsFragment)
                        .commit();
            }
            showFloat();
        } else {
            return false;
        }


        if (getSupportFragmentManager().getFragments() != null) {
            Log.e("Tag", String.valueOf(getSupportFragmentManager().getFragments().size()));
        }
        return true;
    }


    /**
     * 只有在groups和contacts页面才会显示float
     */
    public void showFloat() {
        floatActionButton.animate().rotation(360)
                .translationY(-180)
                .setDuration(300)
                .start();
    }

    /**
     * 别的页面隐藏float
     */
    public void hideFloat() {
        floatActionButton.animate().rotation(-360)
                .translationY(180)
                .setDuration(300)
                .start();
    }


}
