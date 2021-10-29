package com.mobile.myApp.tools;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.mobile.myApp.R;

import java.util.List;

/**
 * helpers, no need to change
 */
public class SelectDialog extends Dialog implements OnClickListener,OnItemClickListener {
    private SelectDialogListener mListener;
    private Activity mActivity;
    private Button mMBtn_Cancel;
    private TextView mTv_Title;
    private List<String> mName;
    private String mTitle;
    private boolean mUseCustomColor = false;
    private int mFirstItemColor;
    private int mOtherItemColor;

    public interface SelectDialogListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }


    /**
     * cancel listener
     *
     */
    private SelectDialogCancelListener mCancelListener;

    public interface SelectDialogCancelListener {
        public void onCancelClick(View v);
    }

    public SelectDialog(Activity activity, int theme,
                        SelectDialogListener listener, List<String> names) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mName=names;

        setCanceledOnTouchOutside(true);
    }

    /**
     * @param activity calls the pop-up menu
     * @param theme theme of the dialog
     * @param listener listener of the dialog
     * @param cancelListener cancel listener
     * @param names dialog names
     *
     */
    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, SelectDialogCancelListener cancelListener , List<String> names) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName=names;

        // set if cancel on touch outside
        setCanceledOnTouchOutside(false);
    }

    /**
     * @param activity calls the pop up activity
     * @param theme theme of the dialog
     * @param listener listener of the dialog
     * @param names name of the dialog
     * @param title title of the dialog
     *
     */
    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, List<String> names, String title) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mName=names;
        mTitle = title;

        // set if cancel on touch outside
        setCanceledOnTouchOutside(true);
    }

    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, SelectDialogCancelListener cancelListener, List<String> names, String title) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName=names;
        mTitle = title;

        // set if cancel on touch outside
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.view_dialog_select,
                null);
        setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // set windoe animations
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // to make sure the button is full screen on width
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // set the display position
        onWindowAttributesChanged(wl);

        initViews();
    }

    private void initViews() {
        DialogAdapter dialogAdapter=new DialogAdapter(mName);
        ListView dialogList=(ListView) findViewById(R.id.dialog_list);
        dialogList.setOnItemClickListener(this);
        dialogList.setAdapter(dialogAdapter);
        mMBtn_Cancel = (Button) findViewById(R.id.mBtn_Cancel);
        mTv_Title = (TextView) findViewById(R.id.mTv_Title);


        mMBtn_Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mCancelListener != null){
                    mCancelListener.onCancelClick(v);
                }
                dismiss();
            }
        });

        if(!TextUtils.isEmpty(mTitle) && mTv_Title != null){
            mTv_Title.setVisibility(View.VISIBLE);
            mTv_Title.setText(mTitle);
        }else{
            mTv_Title.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        mListener.onItemClick(parent, view, position, id);
        dismiss();
    }
    private class DialogAdapter extends BaseAdapter {
        private List<String> mStrings;
        private Viewholder viewholder;
        private LayoutInflater layoutInflater;
        public DialogAdapter(List<String> strings) {
            this.mStrings = strings;
            this.layoutInflater=mActivity.getLayoutInflater();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mStrings.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mStrings.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                viewholder=new Viewholder();
                convertView=layoutInflater.inflate(R.layout.view_dialog_item, null);
                viewholder.dialogItemButton=(TextView) convertView.findViewById(R.id.dialog_item_bt);
                convertView.setTag(viewholder);
            }else{
                viewholder=(Viewholder) convertView.getTag();
            }
            viewholder.dialogItemButton.setText(mStrings.get(position));
            if (!mUseCustomColor) {
                mFirstItemColor = mActivity.getResources().getColor(R.color.blue_300);
                mOtherItemColor = mActivity.getResources().getColor(R.color.blue_300);
            }
            if (1 == mStrings.size()) {
                viewholder.dialogItemButton.setTextColor(mFirstItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.dialog_item_bg_only);
            } else if (position == 0) {
                viewholder.dialogItemButton.setTextColor(mFirstItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_top);
            } else if (position == mStrings.size() - 1) {
                viewholder.dialogItemButton.setTextColor(mOtherItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_buttom);
            } else {
                viewholder.dialogItemButton.setTextColor(mOtherItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_center);
            }
            return convertView;
        }

    }

    public static class Viewholder {
        public TextView dialogItemButton;
    }

    /**
     * Set the text color of the list item
     */
    public void setItemColor(int firstItemColor, int otherItemColor) {
        mFirstItemColor = firstItemColor;
        mOtherItemColor = otherItemColor;
        mUseCustomColor = true;
    }
}
