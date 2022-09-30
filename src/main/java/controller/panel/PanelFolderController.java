/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.ProdDAO;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import com.ae21.studio.hongchi.entity.system.FamilyBean;
import com.ae21.studio.hongchi.entity.system.SearchBean;
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
@RequestMapping(value = "/panel/folder")
public class PanelFolderController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/{uuid}/view.html")
    protected String folderView(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid
    )throws Exception{
        this.frameHandler=new CustFrameHandler(request, "panel/search/folderResult.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        CategoryDAO catDAO=null;
        UserInfo user=null;
        FamilyBean family=null;
        SearchBean search=null;
        CategoryInfo current=null;
        List<ProductInfo> prodList=null;
        try{ 
            request.setAttribute("pageLink", uuid+"/edit");
            request.setAttribute("pagePrefix", "panel/folder");
            request.setAttribute("uuid", uuid);
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                if(uuid!=null && uuid.equalsIgnoreCase("root")){
                    current=new CategoryInfo();
                    current.setFamilyId(0);
                    current.setParentId(0);
                    current.setId(0);
                }else{
                    current=catDAO.loadCatURL(uuid);
                }
                
                if(current!=null){
                    family=catDAO.initFamilyTree(current);
                    search=catDAO.searchProduct(family, 0, 24, user);
                    if(search!=null){
                        request.getSession().setAttribute("SEARCH_FOLDER_RESULT", search);
                        prodList=(search.getPageList()!=null && search.getPageList().size()>0?search.getPageList().get(0):null);
                        request.setAttribute("prodList", prodList);
                        request.setAttribute("family", search.getFamily());
                    }
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
        // return "redirect:/panel/folder/"+langCode+"/search.html";
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/search.html")
    protected String search(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/folderResult.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        //String search=request.getParameter("search");
        String key=request.getParameter("key");
        
        SearchBean search=null;
        int curPage=0;
        List<ProductInfo> prodList=null;
         try{ 
            request.setAttribute("pageLink", "search");
            request.setAttribute("pagePrefix", "panel/folder");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                
                if(request.getSession().getAttribute("SEARCH_FOLDER_RESULT")!=null){
                    search=(SearchBean)request.getSession().getAttribute("SEARCH_FOLDER_RESULT");
                }else{
                    return "redirect:/panel/folder/"+langCode+"/root/view.html";
                }
                
                if(search!=null){
                    request.setAttribute("key", search.getKey());
                    curPage=search.getCurPage();
                    
                    prodList=(search.getPageList()!=null && search.getPageList().size()>0?search.getPageList().get(curPage):null);
                    request.setAttribute("prodList", prodList);
                    request.setAttribute("family", search.getFamily());
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "search", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/search/{action}/page.html")
    protected String searchNextPage(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode, 
             @PathVariable String action
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/folderResult.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        //String search=request.getParameter("search");
        String key=request.getParameter("key");
        
        SearchBean search=null;
        int curPage=0;
        List<ProductInfo> prodList=null;
         try{ 
            request.setAttribute("pageLink", "search/"+action+"/page");
            request.setAttribute("pagePrefix", "panel/folder");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
               
                if(request.getSession().getAttribute("SEARCH_FOLDER_RESULT")!=null){
                    search=(SearchBean)request.getSession().getAttribute("SEARCH_FOLDER_RESULT");
                    if(search!=null){
                        if(action!=null && action .equalsIgnoreCase("next")){
                            curPage=search.getCurPage()+1;
                            
                        }else if(action!=null && action .equalsIgnoreCase("previous")){
                             curPage=search.getCurPage()-1;
                        }else{
                            try{
                                curPage=Integer.parseInt(action);
                            }catch(Exception ignore){
                                 curPage=0;
                            }
                        }
                        
                        if(curPage>search.getPage()){
                                curPage=search.getPage();
                        }
                        
                        if(curPage<=0){
                                curPage=0;
                        }
                        search.setCurPage(curPage);
                    }
                }else{
                    return "redirect:/panel/folder/"+langCode+"/root/view.html";
                }
                
                if(search!=null){
                    request.getSession().setAttribute("SEARCH_FOLDER_RESULT", search);
                    prodList=(search.getPageList()!=null && search.getPageList().size()>0?search.getPageList().get(curPage):null);
                    request.setAttribute("prodList", prodList);
                    request.setAttribute("family", search.getFamily());
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "search", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        //return "redirect:/panel/folder/"+langCode+"/search.html";
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/{parentUUID}/edit.html")
    protected String folderEdit(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid, 
             @PathVariable String parentUUID
    )throws Exception{
        this.frameHandler=new CustFrameHandler(request, "panel/category/folderEdit.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        CategoryDAO catDAO=null;
        UserInfo user=null;
        FamilyBean family=null;
        SearchBean search=null;
        CategoryInfo current=null;
        CategoryInfo parent=null;
        List<ProductInfo> prodList=null;
        try{ 
            request.setAttribute("pageLink", uuid+"/"+parentUUID+"/edit");
            request.setAttribute("pagePrefix", "panel/folder");
            request.setAttribute("uuid", uuid);
            request.setAttribute("parentUUID", parentUUID);
            
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                if(parentUUID!=null && parentUUID.equalsIgnoreCase("root")){
                    
                }else{
                    parent=catDAO.loadCatURL(parentUUID);
                }
                
                if(parent==null){
                    parent=new CategoryInfo();
                    parent.setId(0);
                    parent.setFamilyId(0);
                    parent.setParentId(0);
                    parent.setUuid("root");
                    parent.setUrl("root");
                    parent.setName("主目錄");
                }
                
                if(request.getSession().getAttribute("ERROR.SAVE.FOLDER")!=null){
                        result=(ResultBean)request.getSession().getAttribute("ERROR.SAVE.FOLDER");
                        request.setAttribute("SAVE_RESULT", result);
                        current=(CategoryInfo)result.getObj();
                        request.getSession().removeAttribute("ERROR.SAVE.FOLDER");
                }
                
                if(current==null){
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        current=new CategoryInfo();
                        System.out.println("Current: "+parent.getUrl());
                        if(parent.getFamilyId()==0){
                            current.setFamilyId(parent.getId());
                        }else{
                            current.setFamilyId(parent.getFamilyId());
                        }

                        current.setParentId(parent.getId());
                    }else{
                        current=catDAO.loadCategory(uuid);
                    }
                }
                
                if(current!=null && user.getIsAdmin()==1){
                   request.setAttribute("category", current);
                   request.setAttribute("parent", parent);
                   request.setAttribute("isEmpty", (catDAO.isEmptyFolder(current)?"Y":"N"));
                   
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
    
    @RequestMapping(value = "/{langCode}/{uuid}/{parentUUID}/edit/save.html")
    protected String editSave(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid, 
             @PathVariable String parentUUID
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
            request.setAttribute("pageLink", uuid+"/"+parentUUID+"/edit.html");
            request.setAttribute("pagePrefix", "panel/folder/");
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                result=catDAO.saveFolder(request, uuid,parentUUID, user);
                if(result!=null && result.getCode()==1){
                    return "redirect:/panel/folder/"+langCode+"/"+parentUUID+"/view.html";
                }else{
                    request.getSession().setAttribute("ERROR.SAVE.FOLDER", result);
                    return "redirect:/panel/folder/"+langCode+"/"+uuid+"/"+parentUUID+"/edit.html";
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
    
    @RequestMapping(value = "/{langCode}/{uuid}/{parentUUID}/edit/delete.html")
    protected String editDelete(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
             @PathVariable String uuid, 
             @PathVariable String parentUUID
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
            request.setAttribute("pageLink", uuid+"/"+parentUUID+"/edit.html");
            request.setAttribute("pagePrefix", "panel/folder/");
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                result=catDAO.delFolder(request, uuid,parentUUID, user);
                if(result!=null && result.getCode()==1){
                    return "redirect:/panel/folder/"+langCode+"/"+parentUUID+"/view.html";
                }else{
                    request.getSession().setAttribute("ERROR.SAVE.FOLDER", result);
                    return "redirect:/panel/folder/"+langCode+"/"+uuid+"/"+parentUUID+"/edit.html";
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
