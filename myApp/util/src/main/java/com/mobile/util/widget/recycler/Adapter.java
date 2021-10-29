package com.mobile.util.widget.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.util.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * self-defined adapter
 *
 * @param <T> generic data
 */
public abstract class Adapter<T> extends RecyclerView.Adapter<ViewHolder<T>> implements View.OnClickListener,
        View.OnLongClickListener, AdapterCallback<T> {
    // dataset
    private List<T> itemList;

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    // listener
    private AdapterListener<T> adapterListener;

    /**
     * Constructor
     *
     * @param dataList
     * @param adapterListener
     */
    public Adapter(List<T> dataList, AdapterListener<T> adapterListener) {
        this.itemList = dataList;
        setAdapterListener(adapterListener);
    }

    /**
     * Constructor
     *
     * @param dataList
     */
    public Adapter(List<T> dataList) {
        this.itemList = dataList;
    }

    /**
     * Constructor
     *
     * @param adapterListener
     */
    public Adapter(AdapterListener<T> adapterListener) {
        this.itemList = new ArrayList<>();
        this.adapterListener = adapterListener;
    }

    /**
     * return the whole list
     *
     * @return List<Data>
     */
    public List<T> getItems() {
        return itemList;
    }

    /**
     * replaced by a new list, including clear the list
     *
     * @param dataList a new list
     */
    public void replace(Collection<T> dataList) {
        itemList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        itemList.addAll(dataList);
        notifyDataSetChanged();
    }


    /**
     * Constructor
     */
    public Adapter() {
        this.itemList = new ArrayList<>();
    }


    /**
     * @param parent   parent view
     * @param viewType correesponding id in xml
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // turn xml into a view
        View rootView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        // create own viewHolder
        ViewHolder<T> holder = createHolder(rootView, viewType);

        // on click
        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);

        // bind view and holder by tag
        rootView.setTag(R.id.recycler_holder, holder);
        holder.unbinder = ButterKnife.bind(holder, rootView);

        // bind holder's callback
        holder.callback = this;

        return holder;
    }

    /**
     * create a holder，interited classes need to override
     *
     * @param view
     * @param viewType viewType the corresponding id of xml
     * @return
     */
    protected abstract ViewHolder<T> createHolder(View view, int viewType);

    /**
     * Take the binding data and trigger the binding of holder
     *
     * @param holder   the binding holder
     * @param position position of the item in itemList
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        holder.bindData(itemList.get(position));
    }

    /**
     * @return size of the itemList
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * @param position position
     * @return xml's id，to create holder
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, itemList.get(position));
    }

    /**
     * get data，inherited classes need to override
     *
     * @param position position
     * @param data     data
     * @return xml's id
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, T data);

    /**
     * data update
     *
     *
     * @param data generic data
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addData(T data) {
        itemList.add(data);
        notifyItemInserted(itemList.size() - 1);
    }

    /**
     * update dataList directly
     *
     * @param dataList
     */
    public void addData(T... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = itemList.size();
            Collections.addAll(itemList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * updata dataList directly
     *
     * @param dataList a collection itself
     */
    public void addData(Collection<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = itemList.size();
            itemList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * clear datalist
     */
    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        itemList.clear();
        notifyDataSetChanged();
    }

    /**
     * replace dataList
     *
     * @param dataList
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateDataList(Collection<T> dataList) {
        itemList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        itemList.addAll(dataList);
        notifyDataSetChanged();
    }


    public void setAdapterListener(AdapterListener<T> adapterListener) {
        this.adapterListener = adapterListener;
    }


    @Override
    public void onClick(View v) {
        ViewHolder<T> viewHolder = (ViewHolder<T>) v.getTag(R.id.recycler_holder);
        if (adapterListener != null) {
            adapterListener.onItemClick(viewHolder, viewHolder.data);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder<T> viewHolder = (ViewHolder<T>) v.getTag(R.id.recycler_holder);
        if (adapterListener != null) {
            adapterListener.onItemLongClick(viewHolder, viewHolder.data);
            return true;
        }
        return false;
    }

    /**
     * self-define listenter
     *
     * @param <T> generic data
     */
    public interface AdapterListener<T> {
        void onItemClick(ViewHolder<T> holder, T data);
        void onItemLongClick(ViewHolder<T> holder, T data);
    }

    /**
     * After the holder updates the data,
     * the interface is called back,
     * and the view data update is processed here
     *
     * @param data
     * @param holder
     */
    @Override
    public void updateData(T data, ViewHolder<T> holder) {
        // get the position of current view
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // remove and update data
            itemList.remove(pos);
            itemList.add(pos, data);
            // notify the data has changed under the position
            notifyItemChanged(pos);
        }
    }
}
