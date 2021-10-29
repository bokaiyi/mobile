package com.myApp.net.push.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
<<<<<<< HEAD
 * hibernate初始化
 */
public class Hiber {
    // 全局SessionFactory
    private static SessionFactory sessionFactory;

    static {
        // 静态初始化sessionFactory
=======
 * hibernate init 
 */
public class Hiber {
    // global SessionFactory
    private static SessionFactory sessionFactory;

    static {
        // static init sessionFactory
>>>>>>> 16b0d4c (fix bugs-Final version)
        init();
    }

    private static void init() {
<<<<<<< HEAD
        // 从hibernate.cfg.xml文件初始化
=======
        // 从hibernate.cfg.xml file init
>>>>>>> 16b0d4c (fix bugs-Final version)
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
<<<<<<< HEAD
            // build 一个sessionFactory
=======
            // build a sessionFactory
>>>>>>> 16b0d4c (fix bugs-Final version)
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
<<<<<<< HEAD
            // 错误则打印输出，并销毁
=======
            // print if error
>>>>>>> 16b0d4c (fix bugs-Final version)
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
<<<<<<< HEAD
     * 获取全局的SessionFactory
=======
     * get global SessionFactory
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @return SessionFactory
     */
    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }


    /**
<<<<<<< HEAD
     * 关闭sessionFactory
=======
     * close sessionFactory
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
<<<<<<< HEAD
     * 带返回值的Query接口
=======
     * Query interface
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public interface Query<T> {
        T query(Session session);
    }

    /**
<<<<<<< HEAD
     * 请求数据库方法
=======
     * query method
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    public static <T> T query(Query<T> query) {
        Session session = sessionFactory().openSession();

        T t = null;
        Transaction transaction = session.beginTransaction();
        try {
            t = query.query(session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            session.close();
        }
        return t;
    }

}
