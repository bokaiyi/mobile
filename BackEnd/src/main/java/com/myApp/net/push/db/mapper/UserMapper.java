package com.myApp.net.push.db.mapper;

import com.google.common.base.Strings;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.entity.UserFollow;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.utils.Hiber;
import com.myApp.net.push.utils.TextUtil;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
<<<<<<< HEAD
 * User表操作
=======
 * User mapper
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
public class UserMapper {

    /**
<<<<<<< HEAD
     * @param phone 通过手机号查询用户
=======
     * @param phone find user through phone number
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    public static User findUserByPhone(String phone) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where phone=:mphone")
                .setParameter("mphone", phone)
                .uniqueResult());
    }

    /**
<<<<<<< HEAD
     * @param name 通过名字查询用户
=======
     * @param name find user through name
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    public static User findUserByName(String name) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where name=:mname")
                .setParameter("mname", name)
                .uniqueResult());
    }

    /**
<<<<<<< HEAD
     * 通过账户密码查询用户
=======
     * query user through account
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param accountNo
     * @param pwd
     * @return
     */
    public static User findUserByAccountAndPwd(String accountNo, String pwd) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where phone=:phone and password =:password")
                .setParameter("phone", accountNo.trim())
                .setParameter("password", encryptPwd(pwd))
                .uniqueResult());
    }

    /**
<<<<<<< HEAD
     * 通过token查询用户
=======
     * find client through token
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param token
     * @return
     */
    public static User findUserByToken(String token) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }
<<<<<<< HEAD
    /**
     * 获取我的联系人的列表
     *
     * @param self User
     * @return List<User>
     */
    public static List<User> get_contacts(User self) {
        return Hiber.query(session -> {
            // 重新加载一次用户信息到self中，和当前的session绑定
            session.load(self, self.getId());

            // 所有关注的人
            Set<UserFollow> flows = self.getFollowing();

            //返回UserFollow中getTarget的方法，进行循环
            return flows.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());

        });
    }
    // 通过Name找到User
    public static User findById(String id) {
        // 通过Id查询，更方便
        return Hiber.query(session -> session.get(User.class, id));
    }
    /**
     * 关注人的操作
     *
     * @param origin 发起者
     * @param target 被关注的人
     * @param alias  备注名
     * @return 被关注的人的信息
     */
    public static User follow(final User origin, final User target, final String alias) {
        UserFollow follow = getUserFollow(origin, target);
        if (follow != null) {
            // 已关注，直接返回
            return follow.getTarget();
        }

        return Hiber.query(session -> {
            // 想要操作懒加载的数据，需要重新load一次
            session.load(origin, origin.getId());
            session.load(target, target.getId());

            // 我关注人的时候，同时他增加粉丝信息
            // 所有需要添加两条UserFollow数据
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);
            // 备注是我对他的备注，他对我默认没有备注
            originFollow.setAlias(alias);

            // 发起者是他，我是被关注的人的记录
            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            // 保存数据库
            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });
    }


    /**
     * 查询两个人是否已经关注
     *
     * @param origin 发起者
     * @param target 被关注人
     * @return 返回中间类UserFollow
     */
    public static UserFollow getUserFollow(final User origin, final User target) {
        return Hiber.query(session -> (UserFollow) session
                .createQuery("from UserFollow where originId = :originId and targetId = :targetId")
                .setParameter("originId", origin.getId())
                .setParameter("targetId", target.getId())
                .setMaxResults(1)
                // 唯一查询返回
                .uniqueResult());
    }

    /**
     * 搜索联系人的实现
     *
     * @param name 查询的name，允许为空
     * @return 查询到的用户集合，如果name为空，则返回最近的用户
     */
    @SuppressWarnings("unchecked")
    public static List<User> find(String name) {
        if (Strings.isNullOrEmpty(name))
            name = ""; // 保证不能为null的情况，减少后面的一下判断和额外的错误
        final String searchName = "%" + name + "%"; // 模糊匹配

        return Hiber.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return (List<User>) session.createQuery("from User where lower(name) like :name and portrait is not null and description is not null")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 至多20条
                    .list();

        });

    }


    /**
     * 数据库新增用户
     *
     * @param accountNo 电话
     * @param pwd       密码
     * @param name      名字
=======


    /**
     * 通过ID查询用户
     *
     * @param id
     * @return
     */
    public static User findUserByID(String id) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where id=:id")
                .setParameter("id", id)
                .uniqueResult());
    }


    /**
     * new user in to database
     *
     * @param accountNo phone
     * @param pwd       password
     * @param name      name
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    public static User addUser(String accountNo, String pwd, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(encryptPwd(pwd));
        user.setPhone(accountNo.trim());

        User savedUser = Hiber.query(session -> {
            session.save(user);
            return user;
        });

<<<<<<< HEAD
        // 如果保存成功，进行登录
=======
        // login if login success
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (savedUser != null) {
            return login(savedUser);
        }
        return null;
    }

    /**
<<<<<<< HEAD
     * 密码加密
     *
     * @param pwd 用户输入的密码
=======
     * password encrypt
     *
     * @param pwd 
>>>>>>> 16b0d4c (fix bugs-Final version)
     * @return
     */
    public static String encryptPwd(String pwd) {
        return TextUtil.encodeBase64(TextUtil.getMD5(pwd.trim()));
    }

    /**
<<<<<<< HEAD
     * 账户登录(更新用户token)
=======
     * login and update token
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param user
     * @return
     */
    public static User login(User user) {
        String token = TextUtil.encodeBase64(UUID.randomUUID().toString());
        user.setToken(token);
        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
<<<<<<< HEAD
     * 绑定设备
     *
     * @param user   需要绑定的user
     * @param pushId 设备id
     * @return
     */
    public static User bindService(User user, String pushId) {
        // 检查是否有别的账户已经绑定这个设备了，若有则解绑
=======
     * bind service
     *
     * @param user   user that need to bind
     * @param pushId service id
     * @return
     */
    public static User bindService(User user, String pushId) {
        //  check if serviced is already binded
>>>>>>> 16b0d4c (fix bugs-Final version)
        List<User> bindUsers = Hiber.query(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session
                    .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            return userList;
        });
        if (bindUsers != null) {
            for (User bindUser : bindUsers) {
                bindUser.setPushId(null);
                Hiber.query(session -> {
                    session.saveOrUpdate(bindUser);
                    return bindUser;
                });
            }
        }

<<<<<<< HEAD
        // 绑定当前账户
=======
        // bind currrent user
>>>>>>> 16b0d4c (fix bugs-Final version)
        if (!StringUtils.isNullOrEmpty(user.getPushId())) {
            if (!user.getPushId().equals(pushId)) {
                // TODO: 如果当前账户绑定了别的设备，加一条退出登录的推送
            }
        }
        user.setPushId(pushId);
        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
<<<<<<< HEAD
     * 修改用户信息
=======
     * update user info
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @param updateInfoModel
     * @param user
     * @return
     */
    public static User updateInfo(UpdateInfoModel updateInfoModel, User user) {
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getDescription())) {
            user.setDescription(updateInfoModel.getDescription());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPhone())) {
            user.setPhone(updateInfoModel.getPhone());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPassword())) {
<<<<<<< HEAD
            user.setPassword(updateInfoModel.getPassword());
=======
            user.setPassword(encryptPwd(updateInfoModel.getPassword()));
>>>>>>> 16b0d4c (fix bugs-Final version)
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPortrait())) {
            user.setPortrait(updateInfoModel.getPortrait());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getName())) {
            user.setName(updateInfoModel.getName());
        }
        if (updateInfoModel.getSex() != 0) {
            user.setSex(updateInfoModel.getSex());
        }

        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
