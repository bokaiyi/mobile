package com.myApp.net.push.service;

<<<<<<< HEAD
import com.google.common.base.Strings;
=======
>>>>>>> 16b0d4c (fix bugs-Final version)
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.UserIdentity;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
<<<<<<< HEAD
import java.util.List;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
>>>>>>> f379130 (initial some base classes for message encapsulation)
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
>>>>>>> 16b0d4c (fix bugs-Final version)

// http://localhost:8080/api/user
@Path("/user")
public class UserService {
<<<<<<< HEAD

=======
>>>>>>> 16b0d4c (fix bugs-Final version)
    @Context
    private SecurityContext securityContext;

    /**
<<<<<<< HEAD
     * 拦截器中会直接返回当时token查好的user
     * @return
     */
    private User getUser(){
=======
     * get user
     *
     * @return
     */
    private User getUser() {
        assert securityContext != null;
>>>>>>> 16b0d4c (fix bugs-Final version)
        return (User) securityContext.getUserPrincipal();
    }

    /**
<<<<<<< HEAD
     * @param updateInfoModel 需要修改的参数
     * @param token           用户token
=======
     * @param updateInfoModel 
     * @param token           user token
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    @Path("/updateInfo")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> updateUserInfo(UpdateInfoModel updateInfoModel,
                                                 @HeaderParam("token") String token) {
        if (UpdateInfoModel.isValid(updateInfoModel)) {
            User user = getUser();
            User updated_user = UserMapper.updateInfo(updateInfoModel, user);
<<<<<<< HEAD
            if(updated_user != null){
=======
            if (updated_user != null) {
>>>>>>> 16b0d4c (fix bugs-Final version)
                UserIdentity userIdentity = new UserIdentity(updated_user, true);
                return Response.buildOk(userIdentity);
            }
            return Response.buildUserUpdateError();
        }
        return Response.buildParameterError();
    }

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * 联系人列表
     */
    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> get_contact() {
        // 拿到自己
        User user = getUser();
        // 拿到我的联系人列表
        List<User> users = UserMapper.get_contacts(user);
        // 转换
        List<UserIdentity> userIdentity = users.stream()
                .map(g_user -> new UserIdentity(g_user, true))
                .collect(Collectors.toList());
        return Response.buildOk(userIdentity);
    }

    // 关注人，双方同时关注
    @PUT
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> follow(@PathParam("followId") String followId) {
        User user = getUser();

        // 不能关注我自己
        if (user.getId().equalsIgnoreCase(followId)) {
            return Response.buildParameterError();
        }
        // 不能为空
        if (Strings.isNullOrEmpty(followId)){
            return Response.buildParameterError();
        }

        // 我关注的人
        User followUser = UserMapper.findById(followId);
        if (followUser == null) {
            // 未找到人
            return Response.buildNotFoundUserError(null);
        }

        // 备注默认没有，后面可以扩展
        followUser = UserMapper.follow(user, followUser, null);
        // 关注失败的情况
        if (followUser == null) {
            return Response.buildServiceError();
        }

        // TODO 通知我关注的人我关注他

        // 返回关注的人的信息
        UserIdentity userIdentity = new UserIdentity(followUser, true);
        return Response.buildOk(userIdentity);
    }


    // 获取某人的信息
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> getUser(String id) {
        // 返回异常
        if (Strings.isNullOrEmpty(id)) {
            return Response.buildParameterError();
        }
        // 拿到我自己
        User user = getUser();
        // 如果是自己，不必查询数据库
        if (user.getId().equalsIgnoreCase(id)) {
            return Response.buildOk(new UserIdentity(user, true));
        }
        // 通过id寻找
        User g_user = UserMapper.findById(id);
        //没找到用户，返回异常
        if (g_user == null) {
            return Response.buildNotFoundUserError(null);
        }

        // 如果我们直接有关注的记录，则我已关注需要查询信息的用户
        boolean isFollow = UserMapper.getUserFollow(user, g_user) != null;
        return Response.buildOk(new UserIdentity(g_user, isFollow));
    }


    @GET // 搜索人
    @Path("/search/{name:(.*)?}") // 名字为任意字符，可以为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> find(String name) {
        User user = getUser();

        // 找到数据
        List<User> searchUsers = UserMapper.find(name);
        // 得到联系人
        final List<User> contacts = UserMapper.get_contacts(user);
        // 转化
        List<UserIdentity> userCards = searchUsers.stream().map(s_user -> {
                    // 判断这个人是否是我自己，或者是我的联系人中的人，进行联系人的任意匹配，匹配其中的Id字段
                    boolean isFollow = s_user.getId().equalsIgnoreCase(user.getId())
                            || contacts.stream().anyMatch(contactUser -> contactUser.getId().equalsIgnoreCase(s_user.getId()));
                    return new UserIdentity(s_user, isFollow);
        }).collect(Collectors.toList());
        return Response.buildOk(userCards);
    }
=======
//    @Path("/contacts")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response<List<UserIdentity>> getContacts(){
//
//    }

>>>>>>> f379130 (initial some base classes for message encapsulation)
}

