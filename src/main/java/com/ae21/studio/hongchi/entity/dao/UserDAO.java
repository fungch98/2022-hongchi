/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.UserAuthorizedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Alex
 */
public class UserDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public UserInfo loadUser(int id) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = null;
        UserInfo user = null;
        try {
            query = session.getNamedQuery("UserInfo.findById");
            query.setInteger("id", id);
            user = (UserInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
        }
        return user;
    }

    public UserInfo loadUser(String uuid) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = null;
        UserInfo user = null;
        try {
            query = session.getNamedQuery("UserInfo.findByUuid");
            query.setString("uuid", uuid);
            user = (UserInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
        }
        return user;
    }
    
    public UserAuthorizedBean refresh(UserAuthorizedBean userAuth) throws Exception {
        if (userAuth.isLogined() && userAuth.getLoginedUser() != null) {
            UserInfo user = (UserInfo) userAuth.getLoginedUser();
            user = this.loadUser(user.getId());
            //System.out.println("UserId: "+user.getIsTrial());
            userAuth.setLoginedUser(user);
        }
        return userAuth;
    }
    
    public ResultBean loginByUser(HttpServletRequest request) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Query query = null;
        ResultBean result=new ResultBean();
        UserAuthorizedBean auth = new UserAuthorizedBean();
        CommonHandler lib = new CommonHandler();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserInfo user = null;

        try {
            result.setCode(0);
            result.setMsg("label.success");
            auth.setResultCode(0);
            auth.setLogined(false);
            //System.out.println("Login: "+username+":"+password);
            if (username != null && !username.isEmpty()
                    && password != null && !password.isEmpty()) {
                query = session.getNamedQuery("UserInfo.findByUsername");
                password = lib.getPasswordHash(password);
                query.setString("username", username);
                query.setString("password", password);
                user = (UserInfo) query.uniqueResult();

                if (user != null) {

                    tx = session.beginTransaction();
                    user.setLoginDate(new Date());
                    session.saveOrUpdate(user);
                    tx.commit();

                    auth.setLogined(true);
                    auth.setLoginedUser(user);
                    auth.setResultCode(1);
                    result.setCode(1);
                    result.setObj(auth);
                } else {
                    auth.setResultCode(-1002);
                    auth.setMsg("ERROR.LOGIN.INVALID");
                    result.setCode(-1002);
                    result.setMsg("ERROR.LOGIN.INVALID");
                }
            } else {
                auth.setResultCode(-1001);
                auth.setMsg("ERROR.LOGIN.INFO");
                result.setCode(-1001);
                    result.setMsg("ERROR.LOGIN.INFO");
            }

        } catch (Exception e) {
            auth.setResultCode(-99);
            auth.setMsg("ERROR.SYSTEM");
            result.setCode(-9999);
                    result.setMsg("ERROR.SYSTEM");
            e.printStackTrace();
            try {
                if (tx != null) {tx.rollback();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            try {session.clear();} catch (Exception ignore) {}
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
}
