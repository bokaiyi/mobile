package com.mobile.factory.model.db.entity;

import com.mobile.factory.model.db.Database;
import com.mobile.factory.StaticData.AccountData;
import com.mobile.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * Local message table
 */
@Table(database = Database.class)
public class Message implements DiffUiDataCallback.UiDataDiffer<Message>, Serializable {
    // Receiver type
    public static final int RECEIVER_TYPE_NONE = 1;
    public static final int RECEIVER_TYPE_GROUP = 2;

    // message type
    public static final int TYPE_STR = 1;
    public static final int TYPE_PIC = 2;
    public static final int TYPE_FILE = 3;
    public static final int TYPE_AUDIO = 4;

    // Message status
    public static final int STATUS_DONE = 0; // normal
    public static final int STATUS_CREATED = 1; // create status
    public static final int STATUS_FAILED = 2; // fail to send

    @PrimaryKey
    private String id;//primary key
    @Column
    private String content;// content
    @Column
    private String attach;// attachment message
    @Column
    private int type;// message type
    @Column
    private Date createAt;// create time
    @Column
    private int status;// current message

    @ForeignKey(tableClass = Group.class, stubbedRelationship = true)
    private Group group;// Recipient group foreign key

    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User sender;// Sender foreign key

    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User receiver;// Recipient foreign key

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean isSame(Message oldT) {
        // Whether the two classes point to the same message
        return Objects.equals(id, oldT.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return type == message.type
                && status == message.status
                && Objects.equals(createAt, message.createAt)
                && Objects.equals(group, message.group)
                && Objects.equals(sender, message.sender)
                && Objects.equals(receiver, message.receiver)
                && Objects.equals(id, message.id)
                && Objects.equals(content, message.content)
                && Objects.equals(attach, message.attach)
                ;
    }

    /**
     * When the message type is ordinary message (message sent to people)
     * This method is used to return, who is the person chatting with me
     * @return The person I'm chatting with
     */
    User getOther() {
        if (AccountData.getUserId().equals(sender.getId())) {
            return receiver;
        } else {
            return sender;
        }
    }

    @Override
    public boolean isUiContentSame(Message oldT) {
        // Are the fields in the same message different
        //Here, for the message, the message itself cannot be modified; it can only be added and deleted
        //The only thing that will change is that the status of the local (mobile phone) message will change
        return oldT == this || status == oldT.status;
    }

    /**
     * Build a simple message description
     * Used to simplify the message display
     * @return description of message
     */
    String getSampleContent() {
        if (type == TYPE_PIC)
            return "PICTURE";
        else if (type == TYPE_AUDIO)
            return "MUSIC";
        else if (type == TYPE_FILE)
            return "FILE";
        return content;
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode():0;
    }


}
