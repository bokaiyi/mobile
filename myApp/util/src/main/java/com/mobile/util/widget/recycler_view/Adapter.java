package com.mobile.util.widget.recycler_view;

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
 * 自定义的adapter
 *
 * @param <T> 泛型数据
 */
public class Adapter<T> extends RecyclerView.Adapter<ViewHolder<T>> implements View.OnClickListener,
        View.OnLongClickListener, AdapterCallback<T> {
    // 数据集
    private List<T> itemList;
    // 监听器
    private AdapterListener<T> adapterListener;

    /**
     * 构造器
     *
     * @param dataList
     * @param adapterListener
     */
    public Adapter(List<T> dataList, AdapterListener<T> adapterListener) {
        this.itemList = dataList;
        setAdapterListener(adapterListener);
    }

    /**
     * 构造器
     *
     * @param dataList
     */
    public Adapter(List<T> dataList) {
        this.itemList = dataList;
    }

    /**
     * 构造器
     *
     * @param adapterListener
     */
    public Adapter(AdapterListener<T> adapterListener) {
        this.itemList = new ArrayList<>();
        this.adapterListener = adapterListener;
    }

    /**
     * 构造器
     */
    public Adapter() {
        this.itemList = new ArrayList<>();
    }


    /**
     * @param parent   父view
     * @param viewType xml中对应id
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 把xml变成一个view
        View rootView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        // 创建自己的viewHolder
        ViewHolder<T> holder = createHolder(rootView, viewType);

        // 事件点击
        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);

        // view和holder进行tag绑定
        rootView.setTag(R.id.recycler_holder, holder);
        holder.unbinder = ButterKnife.bind(holder, rootView);

        // 绑定holder的callback
        holder.callback = this;

        return holder;
    }

    /**
     * 创建一个holder，子类需要复写
     *
     * @param view
     * @param viewType viewType就是xml对应的控件id
     * @return
     */
    protected ViewHolder<T> createHolder(View view, int viewType) {
        return null;
    }

    /**
     * 拿绑定数据并触发holder的绑定
     *
     * @param holder   绑定的holder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        holder.bindData(itemList.get(position));
    }

    /**
     * @return 数据大小
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * @param position 坐标
     * @return xml的id，用来创建holder
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, itemList.get(position));
    }

    /**
     * 获取data，子类需要复写
     *
     * @param position 坐标
     * @param data     数据
     * @return xml的id
     */
    @LayoutRes
    protected int getItemViewType(int position, T data) {
        return -1;
    }

    /**
     * data的更新
     * TODO：这里改了逻辑
     *
     * @param data 泛型数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addData(T data) {
        itemList.add(data);
        notifyItemInserted(getItemCount());
        notifyItemChanged(getItemCount());
    }

    /**
     * 直接更新dataList
     * TODO: 逻辑改了
     *
     * @param dataList
     */
    public void addData(T... dataList) {
        if (itemList == null) {
            this.itemList = Arrays.asList(dataList);
        } else {
            if (dataList.length > 0) {
                int pos = getItemCount();
                Collections.addAll(itemList, dataList);
                notifyItemRangeInserted(pos, dataList.length);
            }
        }
    }

    /**
     * 直接更新dataList
     *
     * @param dataList 本身就是个集合
     */
    public void addData(Collection<T> dataList) {
        if (itemList == null) {
            this.itemList = (List<T>) dataList;
        } else {
            if (dataList.size() > 0) {
                int pos = getItemCount();
                itemList.addAll(dataList);
                notifyItemRangeInserted(pos, dataList.size());
            }
        }
    }

    /**
     * 清除datalist
     */
    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        itemList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换dataList
     *
     * @param dataList
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateDataList(Collection<T> dataList) {
        if (itemList != null) {
            itemList.clear();
            itemList.addAll(dataList);
            notifyDataSetChanged();
        } else {
            itemList = (List<T>) dataList;
        }
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
     * 自定义监听器
     *
     * @param <T> 泛型数据
     */
    public interface AdapterListener<T> {
        void onItemClick(ViewHolder<T> holder, T data);
        void onItemLongClick(ViewHolder<T> holder, T data);
    }

    /**
     * holder更新数据后回调接口，在这里处理view数据更新
     *
     * @param data
     * @param holder
     */
    @Override
    public void updateData(T data, ViewHolder<T> holder) {

    }
}
