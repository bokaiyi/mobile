package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.Message;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.MessageMapper;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.MessageIdentity;
import com.myApp.net.push.model.message.ReadMsgModel;
import com.myApp.net.push.model.message.SimpleCreateMsgModel;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * handle interface with messages
 */
@Path("/message")
public class MessageService {

    @Context
    private SecurityContext securityContext;

    /**
     * get user
     *
     * @return
     */
    private User getUser() {
        assert securityContext != null;
        return (User) securityContext.getUserPrincipal();
    }

    /**
     * set message as readed，messageId as parameter
     *
     * @param readMsgModel
     * @return
     */
    @POST
    @Path("/read")
    public Response<List<MessageIdentity>> readMsg(ReadMsgModel readMsgModel) {
        String[] ids = readMsgModel.getMsgId().split(",");
        List<MessageIdentity> messageIdentityList = new ArrayList<>();
        for (String id : ids) {
            Message message = MessageMapper.getMsgById(id);
            Message readMsg = MessageMapper.readMsg(message);
            if (readMsg != null) {
                messageIdentityList.add(new MessageIdentity(readMsg));
            }
        }

        if (messageIdentityList.size() > 0) {
            return Response.buildOk(messageIdentityList);
        }
        return Response.buildServiceError();
    }

    /**
     * get history 
     *
     * @param receiverId
     * @return
     */
    @Path("/{receiverId}")
    @GET
    public Response<List<MessageIdentity>> getMsgHistory(@PathParam("receiverId") String receiverId) {
        User me = getUser();

        //  query from database and add 
        List<Message> history = MessageMapper.getHistory(me.getId(), receiverId);
        List<MessageIdentity> messageIdentityList = new ArrayList<>();
        for (Message message : history) {
            messageIdentityList.add(new MessageIdentity(message));
        }

        return Response.buildOk(messageIdentityList);
    }

    /**
     * send a message and store to database
     *
     * @param msg
     * @return
     */
    @Path(("/send"))
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<MessageIdentity> send(SimpleCreateMsgModel msg) {
        if (StringUtils.isNullOrEmpty(msg.getReceiverId())
                || StringUtils.isNullOrEmpty(msg.getMessage())) {
            return Response.buildParameterError();
        }

        User user = getUser();
        // receiver
        User receiver = UserMapper.findUserByID(msg.getReceiverId());
        if (receiver != null) {
            // sender must not be receiver
            if (receiver.getId().equals(user.getId())) {
                return Response.buildParameterError();
            }

            // build a message
            Message megToPerson = MessageMapper.addMegToPerson(msg.getMsgId(), user,
                    receiver, msg.getMessage(), Integer.parseInt(msg.getType()));
            if (megToPerson != null) {
                MessageIdentity messageIdentity = new MessageIdentity(megToPerson);
                return Response.buildOk(messageIdentity);
            }
            return Response.buildServiceError();
        }
        return Response.buildNotFoundUserError("No such receiver!!!");
    }

    /**
     * get all the unread meesage without any params
     *
     * @return
     */
    @GET
    @Path("/unread")
    public Response<List<MessageIdentity>> getUnread() {
        User me = getUser();
        List<Message> recentMsg = MessageMapper.getRecentMsg(me.getId());
        List<MessageIdentity> ans = new ArrayList<>();
        for (Message message : recentMsg) {
            ans.add(new MessageIdentity(message));
        }
        return Response.buildOk(ans);
    }


//    /**
//     * 发送一条消息，存历史到数据库
//     *
//     * @param createMsgModel
//     * @return
//     */
//    @Path(("/send"))
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response<MessageIdentity> pushMsg(CreateMsgModel createMsgModel) {
//        // 验证消息
//        if (!CreateMsgModel.isValid(createMsgModel)) {
//            return Response.buildParameterError();
//        }
//        User user = getUser(); // 获取当前发送者
//
//        // 验证是否重复请求同一条数据
//        Message message = MessageMapper.getMsgById(createMsgModel.getId());
//        if (message != null) {
//            return Response.buildOk(new MessageIdentity(message));
//        }
//
//        // 存储一个消息，分为发给人或者发给群
//        if (createMsgModel.getReceiverType() == Message.RECEIVER_TYPE_GROUP) {
//            // 暂时不做，获取一个群
//
//            return Response.buildOk();
//        } else {
//            // 接收者
//            User receiver = UserMapper.findUserByID(createMsgModel.getReceiverId());
//            if (receiver != null) {
//                // 消息不能发给自己
//                if (receiver.getId().equals(user.getId())) {
//                    return Response.buildParameterError();
//                }
//                // 构建一条消息
//                Message megToPerson = MessageMapper.addMegToPerson(user, receiver, createMsgModel);
//                if (megToPerson != null) {
//                    // 更新map
//                    String[] key = new String[]{user.getId(), receiver.getId()};
//                    List<MessageIdentity> messageIdentityList = map.get(key);
//                    MessageIdentity messageIdentity = new MessageIdentity(megToPerson);
//                    messageIdentityList.add(messageIdentity);
//                    map.put(key, messageIdentityList);
//
//                    return Response.buildOk(messageIdentity);
//
////                    // 做个推送
////                    PushHistory pushHistory = PushMapper.pushMsg(user, receiver, megToPerson);
////                    // 保存历史不能失败
////                    if (pushHistory != null) {
////                        return Response.buildOk(new MessageIdentity(megToPerson));
////                    }
////                    return Response.buildServiceError();
//                }
//                return Response.buildServiceError();
//            }
//            return Response.buildNotFoundUserError("No such receiver!!!");
//        }
//    }

}
