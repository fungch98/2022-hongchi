/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.web;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
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
public class MainController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/index.html")
    protected String index(
            HttpServletRequest request,
            HttpServletResponse response)throws Exception{
        this.frameHandler=new CustFrameHandler(request, "web/page/index.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
         try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "");
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
    
    @RequestMapping(value = "/{langCode}/{pageName}.html")
    protected String langIndex(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
            @PathVariable String pageName )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/"+pageName+".jsp");
          CommonHandler common=new CommonHandler();
          try{
              //System.out.println(langCode+":"+pageName);
              this.frameHandler.initLang(request, pageName, langCode);
              this.frameHandler.invalidURLHandler(response, "/"+langCode+"/"+pageName);
              if(pageName!=null && pageName.equalsIgnoreCase("index")){
                  return this.index(request, response);
              }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "langIndex", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    
    
    @RequestMapping(value = "/error.html")
    protected String error(
            HttpServletRequest request,
            HttpServletResponse response)throws Exception{
         this.frameHandler=new CustFrameHandler(request, "web/page/error.jsp");
         CommonHandler common=new CommonHandler();
        
         try{ 
            
              //this.frameHandler.initLang(request, "",null);
              
             //System.out.println("Lang("+Globals.LOCALE_KEY+"):　"+request.getSession().getAttribute(Globals.LOCALE_KEY));
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
