package com.myApp.net.push.provider;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

/**
<<<<<<< HEAD
 * 验证请求是否登录过了
=======
 * check if login
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class Authentication implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
<<<<<<< HEAD
        // 验证是否是登录和注册
=======
        // check if login or register
>>>>>>> 16b0d4c (fix bugs-Final version)
        String path = ((ContainerRequest) requestContext).getPath(false);
        if (path.startsWith("account/login") || path.startsWith("account/register")) {
            return;
        }

<<<<<<< HEAD
        // 看下token是不是正确
=======
        // check token is correct
>>>>>>> 16b0d4c (fix bugs-Final version)
        String token = requestContext.getHeaders().getFirst("token");
        if (!StringUtils.isNullOrEmpty(token)) {
            User user = UserMapper.findUserByToken(token);
            if (user != null) {
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return user;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                return;
            }
        }

<<<<<<< HEAD
        // 如果被拦截了，直接返回一个错误
=======
        // if blocked, raise error
>>>>>>> 16b0d4c (fix bugs-Final version)
        Response<Object> error = Response.buildAccountError();
        requestContext.abortWith(javax.ws.rs.core.Response.status(200)
                .entity(error)
                .build());
    }
}
