/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.HashtagDAO;
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
@RequestMapping(value = "/panel/photo")
public class PanelPhotoController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/{uuid}/view.html")
    protected String photoView(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/view.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        ProductInfo prod=null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                prod=prodDAO.loadProd(uuid);
                
                if(prod!=null && (prod.getIsShare()==1 || prodDAO.checkAllowEdit(prod, user)) ){
                    request.setAttribute("photo", prod);
                    request.setAttribute("catList", prodDAO.getProdCat(prod));
                    request.setAttribute("hashList", prodDAO.getProdHashtag(prod));
                }
                
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
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit.html")
    protected String photoEdit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        ProductInfo prod=null;
        UserInfo user=null;
        CategoryInfo parent=null;
        String folder=request.getParameter("folder");
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                if(request.getSession().getAttribute("ERROR.PHOTO.EDIT.INFO")!=null){
                    result=(ResultBean)request.getSession().getAttribute("ERROR.PHOTO.EDIT.INFO");
                    prod=(ProductInfo)result.getObj();
                    request.setAttribute("SAVE_RESULT", result);
                    request.getSession().removeAttribute("ERROR.PHOTO.EDIT.INFO");
                }
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                if(prod==null){
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        prod=new ProductInfo();
                        prod.setCreateDate(common.getLocalTime());
                        prod.setCreateUser(user);
                        prod.setModifyDate(common.getLocalTime());
                        prod.setModifyUser(user);
                        prod.setStatus(1);
                        prod.setUuid(uuid);
                        prod.setProductCreateMethod(1);
                    }else{
                        prod=prodDAO.loadProd(uuid);
                    }
                }
                
                if(prod!=null && prodDAO.checkAllowEdit(prod, user)){
                    request.setAttribute("photo", prod);
                    if(folder!=null && !folder.isEmpty()){
                        parent=catDAO.loadCatURL(folder);
                        if(parent!=null){
                            request.setAttribute("folder", parent);
                        }
                    }
                    request.setAttribute("catList", prodDAO.loadSelectedCat(catDAO.loadCategoryList(0), prod, parent));
                    
                    
                }
                
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "PhotoEdit", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit/save.html")
    protected String photoEditSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        
        ProductInfo prod=null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                
                result=prodDAO.saveProd(request, uuid, user);
                if(result!=null && result.getCode()==1){
                    prod=(ProductInfo)result.getObj();
                    return "redirect:/panel/photo/"+langCode+"/"+prod.getUuid()+"/view.html";
                }else{
                    request.getSession().setAttribute("ERROR.PHOTO.EDIT.INFO", result);
                    return "redirect:/panel/photo/"+langCode+"/"+uuid+"/edit.html";
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "photoEditSave", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/edit/delete.html")
    protected String photoEditDel(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/edit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        
        ProductInfo prod=null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                
                result=prodDAO.delProd(request, uuid, user);
                if(result!=null && result.getCode()==1){
                    //prod=(ProductInfo)result.getObj();
                    return "redirect:/panel/"+langCode+"/dashboard.html";
                }else{
                    request.getSession().setAttribute("ERROR.PHOTO.EDIT.INFO", result);
                    return "redirect:/panel/photo/"+langCode+"/"+uuid+"/edit.html";
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "photoEditSave", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/hashtag.html")
    protected String photoHashtag(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/tag.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        HashtagDAO tagDAO=null;
        ProductInfo prod=null;
        UserInfo user=null;
        List<HashtagInfo> tagList=null;
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                if(request.getSession().getAttribute("ERROR.PHOTO.EDIT.TAG")!=null){
                    result=(ResultBean)request.getSession().getAttribute("ERROR.PHOTO.EDIT.TAG");
                    tagList=(List<HashtagInfo>)result.getObj();
                    request.setAttribute("SAVE_RESULT", result);
                    request.getSession().removeAttribute("ERROR.PHOTO.EDIT.TAG");
                }
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                if(prod==null){
                    prod=prodDAO.loadProd(uuid);
                }
                
                if(prod!=null && prodDAO.checkAllowEdit(prod, user)){
                    request.setAttribute("photo", prod);
                    request.setAttribute("tagList", tagDAO.loadTagList(0));
                    if(tagList!=null){
                        request.setAttribute("hashtagList", tagList);
                    }else{
                        request.setAttribute("hashtagList", prodDAO.loadProdHashtag(prod));
                    }
                    
                }
                
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "photoHashtag", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/hashtag/save.html")
    protected String photoHashtagSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/photo/tag.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        HashtagDAO tagDAO=null;
        ProductInfo prod=null;
        UserInfo user=null;
         try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/photo");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                result=prodDAO.saveHashtag(request, uuid, user);
                if(result!=null && result.getCode()==1){
                     return "redirect:/panel/photo/"+langCode+"/"+uuid+"/view.html";
                }else{
                    request.getSession().setAttribute("ERROR.PHOTO.EDIT.TAG", result);
                    return "redirect:/panel/photo/"+langCode+"/"+uuid+"/hashtag.html";
                }
                
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "photoHashtag", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
}
