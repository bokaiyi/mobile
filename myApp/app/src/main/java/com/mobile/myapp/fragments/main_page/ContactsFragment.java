package com.mobile.myApp.fragments.main_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.present.user.contact.ContactsPresent;
import com.mobile.factory.present.user.contact.ContactsPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.ChatActivity;
import com.mobile.myApp.activities.UserInfoActivity;
import com.mobile.util.WrapContentLinearLayoutManager;
import com.mobile.factory.Application;
import com.mobile.util.app.Fragment;
import com.mobile.factory.model.db.entity.User;
import com.mobile.util.widget.EmptyView;
import com.mobile.util.widget.PortraitView;
import com.mobile.util.widget.recycler.Adapter;

import net.qiujuer.genius.ui.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * get list for contact person
 */
public class ContactsFragment extends Fragment implements ContactsPresent.View {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.empty)
    EmptyView emptyView;

    private Adapter<User> adapter;
    private ContactsPresent.Presenter presenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initialData() {
        super.initialData();
        // load the data
        presenter.start();
    }

    @Override
    protected void initialWidget(View view) {
        super.initialWidget(view);

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new Adapter<User>() {
            @Override
            protected com.mobile.util.widget.recycler.ViewHolder<User> createHolder(View view, int viewType) {
                return new ViewHolder(view);
            }

            @Override
            protected int getItemViewType(int position, User data) {
                // return cell layout id
                return R.layout.cell_contacts;
            }
        });

        // bind view
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = initialPresenter();
    }

    // presenter initialize, return instance
    protected ContactsPresent.Presenter initialPresenter() {
        
        return new ContactsPresentImpl(this);
    }

    @Override
    public Adapter<User> getAdapter() {
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
    public void setPresenter(ContactsPresent.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loading() {
        emptyView.triggerLoading();
    }


    /**
     * return very view for cell
     */
    static class ViewHolder extends com.mobile.util.widget.recycler.ViewHolder<User> {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.contact_portrait)
        PortraitView portraitView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.contact_desc)
        TextView descView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.contact_name)
        TextView nameView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.chat)
        ImageView chatView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private User my;

        @Override
        protected void bindData(User data) {
            my = data;
            
            if (data.getPortrait() != null) {
                NetworkHelper.setPortrait(portraitView, data, Application.getAppContext());
            }
            descView.setText(data.getDescription());
            nameView.setText(data.getName());
        }

        /**
         * get user info when avatar clicked
         */
        @SuppressLint("NonConstantResourceId")
        @OnClick(R.id.contact_portrait)
        public void onClickPortrait() {
            UserInfoActivity.show(this.portraitView.getContext(), my);
        }

        /**
         * start chat
         */
        @SuppressLint("NonConstantResourceId")
        @OnClick(R.id.chat)
        public void onClickChat() {
            ChatActivity.show(this.portraitView.getContext(), my);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initialData();
    }
}