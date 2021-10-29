package com.myApp.net.push.db.mapper;

import com.myApp.net.push.db.entity.Message;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.utils.Hiber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMapper {
    /**
     * 
     * get message history 
     *
     * @param senderId
     * @param receiverId
     * @return
     */
    public static List<Message> getHistory(String senderId, String receiverId) {
        List<Message> history = Hiber.query(session -> (List<Message>) session
                .createQuery("from Message where (senderId=:senderId and receiverId=:receiverId) " +
                        "or (senderId=:receiverId and receiverId=:senderId) order by createAt asc")
                .setParameter("senderId", senderId)
                .setParameter("receiverId", receiverId)
                .list());

        // set each message as readed
        for (Message message : history) {
            if (message.getReceiverId().equals(senderId)) {
                message.setRead(1);
                Hiber.query(session -> {
                    session.saveOrUpdate(message);
                    return message;
                });
            }
        }
        return history;
    }

    /**
     * set a message to be read
     *
     * @param message
     * @return
     */
    public static Message readMsg(Message message) {
        message.setRead(1);
        return Hiber.query(session -> {
            session.saveOrUpdate(message);
            return message;
        });
    }

    /**
     * query for a message
     *
     * @param id
     * @return
     */
    public static Message getMsgById(String id) {
        return Hiber.query(session -> (Message) session
                .createQuery("from Message where id=:id")
                .setParameter("id", id)
                .uniqueResult());
    }

    /**
     * store a message
     *
     * @param sender
     * @param receiver
     * @param content
     * @param type
     * @return
     */
    public static Message addMegToPerson(String id, User sender, User receiver, String content, int type) {
        Message message = new Message(id, type, sender, receiver, content);
        return loadMsg(message);
    }


    /**
     * reload message
     *
     * @param message
     * @return
     */
    public static Message loadMsg(Message message) {
        return Hiber.query(session -> {
            session.save(message);
            session.flush();
            session.refresh(message);
            return message;
        });
    }

    /**
     * 
     * get all the unread message
     *
     * @return
     */
    public static List<Message> getRecentMsg(String userId) {
        List<Message> messageList = Hiber.query(session -> (List<Message>) session
                .createQuery("from Message where receiverId=:userId and has_read=:has_read order by createAt")
                .setParameter("userId", userId)
                .setParameter("has_read", 0)
                .list());

        List<Message> ans = new ArrayList<>();
        Map<String, Message> map = new HashMap<>();
        for (Message message : messageList) {
            String key = message.getSenderId();
            if (map.containsKey(key)) {
                if (message.getCreateAt().compareTo(map.get(key).getCreateAt()) <= 0) {
                    continue;
                }
            }
            map.put(key, message);
        }

        for (String s : map.keySet()) {
            ans.add(map.get(s));
        }
        return ans;
    }


}
