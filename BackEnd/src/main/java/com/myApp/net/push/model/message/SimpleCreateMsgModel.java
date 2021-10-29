package com.myApp.net.push.model.message;

import com.google.gson.annotations.Expose;

public class SimpleCreateMsgModel {
    @Expose
    private String msgId;
    @Expose
    private String receiverId;
    @Expose
    private String message;
    @Expose
    private String type;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
