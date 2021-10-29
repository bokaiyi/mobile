package com.myApp.net.push.model.message;

import com.google.gson.annotations.Expose;

/**
 * read message model, a string split by ","
 */
public class ReadMsgModel {
    @Expose
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
