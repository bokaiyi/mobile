package com.mobile.util.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.mobile.util.R;
import com.mobile.util.widget.occupy.PlaceHolderView;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Simple placeholder widget，
 * show an empty picture，
 * can show no data loading and other states together with MVP
 */
@SuppressWarnings("unused")
public class EmptyView extends LinearLayout implements PlaceHolderView {
    private ImageView empty_img;
    private TextView show_text;
    private Loading loading;

    private int[] drawableIds = new int[]{0, 0};
    private int[] textIds = new int[]{0, 0, 0};

    private View[] bindViews;

    public EmptyView(Context context) {
        super(context);
        init(null, 0);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.empty_layout, this);
        empty_img = (ImageView) findViewById(R.id.empty_view);
        show_text = (TextView) findViewById(R.id.empty_text);
        loading = (Loading) findViewById(R.id.loading);

        @SuppressLint("Recycle")
        TypedArray arr = getContext().obtainStyledAttributes(
                attrs, R.styleable.EmptyView, defStyle, 0);

        drawableIds[0] = arr.getInt(R.styleable.EmptyView_EmptyDrawable, R.drawable.status_empty);
        drawableIds[1] = arr.getInt(R.styleable.EmptyView_ErrorDrawable, R.drawable.status_empty);
        textIds[0] = arr.getInt(R.styleable.EmptyView_EmptyText, R.string.empty);
        textIds[1] = arr.getInt(R.styleable.EmptyView_ErrorText, R.string.error);
        textIds[2] = arr.getInt(R.styleable.EmptyView_LoadingText, R.string.loading);

        arr.recycle();
    }

    /**
     * Binding a series of data display layout
     * Automatically display the bound data layout
     * when the current layout is hidden (when there is data)
     * When the data is loaded, Loading is automatically displayed and the data layout is hidden
     *
     * @param views Data display layout
     */
    public void bind(View... views) {
        this.bindViews = views;
    }

    /**
     * Change the display status of the binding layout
     *
     * @param visible Displayed state
     */
    private void changeBindViewVisibility(int visible) {
        final View[] views = bindViews;
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(visible);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerEmpty() {
        loading.setVisibility(GONE);
        loading.stop();
        empty_img.setImageResource(drawableIds[0]);
        show_text.setText(textIds[0]);
        empty_img.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerNetError() {
        loading.setVisibility(GONE);
        loading.stop();
        empty_img.setImageResource(drawableIds[1]);
        show_text.setText(textIds[1]);
        empty_img.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerError(@StringRes int strRes) {
        Toast.makeText(getContext(), strRes, Toast.LENGTH_SHORT).show();
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerLoading() {
        empty_img.setVisibility(GONE);
        show_text.setText(textIds[2]);
        setVisibility(VISIBLE);
        loading.setVisibility(VISIBLE);
        loading.start();
        changeBindViewVisibility(GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerOk() {
        setVisibility(GONE);
        changeBindViewVisibility(VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerOkOrEmpty(boolean isOk) {
        if (isOk)
            triggerOk();
        else
            triggerEmpty();
    }

}