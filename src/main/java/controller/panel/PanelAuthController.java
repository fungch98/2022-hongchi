/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping(value = "/panel/user")
public class PanelAuthController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/index.html")
    protected String index(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/user/list.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        UserInfo user=null;
        UserDAO userDAO=null;
        try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "panel/user/");
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                if(user!=null && user.getIsAdmin()==1){
                    userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
                    request.setAttribute("userList", userDAO.loadUserList(0));
                }else{
                    return "redirect:/panel/user/"+langCode+"/info/me/edit.html";
                }
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/info/{uuid}/edit.html")
    protected String userEdit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/user/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        UserInfo user=null;
        UserInfo edited=null;
        UserDAO userDAO=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            
            if(request.getSession().getAttribute("ERROR.AUTH.EDIT.INFO")!=null){
                request.setAttribute("SAVE_RESULT_SRC", "INFO");
                result=(ResultBean)request.getSession().getAttribute("ERROR.AUTH.EDIT.INFO");
                request.setAttribute("SAVE_RESULT", result);
                request.getSession().removeAttribute("ERROR.AUTH.EDIT.INFO");
            }
            
            if(request.getSession().getAttribute("ERROR.AUTH.EDIT.PWD")!=null){
                request.setAttribute("SAVE_RESULT_SRC", "PWD");
                result=(ResultBean)request.getSession().getAttribute("ERROR.AUTH.EDIT.PWD");
                request.setAttribute("SAVE_RESULT", result);
                request.getSession().removeAttribute("ERROR.AUTH.EDIT.PWD");
            }
            
            if(request.getSession().getAttribute("ERROR.AUTH.EDIT.ACTION")!=null){
                request.setAttribute("SAVE_RESULT_SRC", "ACTION");
                result=(ResultBean)request.getSession().getAttribute("ERROR.AUTH.EDIT.ACTION");
                request.setAttribute("SAVE_RESULT", result);
                request.getSession().removeAttribute("ERROR.AUTH.EDIT.ACTION");
            }
            
            if(request.getSession().getAttribute("SUCCESS.AUTH.EDIT")!=null){
                request.setAttribute("SUCCESS_AUTH_EDIT", "Y");
                request.getSession().removeAttribute("SUCCESS.AUTH.EDIT");
            }
            
            System.out.println(request.getAttribute("SAVE_RESULT_SRC"));
            
            request.setAttribute("pageLink", "info/"+uuid+"/edit.html");
            request.setAttribute("pagePrefix", "panel/user/");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                if(user!=null && user.getIsAdmin()==1){
                    userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
                    request.setAttribute("editUser", userDAO.loadUser(uuid));
                    request.setAttribute("isAdmin", user);
                }else{
                    request.setAttribute("editUser", user);
                }
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{action}/{uuid}/edit/save.html")
    protected String userEditSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid, 
             @PathVariable String action
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/user/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        UserInfo user=null;
        UserDAO userDAO=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            
            request.setAttribute("pageLink", "info/"+uuid+"/edit.html");
            request.setAttribute("pagePrefix", "panel/user/");
            if(this.frameHandler.isLogin(request)){
                result=new ResultBean();
                
                if(action!=null && (action.equalsIgnoreCase("info") || action.equalsIgnoreCase("password"))){
                    userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
                    
                    user=this.frameHandler.getLoginUser(request);
                    if(action.equalsIgnoreCase("password")){
                        result=userDAO.changePassword(request, user, uuid);
                    }else{
                        result=userDAO.saveInfo(request, user, uuid);
                    }
                    System.out.println("Code: "+result.getCode()+"("+result.getMsg()+")"+user.getIsAdmin());
                    if(result.getCode()==1){
                        if(user!=null && user.getIsAdmin()==1){
                            return "redirect:/panel/user/"+langCode+"/index.html"+"";
                        }else{
                            request.getSession().setAttribute("SUCCESS.AUTH.EDIT", result);
                            return "redirect:/panel/user/"+langCode+"/"+action+"/"+uuid+"/edit.html"+"";
                        }
                    }else{
                        System.out.println("ERROR.AUTH.EDIT."+(action.equalsIgnoreCase("password")?"PWD":"INFO"));
                        request.getSession().setAttribute("ERROR.AUTH.EDIT."+(action.equalsIgnoreCase("password")?"PWD":"INFO"), result);
                        return "redirect:/panel/user/"+langCode+"/info/"+uuid+"/edit.html"+"";
                    }
                    
                }else{
                    //Invalid Action                    
                    result.setCode(-9001);
                    result.setMsg("ERROR.ACCESS");
                    request.getSession().setAttribute("ERROR.AUTH.EDIT.ACTION", result);
                    return "redirect:/panel/user/"+langCode+"/"+action+"/"+uuid+"/edit.html"+"";
                }
                
                
               
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
}
