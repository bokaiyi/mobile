package com.mobile.myApp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.mobile.myApp.R;
import com.mobile.myApp.fragments.search.SearchFragment;
import com.mobile.myApp.fragments.search.SearchPersonFragment;
import com.mobile.util.app.Activity;
import com.mobile.util.app.Fragment;
import com.mobile.util.app.ToolBarActivity;
import com.raizlabs.android.dbflow.StringUtils;

public class SearchActivity extends ToolBarActivity {

    private SearchFragment searchFragment;
    private int type = 0; // represent the search type, 1 for person, 2 for group

    /**
     * the entrance of activity
     *
     * @param context Context
     */
    public static void show(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("SEARCH_TYPE", type);
        context.startActivity(intent);
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();

        Fragment fragment = null;

        // show fragment
        if (type == 1) {
            SearchPersonFragment searchPersonFragment = new SearchPersonFragment();
            fragment = searchPersonFragment;
            searchFragment = searchPersonFragment;
        } else if (type == 2) {
//            SearchGroupFragment searchGroupFragment = new SearchGroupFragment();
//            fragment = searchGroupFragment;
//            searchFragment = searchGroupFragment;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.lay_container, fragment)
                    .commit();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected boolean checkBundle(Bundle bundle) {
        // search for person or group,1 for person, 2 for group
        type = bundle.getInt("SEARCH_TYPE");
        return type == 1 || type == 2;
    }

    /**
     * initialize themenu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchView != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /**
                 * when text submit
                 * @param query
                 * @return
                 */

                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return true;
                }

                /**
                 * when text changes
                 * @param newText
                 * @return
                 */
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (StringUtils.isNullOrEmpty(newText)) {
                        search("");
                        return true;
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * the method to search
     *
     * @param query
     */
    public void search(String query) {
        Log.e("enter search", "!!!");
        if (searchFragment != null) {
            searchFragment.search(query);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(1);
    }
}
