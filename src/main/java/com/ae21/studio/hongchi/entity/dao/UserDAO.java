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
import java.util.List;
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
    
    public List<UserInfo> loadUserList(int size) throws Exception{
        List<UserInfo> result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try{
            query=session.getNamedQuery("UserInfo.findAll");
            if(size>0){
                query.setMaxResults(size);
            }
            result=(List<UserInfo>)query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) { }
        }
        return result;
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
                    user.setIsAdmin(0);
                }
                user.setSocialCode(fbCode);
                user.setSocialToken(fbToken);
                user.setLoginDate(common.getLocalTime());
                //gender = (json.getString("gender") != null ? json.getString("gender") : "");
                //user.setGender((gender.length() > 0 ? gender.toUpperCase().charAt(0) : 'M'));
                user.setCoverUrl((json.getString("picture") != null ? json.getString("picture") : ""));
                
                
               
                //user.setIsAWS(0);
                user.setLoginDate(new Date());
                if(user.getUserStatus()==1){
                    //System.out.println("User Id:"+user.getId());
                    //user.setIdRef(lib.getPasswordHash(""+user.getId()));
                    session.saveOrUpdate(user);
                    //System.out.println("UserId: "+user.getId()+":"+user.getFbCoverImage());
                    auth.setLogined(true);
                    auth.setLoginedUser(user);
                    auth.setResultCode(0);
                    tx.commit();
                }else{
                    auth.setResultCode(1);//Can't get Facebook ID
                }
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
    
    public ResultBean changePassword(HttpServletRequest request,UserInfo user, String uuid) throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        UserInfo editUser=null;
        
        String password=request.getParameter("password");
        //String newPwd=request.getParameter("password");
        String confirm=request.getParameter("confrim");
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            password=(password!=null?password.trim():"");
            confirm=(confirm!=null?confirm.trim():"");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("me") && user.getUuid()!=null ){
                    uuid=user.getUuid();
                }
                
                editUser=this.loadUser(uuid);
                
                if(editUser!=null){
                    if(user.getIsAdmin()==1 || user.getUuid().equalsIgnoreCase(uuid)){ //Admin User or Owner
                        if(password.isEmpty()){
                            result.setCode(-2001);
                            result.setMsg("ERROR.USER.PWD.EMPTY");
                        }else{
                            if(password.length()<8){
                                result.setCode(-2002);
                                result.setMsg("ERROR.USER.PWD.LEN");
                            }
                            
                            if(!password.equals(confirm)){
                                result.setCode(-2003);
                                result.setMsg("ERROR.USER.PWD.EQUAL");
                            }
                        }
                    }else{
                        result.setCode(-1003);
                        result.setMsg("ERROR.ACCESS");
                    }
                }else{
                    result.setCode(-1002);
                    result.setMsg("ERROR.NOFOUND");
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
            if(result.getCode()==0){
                
                tx=session.beginTransaction();
                editUser.setPwd(lib.getPasswordHash(password));
                session.saveOrUpdate(editUser);
                tx.commit();
                
                result.setCode(1);
                result.setMsg("label.success");
                result.setObj(editUser);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public ResultBean saveInfo(HttpServletRequest request,UserInfo user, String uuid) throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        UserInfo editUser=null;
        
        String username=request.getParameter("username");
        String display=request.getParameter("display");
        String isAdmin=request.getParameter("isAdmin");
        String userStatus=request.getParameter("userStatus");
        int status=0;
        
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("me") && user!=null){
                   uuid=user.getUuid();
                }
                
                editUser=this.loadUser(uuid);
                
                
                if(editUser!=null){
                    
                   if(user.getIsAdmin()==1){  //Only Admin can change
                       try{
                           editUser.setIsAdmin(Integer.parseInt(isAdmin));
                       }catch(Exception ignore){
                           result.setCode(-2001);
                           result.setMsg("ERROR.USER.ISADMIN.INVALID");
                       }
                       
                       try{
                           status=Integer.parseInt(userStatus);
                           
                           if(status==1){
                               editUser.setUserStatus(status);
                           }else{
                               editUser.setUserStatus(0);
                           }
                           
                       }catch(Exception ignore){
                           ignore.printStackTrace();
                           editUser.setUserStatus(0);
                           result.setCode(-2011);
                           result.setMsg("ERROR.STATUS.INVALID");
                       }
                   }
                   
                   display=(display!=null?display.trim():"");
                   if(display.isEmpty()){
                       result.setCode(-2002);
                       result.setMsg("ERROR.USER.DISPLAY.EMPTY");
                   }else{
                       editUser.setDisplayName(display);
                   }
                   
                   username=(username!=null?username.trim():"");
                   if(username.isEmpty()){
                       result.setCode(-2003);
                       result.setMsg("ERROR.USER.USERNAME.EMPTY");
                   }else{
                       try{
                           sql="Select {u.*} from user_info u where u.uuid!=:uuid and u.username=:user ";
                           squery=session.createSQLQuery(sql);
                           squery.addEntity("u", UserInfo.class);
                           squery.setString("uuid", uuid);
                           squery.setString("user", username);
                           List temp=squery.list();
                           
                           if(temp!=null && temp.size()>0){  //Username exist
                               result.setCode(-2012);
                               result.setMsg("ERROR.USER.USERNAME.UNIQUE");
                           }else{
                               editUser.setUsername(username);
                           }
                           
                       }catch(Exception ignore){
                           result.setCode(-2011);
                           result.setMsg("ERROR.USER.USERNAME.INVALID");
                       }
                   }
                }else{
                    result.setCode(-1002);
                    result.setMsg("ERROR.NOFOUND");
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
            result.setObj(editUser);
            if(result.getCode()==0){
                tx=session.beginTransaction();
               
                session.saveOrUpdate(editUser);
                tx.commit();
                
                result.setCode(1);
                result.setObj(editUser);
                result.setMsg("label.success");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
}

