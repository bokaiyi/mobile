package com.mobile.factory.utils;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Compare data differences
 * @param <T>
 */
public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback {
    private List<T> mOldList, mNewList;

    public DiffUiDataCallback(List<T> mOldList, List<T> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        // Old data size
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        // New data size
        return mNewList.size();
    }

    // Whether the two classes are the same thing, such as User with the same Id
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isSame(beanOld);
    }

    // After the equal judgment, further judge whether there is data change
    // For example, two different instances of the same user have different name fields
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isUiContentSame(beanOld);
    }

    // Data type to be compared
    // The purpose of the Generics data type is that you are comparing with a data of your type
    public interface UiDataDiffer<T> {
        // Send an old data to you and ask if you are the same data as you marked
        boolean isSame(T old);

        // Compare with the old data, whether the content is the same
        boolean isUiContentSame(T old);
    }
}
