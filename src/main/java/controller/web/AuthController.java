/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.web;

import com.ae21.bean.ResultBean;
import com.ae21.bean.UserAuthorizedBean;
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
public class AuthController {
     private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    
    @RequestMapping(value = "/auth/{langCode}/login.html")
    protected String login(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
            )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/login.jsp");
         CommonHandler common=new CommonHandler();
        ResultBean result=null;
         try{ 
            request.setAttribute("pageLink", "login");
            request.setAttribute("pagePrefix", "auth/");
            this.frameHandler.initLang(request, "login", langCode);
            if(request.getSession().getAttribute("ERROR.LOGIN")!=null){
                result=(ResultBean)request.getSession().getAttribute("ERROR.LOGIN");
                System.out.println(result.getCode());
                request.setAttribute("SAVE_RESULT", result);
                request.getSession().removeAttribute("ERROR.LOGIN");
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
    
    @RequestMapping(value = "/auth/{langCode}/login/submit.html")
    protected String loginSubmit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
            )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/login.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        UserDAO userDAO=null;
        UserAuthorizedBean userAuth = null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", "login");
            request.setAttribute("pagePrefix", "auth/");
            this.frameHandler.initLang(request, "login", langCode);
            userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
            
            result=userDAO.loginByUser(request);
            
            if (result.getCode() == 1) { //login Success
                userAuth=(UserAuthorizedBean)result.getObj();
                this.frameHandler.login(request, userAuth);
                user = (UserInfo) userAuth.getLoginedUser();
                //System.out.println("User: "+user.getDisplayName());
                return "redirect:/panel/"+langCode+"/dashboard.html";
            } else {
                this.frameHandler.logout(request);
                //request.setAttribute("loginMsg", userAuth);
                request.getSession().setAttribute("ERROR.LOGIN", result);
                return "redirect:/auth/"+langCode+"/login.html";
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return "redirect:/auth/"+langCode+"/login.html";
    }
}
