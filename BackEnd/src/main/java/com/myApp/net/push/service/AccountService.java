package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.UserIdentity;
import com.myApp.net.push.model.account.LoginModel;
import com.myApp.net.push.response.AccountResponse;
import com.myApp.net.push.model.account.RegisterModel;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

// http://localhost:8080/api/account
@Path("/account")
public class AccountService {
    @Context
    private SecurityContext securityContext;

    /**
<<<<<<< HEAD
     * 拦截器中会直接返回当时token查好的user
=======
     * 
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    private User getUser(){
        return (User) securityContext.getUserPrincipal();
    }

    /**
<<<<<<< HEAD
     * @param registerModel 请求参数：pwd，name，accountNo, (pushId)
=======
     * @param registerModel get：pwd，name，accountNo, (pushId)
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    @POST
    @Path("/register")
<<<<<<< HEAD
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<AccountResponse> register(RegisterModel registerModel) {
        // 校验入参非空
        if (!RegisterModel.isValid(registerModel)) {
            return Response.buildParameterError();
        }
        // 如果name或者accountno重复，告知用户
=======
    // request and response in JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<AccountResponse> register(RegisterModel registerModel) {
        // not empty
        if (!RegisterModel.isValid(registerModel)) {
            return Response.buildParameterError();
        }
        // if name or account exists，handle 
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (UserMapper.findUserByPhone(registerModel.getAccountNo()) != null) {
            return Response.buildHaveAccountError();
        }
        if (UserMapper.findUserByName(registerModel.getName()) != null) {
            return Response.buildHaveNameError();
        }

<<<<<<< HEAD
        // 注册
        User user = UserMapper.addUser(registerModel.getAccountNo(),
                registerModel.getPwd(), registerModel.getName());
        if (user != null) {
            // 如果携带绑定参数，绑定
=======
        // register
        User user = UserMapper.addUser(registerModel.getAccountNo(),
                registerModel.getPwd(), registerModel.getName());
        if (user != null) {
            // bind user to model
>>>>>>> 16b0d4c (fix bugs-Final version)
            if (!StringUtils.isNullOrEmpty(registerModel.getPushId())) {
                return bind(user, registerModel.getPushId());
            }
            AccountResponse accountResponse = new AccountResponse(user);
            return Response.buildOk(accountResponse);
        }
        return Response.buildRegisterError();
    }

    /**
<<<<<<< HEAD
     * 登陆
     *
     * @param loginModel 请求参数：accountNo, pwd, (pushId)
=======
     * login
     *
     * @param loginModel requestaccountNo, pwd, (pushId)
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    @Path("/login")
    @POST
    public Response<AccountResponse> login(LoginModel loginModel) {
        if (!LoginModel.isValid(loginModel)) {
            return Response.buildParameterError();
        }

        User user = UserMapper.findUserByAccountAndPwd(loginModel.getAccountNo(),
                loginModel.getPwd());
        if (user != null) {
<<<<<<< HEAD
            // 如果携带了pushID，绑定操作
=======
            // if carry pushID, bind reponse
>>>>>>> 16b0d4c (fix bugs-Final version)
            if (!StringUtils.isNullOrEmpty(loginModel.getPushId())) {
                return bind(user, loginModel.getPushId());
            }
            AccountResponse accountResponse = new AccountResponse(user);
            return Response.buildOk(accountResponse);
        }
        return Response.buildLoginError();
    }

    /**
<<<<<<< HEAD
     * 绑定请求
     *
     * @param pushId 设备id  放在url
     * @param token  用户token  放在head
=======
     * 
     *
     * @param pushId 
     * @param token  
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    @Path("/bind/{pushId}")
    @POST
    public Response<AccountResponse> login(@PathParam("pushId") String pushId,
                                           @HeaderParam("token") String token) {
        if (!StringUtils.isNullOrEmpty(pushId)) {
            User user = getUser();
            return bind(user, pushId);
        }
        return Response.buildParameterError();
    }


    /**
<<<<<<< HEAD
     * 绑定设备的操作
     *
     * @param user   需要绑定的user
     * @param pushId 设备id
=======
     * 
     *
     * @param user   
     * @param pushId 
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    public static Response<AccountResponse> bind(User user, String pushId) {
        User bindUser = UserMapper.bindService(user, pushId);
        if (bindUser != null) {
            AccountResponse accountResponse = new AccountResponse(bindUser);
            return Response.buildOk(accountResponse);
        }
        return Response.buildBindServiceError();
    }


}
