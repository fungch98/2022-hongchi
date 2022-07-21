/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.ProdDAO;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import java.util.List;
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
        CategoryDAO catDAO=null;
        List<CategoryInfo> catList=null;
        
         try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "panel/category/");
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                catList=catDAO.loadCategoryList(0);
                request.setAttribute("catList", catList);
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
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit.html")
    protected String edit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/category/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        CategoryInfo category=null;
        List<CategoryInfo> catList=null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "panel/category/");
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                if(user!=null && user.getIsAdmin()==1){
                    catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                    if(request.getSession().getAttribute("ERROR.SAVE.CAT")!=null){
                        result=(ResultBean)request.getSession().getAttribute("ERROR.SAVE.CAT");
                        request.setAttribute("SAVE_RESULT", result);
                        category=(CategoryInfo)result.getObj();
                        request.getSession().removeAttribute("ERROR.SAVE.CAT");
                    }
                    
                    if(category==null){
                        category=catDAO.loadCategory(uuid);
                    }
                    request.setAttribute("category", category);
                }else{
                    request.setAttribute("NO_ACCESS", "Y");
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
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit/save.html")
    protected String editSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/category/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=new ResultBean();
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        CategoryInfo category=null;
        List<CategoryInfo> catList=null;
        UserInfo user=null;
        
         try{ 
            request.setAttribute("pageLink", "index.html");
            request.setAttribute("pagePrefix", "panel/category/");
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                result=catDAO.saveCategory(request, uuid, user);
                if(result!=null && result.getCode()==1){
                    return "redirect:/panel/category/"+langCode+"/index.html";
                }else{
                    request.getSession().setAttribute("ERROR.SAVE.CAT", result);
                    return "redirect:/panel/category/"+langCode+"/"+uuid+"/edit.html";
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
        return "redirect:/panel/category/"+langCode+"/"+uuid+"/edit.html";
    }
}
