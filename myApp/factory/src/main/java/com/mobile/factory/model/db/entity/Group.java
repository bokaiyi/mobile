package com.mobile.factory.model.db.entity;

import com.mobile.factory.model.db.Database;
import com.mobile.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * Group model table
 */
@Table(database = Database.class)
public class Group extends BaseModel implements Serializable, DiffUiDataCallback.UiDataDiffer<Group> {
    @PrimaryKey
    private String id; // Group Id
    @Column
    private String name;// Group name
    @Column
    private String desc;// Group description
    @Column
    private String picture;// Group picture
    @Column
    // My message notification level in the group-the object is my currently logged-in account
    private int notifyLevel;
    @Column
    private Date joinAt;// Join time
    @Column
    private Date modifyAt;// Information modification time

    @ForeignKey(tableClass = User.class, stubbedRelationship = true)
    private User owner;// Foreign key

    // Reserved field for interface display
    public Object holder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return notifyLevel == group.notifyLevel
                && Objects.equals(joinAt, group.joinAt)
                && Objects.equals(modifyAt, group.modifyAt)
                && Objects.equals(owner, group.owner)
                && Objects.equals(holder, group.holder)
                && Objects.equals(id, group.id)
                && Objects.equals(name, group.name)
                && Objects.equals(desc, group.desc)
                && Objects.equals(picture, group.picture)
                ;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode():0;
    }

    @Override
    public boolean isSame(Group oldT) {
        // Determine whether the id is a group of information
        return Objects.equals(id, oldT.id);
    }

    @Override
    public boolean isUiContentSame(Group oldT) {
        // If the information displayed on the interface has changed,
        // the only possibility is that it has changed:
        //Group name, description, picture, and interface display corresponding Holder
        return Objects.equals(this.name, oldT.name)
                && Objects.equals(this.desc, oldT.desc)
                && Objects.equals(this.picture, oldT.picture)
                && Objects.equals(this.holder, oldT.holder);
    }
}
