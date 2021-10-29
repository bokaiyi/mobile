package com.mobile.util.widget.occupy;


import androidx.annotation.StringRes;

/**
 * The basic placehoder interface
 */
public interface PlaceHolderView {

    /**
     * No data
     * Show empty layout, hide current data layout
     */
    void triggerEmpty();

    /**
     * Internet error
     * Show a network error icon
     */
    void triggerNetError();

    /**
     * Loading error, and show the error info
     *
     * @param strRes Error info
     */
    void triggerError(@StringRes int strRes);

    /**
     * show the loading status
     */
    void triggerLoading();

    /**
     * The data is loaded successfully.
     * When calling this method, the current placeholder layout should be hidden
     */
    void triggerOk();

    /**
     * If isOk is True, the status is successful.
     * hide the layout, or show the empty data layout.
     *
     * @param isOk load the data or not
     */
    void triggerOkOrEmpty(boolean isOk);
}
