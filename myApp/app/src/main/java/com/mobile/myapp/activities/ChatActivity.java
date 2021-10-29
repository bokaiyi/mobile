package com.mobile.myApp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.model.db.identity.MessageIdentity;
import com.mobile.myApp.R;
import com.mobile.myApp.fragments.message.ChatWithPersonFragment;
import com.mobile.util.Config;
import com.mobile.util.app.Activity;
import com.mobile.util.app.Fragment;
import com.mobile.factory.model.db.entity.Group;
import com.mobile.factory.model.db.entity.User;
import com.mobile.util.widget.PortraitView;
import com.raizlabs.android.dbflow.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * For chatting
 */
public class ChatActivity extends Activity {
    static boolean isPerson = true; // determine isPerson or not
    public static User my_user; // receive person
    static Group my_group; // receive group

    /**
     * The entrance of chat with person activity
     *
     */
    public static void show(Context context, User user) {
        if (user == null || StringUtils.isNullOrEmpty(user.getId())) {
            return;
        }
        my_user = user;
        context.startActivity(new Intent(context, ChatActivity.class));
    }

    /**
     * The entrance of chat with group activity
     *
     * @param context
     */
    public static void show(Context context, Group group) {
        if (group == null || StringUtils.isNullOrEmpty(group.getId())) {
            return;
        }
        isPerson = false;
        my_group = group;
        context.startActivity(new Intent(context, ChatActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.chat_activity;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initialWidget() {
        super.initialWidget();
        Fragment fragment = null;
        if (isPerson) {
            fragment = new ChatWithPersonFragment(my_user);
        } else {
//            fragment = new ChatWithGroupFragment(my_group);
        }
        assert fragment != null;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }
}
