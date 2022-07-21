/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.HashtagDAO;
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
@RequestMapping(value = "/panel/tag")
public class PanelTagController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "add.html")
    protected String tagAdd(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/view.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        HashtagDAO tagDAO=null;
        UserInfo user=null;
         try{ 
           
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "photoView", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return "panel/tag/addTagResp";
    }
}
