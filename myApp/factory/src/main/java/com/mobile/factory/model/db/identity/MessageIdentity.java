package com.mobile.factory.model.db.identity;


import com.mobile.factory.model.db.entity.Group;
import com.mobile.factory.model.db.entity.Message;
import com.mobile.factory.model.db.entity.User;

import java.util.Date;


/**
 * Message Identity,
 * used to receive the information returned by the server
 *
 */
public class MessageIdentity {
    private String id;
    private String content;
    private String attach;
    private int type;
    private Date createAt;
    private String groupId;
    private String senderId;
    private String receiverId;

    // Two additional local fields
    // transient not serialized and deserialized by Gson
    private transient int status = Message.STATUS_DONE; //Current message status
    //Whether the upload is complete (corresponding to the file)
    private transient boolean uploaded = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }



    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * To build a message, 3 models corresponding to foreign keys must be prepared
     *
     * @param sender   Sender
     * @param receiver Receiver
     * @param group    Receive group
     * @return A message
     */
    public Message build(User sender, User receiver, Group group) {
        Message message = new Message();
        message.setId(id);
        message.setContent(content);
        message.setAttach(attach);
        message.setType(type);
        message.setCreateAt(createAt);
        message.setGroup(group);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setStatus(status);
        return message;
    }
}
