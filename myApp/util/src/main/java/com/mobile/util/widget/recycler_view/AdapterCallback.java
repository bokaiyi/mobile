package com.mobile.util.widget.recycler_view;

public interface AdapterCallback<T> {
    void updateData(T data, ViewHolder<T> holder);
}