=======
    /**
     * get a user info
     *
     * @return
     */
    @Path("/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> getUser(@PathParam("id") String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            return Response.buildParameterError();
        }

        User my = getUser();
        if (Objects.equals(my.getId(), id)) {
            return Response.buildOk(new UserIdentity(my, true));
        }
        User find = UserMapper.findUserByID(id);
        if (find == null) {
            return Response.buildNotFoundUserError(null);
        }
        if (UserMapper.isFollow(my, find) != null) {
            return Response.buildOk(new UserIdentity(find, true));
        }
        return Response.buildOk(new UserIdentity(find, false));
    }

    /**
     * get contact using id
     * @return
     */
    @Path("/contacts/{userId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> getContactsByID(@PathParam("userId") String userId) {
        if(!StringUtils.isNullOrEmpty(userId)){
            List<User> contacts = UserMapper.findContactsByID(userId);
            if(contacts == null){
                return Response.buildOk(new ArrayList<>());
            }
            List<UserIdentity> contactIdentityList = new ArrayList<>();
            for (User contact : contacts) {
                contactIdentityList.add(new UserIdentity(contact, true));
            }
            return Response.buildOk(contactIdentityList);
        }
        return Response.buildParameterError();
    }

    /**
     * get all contact 
     *
     * @return
     */
    @Path("/contacts")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> getContacts() {
        List<User> contacts = UserMapper.findContacts(getUser());
        List<UserIdentity> contactIdentityList = new ArrayList<>();
        for (User contact : contacts) {
            contactIdentityList.add(new UserIdentity(contact, true));
        }
        return Response.buildOk(contactIdentityList);
    }

    /**
     * find name
     *
     * @return
     */
    @Path("/find/{name:(.*)?}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> findUser(@DefaultValue("") @PathParam("name") String name) {
        User my = getUser();
        // 如果名字为空，返回数据库前10个user; 否则进行匹配，并显示是否已经关注
        List<User> userList = UserMapper.findUsersByName(name);
        if (userList == null) {
            return Response.buildNotFoundUserError("No matched users found!!");
        }

        List<UserIdentity> userIdentityList = new ArrayList<>();
        for (User follow : userList) {
            if (UserMapper.isFollow(my, follow) != null) {
                userIdentityList.add(new UserIdentity(follow, true));
            } else {
                userIdentityList.add(new UserIdentity(follow, false));
            }
        }
        return Response.buildOk(userIdentityList);
    }

    /**
     * follow
     *
     * @return
     */
    @Path("/follow/{followId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> follow(@PathParam("followId") String followId) {
        User user = getUser();
        if (Objects.equals(followId, user.getId())) {
            return Response.buildParameterError();
        }

        User following = UserMapper.findUserByID(followId);
        if (following != null) {
            following = UserMapper.followContact(user, following, null);
            if (following == null) {
                return Response.buildServiceError();
            }
            // TODO: notificate the followed
            return Response.buildOk(new UserIdentity(following, true));
        }
        return Response.buildNotFoundUserError("This user doesn't exist");
    }

}
>>>>>>> 16b0d4c (fix bugs-Final version)
