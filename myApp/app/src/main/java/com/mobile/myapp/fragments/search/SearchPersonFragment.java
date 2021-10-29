package com.mobile.myApp.fragments.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.factory.present.search.SearchPersonPresentImpl;
import com.mobile.factory.present.search.SearchPresent;
import com.mobile.factory.present.user.contact.FollowPresent;
import com.mobile.factory.present.user.contact.FollowPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.UserInfoActivity;
import com.mobile.factory.Application;
import com.mobile.util.app.Fragment;
import com.mobile.factory.model.db.identity.UserIdentity;
import com.mobile.util.widget.EmptyView;
import com.mobile.util.widget.PortraitView;
import com.mobile.util.widget.recycler.Adapter;

import net.qiujuer.genius.ui.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Person search
 */
public class SearchPersonFragment extends Fragment implements SearchFragment, SearchPresent.PersonView {
    private SearchPresent.Presenter presenter;
    private Adapter<UserIdentity> adapter;

    // bind view
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.empty)
    EmptyView emptyView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_person;
    }

    @Override
    protected void initialWidget(View view) {
        super.initialWidget(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new Adapter<UserIdentity>() {
            @Override
            protected com.mobile.util.widget.recycler.ViewHolder<UserIdentity> createHolder(View view, int viewType) {
                return new ViewHolder(view);
            }

            @Override
            protected int getItemViewType(int position, UserIdentity data) {
                // return id for cell
                return R.layout.cell_search;
            }
        });

        // bind view
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    // presenter init，return instance
    protected SearchPresent.Presenter initialPresenter() {
        
        return new SearchPersonPresentImpl(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // initialize presenter
        presenter = initialPresenter();
    }

    @Override
    public void search(String query) {
        // activity -> fragment -> presenter(reuqest)
        presenter.search(query);
    }

    @Override
    public void setPresenter(SearchPresent.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void searchSuccess(List<UserIdentity> userIdentityList) {
        // show cells when succeed
        adapter.updateDataList(userIdentityList);
        emptyView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    @Override
    public void searchFail(int error) {
        emptyView.triggerError(error);
    }

    @Override
    public void loading() {
        emptyView.triggerLoading();
    }

    /**
     * show view of each cell
     */
    static class ViewHolder extends com.mobile.util.widget.recycler.ViewHolder<UserIdentity> implements FollowPresent.View {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.search_portrait)
        PortraitView portraitView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.search_name)
        TextView nameView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.search_follow)
        ImageView followView;

        private UserIdentity my;

        private FollowPresent.Presenter presenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            presenter = initialPresenter();
        }

        @Override
        protected void bindData(UserIdentity data) {
            if(data.getPortrait() != null){
                NetworkHelper.setPortrait(portraitView, data.build(), Application.getAppContext());
            }
            my = data;
            nameView.setText(data.getName());
            followView.setEnabled(!data.getFollow());
        }

        /**
         * raise follow
         */
        @SuppressLint("NonConstantResourceId")
        @OnClick(R.id.search_follow)
        public void onClickFollow(){
            presenter.requestFollow(my.getId());
        }

        @Override
        public void setPresenter(FollowPresent.Presenter presenter) {
            this.presenter = presenter;
        }

        // presenter init，return instance
        protected FollowPresent.Presenter initialPresenter() {
            
            return new FollowPresentImpl(this);
        }

        @Override
        public void followSuccess(UserIdentity userIdentity) {
            followView.setImageResource(R.drawable.search_added_add);
            followView.setEnabled(my.getFollow());
            updateData(userIdentity);
            Toast.makeText(this.followView.getContext(),
                    "Successfully followed " + my.getName() + "!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void followFail(int error) {
            followView.setImageResource(R.drawable.search_added_add);
            Toast.makeText(this.followView.getContext(), error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void loading() {
            // 把图标换成loading
            followView.setImageResource(R.drawable.loading);
        }

        /**
         * get user information when avatar clicked
         */
        @SuppressLint("NonConstantResourceId")
        @OnClick(R.id.search_portrait)
        public void onClickPortrait(){
            UserInfoActivity.show(this.portraitView.getContext(), my.build());
        }
    }

}
