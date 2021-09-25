package com.mobile.util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.util.R;


public class ImageSelectView extends RecyclerView {
    private com.mobile.util.widget.recycler_view.Adapter<Img> adapter = new ImgAdapter();

    public ImageSelectView(Context context) {
        super(context);
        initialRecycler();
    }

    public ImageSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialRecycler();
    }

    public ImageSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialRecycler();
    }

    public void initialRecycler(){
        // 初始化layout，每行3个图
        setLayoutManager(new GridLayoutManager(getContext(), 3));
        // 设置监听器
        adapter.setAdapterListener(new com.mobile.util.widget.recycler_view.Adapter.AdapterListener<Img>() {
            @Override
            public void onItemClick(com.mobile.util.widget.recycler_view.ViewHolder<Img> holder, Img data) {

            }

            @Override
            public void onItemLongClick(com.mobile.util.widget.recycler_view.ViewHolder<Img> holder, Img data) {

            }
        });
    }

    /**
     * 每一个图都有一样的数据结构
     */
    class Img {

    }

    /**
     * 图片的适配器
     */
    class ImgAdapter extends com.mobile.util.widget.recycler_view.Adapter<Img>{
        @NonNull
        @Override
        public com.mobile.util.widget.recycler_view.ViewHolder<Img> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ImgViewHolder(parent);
        }

        @Override
        public void updateData(Img data, com.mobile.util.widget.recycler_view.ViewHolder<Img> holder) {

        }

        @Override
        public int getItemViewType(int position) {
//            return R.layout.single_img;
            return -1;
        }
    }

    /**
     * 图片的viewHolder
     */
    class ImgViewHolder extends com.mobile.util.widget.recycler_view.ViewHolder<Img>{

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(Img data) {

        }
    }

}
