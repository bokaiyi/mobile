package com.mobile.util.app;

import androidx.appcompat.widget.Toolbar;

import com.mobile.util.R;

/**
 * The basic ToolBarActivity
 */
public class ToolBarActivity extends Activity{
    public Toolbar toolbar;

    @Override
    protected void initialWidget() {
        super.initialWidget();
        initToolBar((Toolbar) findViewById(R.id.toolbar));
    }

    public void initToolBar(Toolbar toolbar){
        this.toolbar = toolbar;
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    // no return method yet

}
