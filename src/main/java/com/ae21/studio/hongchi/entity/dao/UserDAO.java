/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.UserAuthorizedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONObject;

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
    
    public UserAuthorizedBean loginByGoogle(JSONObject json, String fbCode, String fbToken) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Query query = null;
        UserAuthorizedBean auth = new UserAuthorizedBean();
        UserInfo user = null;
        String email = null;
        String googleId = "";
        String gender = "";
        String emailSplit []=null;
        CommonHandler common=new CommonHandler();
        try {
            auth.setLogined(false);
            auth.setLoginedUser(null);
            auth.setResultCode(-1);
            googleId = json.getString("id");
            email=(json.getString("email") != null ? json.getString("email") : "");
            emailSplit=email.split("@");
            
            
           
            tx = session.beginTransaction();
            if (this.checkEmailWhiteList(session, email) && googleId != null && !googleId.isEmpty()) {
                query = session.getNamedQuery("UserInfo.findBySocialId");
                query.setString("id", googleId);
                user = (UserInfo) query.uniqueResult();
                if (user == null) {
                    //email = (request.getParameter("email") != null ? request.getParameter("email") : "");
                    user = new UserInfo();
                    user.setUsername("G" + googleId);
                    user.setEmail((json.getString("email") != null ? json.getString("email") : ""));
                    user.setCreateDate(new Date());
                    user.setDisplayName((json.getString("name") != null ? json.getString("name") : ""));
                    user.setPwd("");
                    user.setUserStatus(1);
                    user.setUuid(common.generateUUID());
                    user.setCreateDate(common.getLocalTime());
                    user.setSocialCode("");
                    user.setSocialId(googleId);
                    user.setSocialToken("");
                    user.setSocialType("G");
                }
                user.setSocialCode(fbCode);
                user.setSocialToken(fbToken);
                user.setLoginDate(common.getLocalTime());
                //gender = (json.getString("gender") != null ? json.getString("gender") : "");
                //user.setGender((gender.length() > 0 ? gender.toUpperCase().charAt(0) : 'M'));
                user.setCoverUrl((json.getString("picture") != null ? json.getString("picture") : ""));
                user.setIsAdmin(0);
                
               
                //user.setIsAWS(0);
                user.setLoginDate(new Date());

                //System.out.println("User Id:"+user.getId());
                //user.setIdRef(lib.getPasswordHash(""+user.getId()));
                session.saveOrUpdate(user);
                //System.out.println("UserId: "+user.getId()+":"+user.getFbCoverImage());
                auth.setLogined(true);
                auth.setLoginedUser(user);
                auth.setResultCode(0);
                tx.commit();
            } else {
                auth.setResultCode(1);//Can't get Facebook ID
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            try {
                session.clear();
            } catch (Exception ignore) {
            }
            try {
                session.close();
            } catch (Exception ignore) {
            }
        }
        return auth;
    }
    
    public boolean checkEmailWhiteList(Session session, String email)throws Exception{
        String emailPart []=null;
        String check="";
        boolean result=false;
        SQLQuery query = null;
        String sql="";
        Object temp=null;
        int size=0;
        try{
            if(email!=null && !email.isEmpty()){
                emailPart=email.trim().split("@");
                if(emailPart!=null && emailPart.length>1){
                    check=emailPart[1];
                    
                    sql="SELECT COUNT(*) FROM para_info WHERE CODE='LOGIN' AND subcode='WHITELIST' AND para_status=1 ";
                    query=session.createSQLQuery(sql);
                    temp=query.uniqueResult();
                    
                    try{
                        if(temp!=null){
                            size=Integer.parseInt(""+temp);
                        }
                    }catch(Exception ignore){
                        ignore.printStackTrace();
                        size=0;
                    }
                    
                    if(size>0){
                        sql="SELECT COUNT(*) FROM para_info WHERE CODE='LOGIN' AND subcode='WHITELIST' AND para_status=1 AND str01 =:check ";
                        query=session.createSQLQuery(sql);
                        query.setString("check", check);
                        temp=query.uniqueResult();
                        if(temp!=null){
                            size=Integer.parseInt(""+temp);
                            if(size>0){
                                return true;
                            }
                        }
                    }else{
                        return true;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
