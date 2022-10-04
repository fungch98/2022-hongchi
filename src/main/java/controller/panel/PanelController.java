package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
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
@RequestMapping(value = "/panel")
public class PanelController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/dashboard.html")
    protected String dashboard(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/page/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        String search=request.getParameter("search");
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", "dashboard");
            request.setAttribute("pagePrefix", "panel/");
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                request.setAttribute("prodList", prodDAO.queryProd(search,user, false,  (user!=null && user.getIsAdmin()==1?true:false), 30));
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
