package com.mobile.util.widget.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import butterknife.Unbinder;

/**
 * 自定义的holder
 *
 * @param <T> 数据是泛型
 */
public class ViewHolder<T> extends RecyclerView.ViewHolder {
    protected T data;
    protected Unbinder unbinder;
    protected AdapterCallback<T> callback;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    private void setData(T data) {
        this.data = data;
        bindData(data);
    }

    /**
     * @param data 泛型数据
     */
    protected void bindData(T data) {

    }

    /**
     * 更新数据时触发回调通知adapter
     * @param data 泛型数据
     */
    public void updateData(T data) {
        if (callback != null) {
            callback.updateData(data, this);
        }
    }

}
