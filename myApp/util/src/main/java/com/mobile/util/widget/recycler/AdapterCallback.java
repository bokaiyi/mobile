package com.mobile.util.widget.recycler;

public interface AdapterCallback<T> {
    void updateData(T data, ViewHolder<T> holder);
}
