/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
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
    
    @RequestMapping(value = "/{langCode}/list.html")
    protected String tagList(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/tag/list.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        HashtagDAO tagDAO=null;
        UserInfo user=null;
         try{ 
           request.setAttribute("pageLink", "list");
            request.setAttribute("pagePrefix", "panel/tag");
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                request.setAttribute("tagList", tagDAO.loadTagList(0));
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "tagList", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getPanelReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit.html")
    protected String tagEdit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/tag/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        HashtagDAO tagDAO=null;
        UserInfo user=null;
        HashtagInfo tag=null;
         try{ 
           request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/tag");
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                //System.out.println("Tag: "+uuid);
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                
                if(request.getSession().getAttribute("ERROR.TAG.EDIT.SAVE")!=null){
                    result=(ResultBean)request.getSession().getAttribute("ERROR.TAG.EDIT.SAVE");
                    tag=(HashtagInfo)result.getObj();
                    request.setAttribute("SAVE_RESULT", result);
                    request.getSession().removeAttribute("ERROR.TAG.EDIT.SAVE");
                }
                
                if(tag==null){
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        tag=new HashtagInfo();
                        tag.setUuid(uuid);
                        tag.setName("");
                        tag.setCreateDate(common.getLocalTime());
                        tag.setCreateUser(user);
                        tag.setModifyDate(common.getLocalTime());
                        tag.setModifyUser(user);
                    }else{
                        tag=tagDAO.loadTag(uuid);
                    }
                }
                
                
                if(tag!=null){
                    request.setAttribute("tag", tag);
                    request.setAttribute("prodList", tagDAO.getTagProduct(tag));
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "tagList", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getPanelReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit/{action}.html")
    protected String tagEditSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid, 
             @PathVariable String action
             
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/tag/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        HashtagDAO tagDAO=null;
        UserInfo user=null;
        HashtagInfo tag=null;
         try{ 
           request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/tag");
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                
                //System.out.println("Tag: "+uuid+":"+user.getDisplayName());
                if(action!=null && action.equalsIgnoreCase("delete")){
                    result=tagDAO.delTag(request, uuid, user);
                }else if(action!=null && action.equalsIgnoreCase("save")){
                    result=tagDAO.saveTag(request, uuid, user);
                }else{
                    result=new ResultBean();
                    result.setCode(-9001);
                    result.setMsg("ERROR.INVALID.ACTION");
                }
                
                
                if(result!=null && result.getCode()==1){
                    return "redirect:/panel/tag/"+langCode+"/list.html";
                }else{
                    if(result!=null || result.getObj()!=null){
                        tag=(HashtagInfo)result.getObj();
                    }
                    request.getSession().setAttribute("ERROR.TAG.EDIT.SAVE", result);
                    return "redirect:/panel/tag/"+langCode+"/"+(tag!=null && tag.getUuid()!=null?tag.getUuid():uuid)+"/edit.html";
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "tagList", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return "redirect:/panel/tag/"+langCode+"/"+uuid+"/edit.html";
    }
    
    
}
