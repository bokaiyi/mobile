package com.mobile.myApp.fragments.main_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.factory.Application;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.model.db.entity.Message;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.present.user.contact.ContactsPresent;
import com.mobile.factory.present.user.contact.ContactsPresentImpl;
import com.mobile.factory.present.user.session.UnreadMsgPresent;
import com.mobile.factory.present.user.session.UnreadMsgPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.ChatActivity;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.myApp.activities.SearchActivity;
import com.mobile.myApp.activities.UserInfoActivity;
import com.mobile.util.WrapContentLinearLayoutManager;
import com.mobile.util.app.Fragment;
import com.mobile.util.widget.EmptyView;
import com.mobile.util.widget.PortraitView;
import com.mobile.util.widget.recycler.Adapter;
import com.mobile.util.widget.recycler.ViewHolder;

import net.qiujuer.genius.ui.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * show all unread meesage
 * voice should be in other format,not in url
 */
public class HomeFragment extends Fragment implements UnreadMsgPresent.View {
    private UnreadMsgPresent.Presenter presenter;
    private Adapter<Message> adapter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.empty)
    EmptyView emptyView;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialData() {
        super.initialData();
        presenter.start();
    }

    @Override
    protected void initialWidget(View view) {
        super.initialWidget(view);

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new Adapter<Message>() {
            @Override
            protected com.mobile.util.widget.recycler.ViewHolder<Message> createHolder(View view, int viewType) {
                return new HomeFragment.ViewHolder(view);
            }

            @Override
            protected int getItemViewType(int position, Message data) {
                // return cell layout id
                return R.layout.cell_unread;
            }

        });

        // transfer to chat view after clicked
        adapter.setAdapterListener(new Adapter.AdapterListener<Message>() {
            @Override
            public void onItemClick(com.mobile.util.widget.recycler.ViewHolder<Message> holder, Message data) {
                ChatActivity.my_user = data.getSender();
                startActivityForResult(new Intent(getContext(), ChatActivity.class), 2);
            }

            @Override
            public void onItemLongClick(com.mobile.util.widget.recycler.ViewHolder<Message> holder, Message data) {

            }
        });

        // bind view
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            presenter.start();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = initialPresenter();
    }

    // presenter initialize, return instance
    protected UnreadMsgPresent.Presenter initialPresenter() {
        
        return new UnreadMsgPresentImpl(this);
    }

    @Override
    public Adapter<Message> getAdapter() {
        return adapter;
    }

    @Override
    public void notifyViewWhenDataChanged() {
        emptyView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    @Override
    public void requestFail(int error) {
        emptyView.triggerError(error);
    }

    @Override
    public void setPresenter(UnreadMsgPresent.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loading() {
        emptyView.triggerLoading();
    }

    /**
     * show cell for layout
     */
    static class ViewHolder extends com.mobile.util.widget.recycler.ViewHolder<Message> {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.sender_portrait)
        PortraitView portraitView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.sender_message)
        TextView messageView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.sender_name)
        TextView nameView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.sender_time)
        TextView timeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private User my;  // current sender

        @SuppressLint("SetTextI18n")
        @Override
        protected void bindData(Message data) {
            super.bindData(data);
            my = data.getSender();
            if (my.getPortrait() != null) {
                NetworkHelper.setPortrait(portraitView, my, Application.getAppContext());
            }
            nameView.setText(my.getName());
            if (data.getType() == Message.TYPE_AUDIO) {
                messageView.setText("[audio]");
            } else {
                messageView.setText(data.getContent());
            }
            // bind time
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            timeView.setText(sdf.format(data.getCreateAt()));
        }


        /**
         * get user information when avatar clicked
         */
        @SuppressLint("NonConstantResourceId")
        @OnClick(R.id.sender_portrait)
        public void onClickPortrait() {
            UserInfoActivity.show(this.portraitView.getContext(), my);
        }
    }
}