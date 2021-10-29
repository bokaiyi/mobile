package com.mobile.factory.model.db.entity;

import com.mobile.factory.model.db.Database;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.Objects;


/**
 * Groupmember Model table
 */
@Table(database = Database.class)
public class GroupMember extends BaseModel {
    // Level of the notification
    public static final int NOTIFY_LEVEL_INVALID = -1; // close notify
    public static final int NOTIFY_LEVEL_NONE = 0; // normal

    @PrimaryKey
    private String id; // primary key
    @Column
    private String alias;// Alias name
    @Column
    private boolean isAdmin;// Is it an administrator
    @Column
    private boolean isOwner;// Is the group creator
    @Column
    private Date modifyAt;// Update time

    @ForeignKey(tableClass = Group.class, stubbedRelationship = true)
    private Group group;// Corresponding group foreign key

    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User user;// Corresponding user foreign key

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMember that = (GroupMember) o;

        return isAdmin == that.isAdmin
                && isOwner == that.isOwner
                && Objects.equals(modifyAt, that.modifyAt)
                && Objects.equals(group, that.group)
                && Objects.equals(user, that.user)
                && Objects.equals(id, that.id)
                && Objects.equals(alias, that.alias)
                ;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode():0;
    }
}
