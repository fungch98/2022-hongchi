/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.dao.ProdDAO;
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
@RequestMapping(value = "/panel/category")
public class CategoryController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/index.html")
    protected String dashboard(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/category/index.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        String search=request.getParameter("search");
         try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "panel/category/");
          
            this.frameHandler.loadTesting(request, 0);
            System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                
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