<<<<<<< HEAD
     * 查询所有联系人
     * @param user
     * @return 返回一个关注人的集合
     */
    public static List<User> findContacts(User user) {
        return Hiber.query(session -> {
            // 重新加载一次
            session.load(user, user.getId());

            Set<UserFollow> user_followings = user.getFollowing();
            
=======
     * find contacts by id
     * @param userId
     * @return
     */
    public static List<User> findContactsByID(String userId) {
        User user = findUserByID(userId);
        if(user != null){
            return findContacts(user);
        }
        return null;
    }

    /**
     * find contacts
     *
     * @param user
     * @return return a list of all contacts
     */
    public static List<User> findContacts(User user) {
        return Hiber.query(session -> {
            // reload
            session.load(user, user.getId());

            Set<UserFollow> user_followings = user.getFollowing();

>>>>>>> 16b0d4c (fix bugs-Final version)
            List<User> followings = new ArrayList<>();
            for (UserFollow user_following : user_followings) {
                followings.add(user_following.getTarget());
            }
            return followings;
        });
    }

<<<<<<< HEAD
=======
    /**
     * follow
     *
     * @param my     sender
     * @param follow followed 
     * @param note   note
     * @return
     */
    public static User followContact(User my, User follow, String note) {
        UserFollow userFollow = isFollow(my, follow);
        if (userFollow != null) {
            return userFollow.getTarget();
        }

        // follow
        return Hiber.query(session -> {
            session.load(my, my.getId());
            session.load(follow, follow.getId());

            // follow each other
            UserFollow follow1 = new UserFollow();
            follow1.setOrigin(my);
            follow1.setTarget(follow);
            follow1.setAlias(note);
            UserFollow follow2 = new UserFollow();
            follow2.setOrigin(follow);
            follow2.setTarget(my);

            session.save(follow1);
            session.save(follow2);

            return follow;
        });
    }

    /**
     * is followed or not 
     *
     * @param my
     * @param follow
     * @return
     */
    public static UserFollow isFollow(User my, User follow) {
        return (UserFollow) Hiber.query(session -> {
            return session.createQuery("from UserFollow where originId = :originId and targetId = :targetId")
                    .setParameter("originId", my.getId())
                    .setParameter("targetId", follow.getId())
                    .uniqueResult();
        });
    }

    /**
     * find user by name
     *
     * @param name
     * @return
     */
    public static List<User> findUsersByName(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return Hiber.query(session -> {
                @SuppressWarnings("unchecked")
                List<User> userList = (List<User>) session.createQuery("from User order by name desc")
                        .setMaxResults(10)
                        .list();
                return userList;
            });
        }

        return Hiber.query(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session.createQuery("from User where name like :name")
                    .setMaxResults(10)
                    .setParameter("name", name + "%")
                    .list();
            return userList;
        });
    }
>>>>>>> 16b0d4c (fix bugs-Final version)

}
