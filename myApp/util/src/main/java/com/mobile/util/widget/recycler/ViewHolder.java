package com.mobile.util.widget.recycler;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.Unbinder;

/**
 * Self-define holder
 *
 * @param <T> Generics Type
 */
public class ViewHolder<T> extends RecyclerView.ViewHolder {
    protected T data;
    protected Unbinder unbinder;
    protected AdapterCallback<T> callback;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(T data) {
        this.data = data;
        bindData(data);
    }

    /**
     * @param data Generics Type
     */
    protected void bindData(T data) {
        this.data = data;
    }

    /**
     * Trigger a callback to notify the adapter when the data is updated
     * @param data Generics Type
     */
    public void updateData(T data) {
        if (callback != null) {
            callback.updateData(data, this);
        }
    }

}
