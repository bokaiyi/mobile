package com.myApp.net.push.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

// localhost:8080/api/account
@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
    public String get(){
        return "you are logged in";
    }
}
