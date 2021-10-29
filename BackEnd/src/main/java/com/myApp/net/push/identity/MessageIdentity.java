package com.myApp.net.push.identity;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.Message;

import java.time.LocalDateTime;

/**
<<<<<<< HEAD
 * 消息的卡片Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class MessageIdentity {
    @Expose
    private String id; // Id
    @Expose
    private String content;// 内容
    @Expose
    private String attach;// 附件，附属信息
    @Expose
    private int type;// 消息类型
    @Expose
    private LocalDateTime createAt;// 创建时间
    @Expose
    private String groupId;// 如果是群信息，对应群Id
    @Expose
    private String senderId;// 发送者Id，不为空
    @Expose
    private String receiverId;// 接收者Id
=======
 * message identity Model
 *
 * 
 * 
 */
public class MessageIdentity {
    @Expose
    private String id; 
    @Expose
    private String content;
    @Expose
    private String attach;
    @Expose
    private int type;
    @Expose
    private LocalDateTime createAt;// create time
    @Expose
    private String groupId;
    @Expose
    private String senderId;
    @Expose
    private String receiverId;
>>>>>>> 16b0d4c (fix bugs-Final version)

    public MessageIdentity(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.type = message.getType();
        this.attach = message.getAttach();
        this.createAt = message.getCreateAt();
        this.groupId = message.getGroupId();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
    }

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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
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
}
