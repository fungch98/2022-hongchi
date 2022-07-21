/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.system;

import com.ae21.bean.UserAuthorizedBean;
import com.ae21.config.SystemConfig;
import com.ae21.handler.CommonHandler;
import com.ae21.handler.MainFrameHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;

/**
 *
 * @author Alex
 */
public class CustFrameHandler  extends MainFrameHandler{
    public CustFrameHandler() {
        super();
    }
    
    

    public CustFrameHandler(HttpServletRequest request) {
        super(request);
    }

    public CustFrameHandler(HttpServletRequest request, String jsp) {
        super(request, jsp);
    }
    
    public String getPanelReturnPath(HttpServletRequest request) {
        return this.getPanelReturnPath(request, true);
    }

    public String getPanelReturnPath(HttpServletRequest request, boolean checkLogin) {
        //String path = "panel/panelFrame";
        String path = "mainFrame";
       
        try {
            //this.loadTesting(request, 6);
            if(checkLogin){
                UserAuthorizedBean oauth=this.checkLoginStatus(request);
                if(!oauth.isLogined()){
                    //System.out.println("Redirect:Logined");
                    return "redirect:/auth/logout.html";
                }else{
                    //System.out.println("Logined");
                }
            }
            this.initLanguageCode(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Return Path: "+path);
        return path;
    }
    
    public UserAuthorizedBean loadTesting(HttpServletRequest request,int id)throws Exception{
        UserInfo user=null;
        boolean isProd=false;
        
        
            CommonHandler common=new CommonHandler();
            try{
                SystemConfig config=(SystemConfig)common.getDAOObject(request, "systemConfig");
                if(config!=null && config.getKey3()!=null && config.getKey3().equalsIgnoreCase("SIT")){
                    UserDAO userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
                    if(userDAO!=null){
                        user=userDAO.loadUser(id);                        
                        return this.loadTesting(request, user);
                    }
                }
                //System.out.println((user!=null?""+user.getUsername():"NUll"));
            }catch(Exception e){
                e.printStackTrace();
            }
           
        return null;
        
    }


    
    public UserAuthorizedBean loadTesting(HttpServletRequest request, UserInfo user)throws Exception{
        
        UserAuthorizedBean result=null;
        try{
            
            result=new UserAuthorizedBean();
            result.setLogined(true);
            result.setLoginedUser(user);
            
            this.login(request, result);
        }catch(Exception e){
        }
        return result;
    }
    
    public UserInfo getLoginUser(HttpServletRequest request) throws Exception {
         UserAuthorizedBean userAuth = this.checkLoginStatus(request, false);
         return (userAuth!=null? (UserInfo)userAuth.getLoginedUser():null);
    }
    
    public String logout(HttpServletRequest request)throws Exception{
        return this.logout(request, this.getLang(request));
    }
    
    
    public String logout(HttpServletRequest request, String langCode)throws Exception{
        request.getSession().removeAttribute("UserAuthorized");
        request.getSession().removeAttribute("UserAuthorizedLogin");
        return "redirect:/auth/"+langCode+"/logout.html";
    }
    
    @Override
    public HttpServletRequest initLang(HttpServletRequest request,String langCode){
        langCode="zh";
        request.setAttribute("langCode", langCode);
        request.setAttribute("pageLangCode", langCode);
        return request;
    }
    
    @Override
    public void initLanguageCode(HttpServletRequest request) throws Exception {
        request.getSession().setAttribute(
                            Globals.LOCALE_KEY, Locale.TRADITIONAL_CHINESE);
        request.setAttribute("langCode", "zh");
    }
}
