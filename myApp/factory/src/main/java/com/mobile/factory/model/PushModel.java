package com.mobile.factory.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.factory.BaseFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Basic Model of Push Message
 */
@SuppressWarnings("WeakerAccess")
public class PushModel {
    // log out
    public static final int ENTITY_TYPE_LOGOUT = -1;
    // message
    public static final int ENTITY_TYPE_MESSAGE = 200;
    // add new friend
    public static final int ENTITY_TYPE_ADD_FRIEND = 1001;
    // add new group
    public static final int ENTITY_TYPE_ADD_GROUP = 1002;
    // add new group members
    public static final int ENTITY_TYPE_ADD_GROUP_MEMBERS = 1003;
    // modify group members info
    public static final int ENTITY_TYPE_MODIFY_GROUP_MEMBERS = 2001;
    // group members exit
    public static final int ENTITY_TYPE_EXIT_GROUP_MEMBERS = 3001;

    private List<Entity> entities = new ArrayList<>();

    private PushModel(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Transfer Json to array，
     * and encapsulate the array into PushModel to facilitate subsequent data flow processing
     *
     * @param json Json type data
     * @return
     */
    public static PushModel decode(String json) {
        Gson gson = BaseFactory.getGson();
        Type type = new TypeToken<List<Entity>>() {
        }.getType();

        try {
            List<Entity> entities = gson.fromJson(json, type);
            if (entities.size() > 0)
                return new PushModel(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Entity {
        public Entity() {

        }

        // message type
        public int type;
        // message content
        public String content;
        // message created time
        public Date createAt;

        @Override
        public String toString() {
            return "Entity{" +
                    "type=" + type +
                    ", content='" + content + '\'' +
                    ", createAt=" + createAt +
                    '}';
        }
    }

}
