
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.EditorItem;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.CommonDAO;
import com.ae21.studio.hongchi.entity.dao.EditorDAO;
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
@RequestMapping(value = "/panel/editor")
public class PanelEditorController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/{prod}/{uuid}/dashboard.html")
    protected String dashboard(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
            @PathVariable String prod,
             @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo photo=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        CommonDAO comDAO=null;
        String parentURL=request.getParameter("folder");
        String copy=request.getParameter("copy");
        CategoryInfo folder=null;
        ProductInfo copyImage=null;
        EditorItem copyItem=null;
        try{ 
            
            request.setAttribute("pageLink", prod+"/"+uuid+"/dashboard.html");
            request.setAttribute("pagePrefix", "panel/editor/");
            request.setAttribute("isEditor", "Y");
            request.setAttribute("uuid", uuid);
            
            if(request.getSession().getAttribute("SEARCH_EDITOR_RESULT")!=null){
                request.getSession().removeAttribute("SEARCH_EDITOR_RESULT");
            }
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                comDAO=(CommonDAO)common.getDAOObject(request, "commDAO");
                
                photo=editorDAO.loadEditorProduct(prod, user);
                copyImage=prodDAO.loadProd(copy);
                //System.out.println("Copy: "+copy+":"+(copyImage!=null?copyImage.getId():"NULL"));
                editor=editorDAO.loadEditor(uuid, photo, user, false);
                
                if(editor!=null){
                    request.setAttribute("photo", photo );
                    request.setAttribute("editor", editor );
                    
                    if(copyImage!=null){
                        copyItem=editorDAO.addItem("photo", copyImage.getUuid());
                        if(copyItem!=null && editor.getEditorItemList()!=null){
                            editor.getEditorItemList().add(copyItem);
                        }
                    }
                    if(editor.getEditorItemList()!=null){
                        request.setAttribute("itemList",editorDAO.loadEditorItem(editor, true) );
                    }
                    request.setAttribute("userPhotoList", prodDAO.loadUserProd(user, 24));
                    request.setAttribute("catList", catDAO.loadRootCategoryList(0));
                    request.setAttribute("charList", comDAO.getParaList("EDITOR", "CHAR", 0));
                    request.setAttribute("objList", comDAO.getParaList("EDITOR", "OBJ", 0));
                    request.setAttribute("emotionList", comDAO.getParaList("ROLE", "EMOTION", 0));
                   request.setAttribute("actionList", comDAO.getParaList("ROLE", "ACTION", 0));
                    
                    if(parentURL!=null && !parentURL.isEmpty()){
                        //folder=catDAO.loadCatURL(parentURL);
                        request.setAttribute("folder", parentURL);
                    }
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
    
    @RequestMapping(value = "/{langCode}/item/{type}/{uuid}/add.html")
    protected String addItem(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
            @PathVariable String type, 
            @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo photo=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        CommonDAO comDAO=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                comDAO=(CommonDAO)common.getDAOObject(request, "commDAO");
               request.setAttribute("itemDetail", editorDAO.addItem(type, uuid));
               
               if(type!=null && type.equalsIgnoreCase("role")){
                   request.setAttribute("emotionList", comDAO.getParaList("ROLE", "EMOTION", 0));
                   request.setAttribute("actionList", comDAO.getParaList("ROLE", "ACTION", 0));
               }
                
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "addItem", e);
        }finally{
            //request=this.frameHandler.initPage(request);
        }
        return "panel/editor/add_item";
    }
    
    @RequestMapping(value = "/{langCode}/item/upload/add.html")
    protected String addUpload(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo prod=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        String uuid="new";
        String parentURL=request.getParameter("folder");
        CategoryInfo parent=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
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
                        prod.setIsShare(0);
                    }
                }
                
                if(prod!=null){
                    request.setAttribute("photo", prod);
                    if(parentURL!=null && !parentURL.isEmpty()){
                        parent=catDAO.loadCatURL(parentURL);
                        if(parent!=null){
                            request.setAttribute("folder", parent);
                            //System.out.println("P: "+parent.getName());
                        }
                    }
                    request.setAttribute("catList", prodDAO.loadSelectedCat(catDAO.loadCategoryList(0), prod,parent));
                    
                    
                }
                
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "addUpload", e);
        }finally{
            //request=this.frameHandler.initPage(request);
        }
        return "panel/editor/item/upload-ajax";
    }
    
    @RequestMapping(value = "/{langCode}/item/upload/save.html")
    protected String saveUpload(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo prod=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        ProdDAO prodDAO=null;
        CategoryDAO catDAO=null;
        String uuid="new";
        ResultBean result=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                result=prodDAO.saveProd(request, uuid, user);
                request.setAttribute("result", result);
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "addUpload", e);
        }finally{
            //request=this.frameHandler.initPage(request);
        }
        return "panel/editor/resp/save_upload";
    }
    
    @RequestMapping(value = "/{langCode}/{uuid}/save.html")
    protected String save(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode,
            @PathVariable String uuid
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo photo=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        ProdDAO prodDAO=null;
        ResultBean result=null;
        SystemConfigBean config=null;
        try{ 
            request.setAttribute("inputKey", uuid);
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                config = (SystemConfigBean) common.getDAOObject(request, "defaultConfig");
               /* String [] nameList=request.getParameterValues("name");
                System.out.println("Save Action: "+(nameList!=null?nameList.length:"NA"));*/
               //System.out.println(uuid);
               result=editorDAO.save(request, uuid, user);
                request.setAttribute("result", result);
                if(result!=null && result.getCode()==1){
                    editor=(EditorInfo)result.getObj();
                    editorDAO.generatePhoto(editor, user, config);
                    prodDAO.generateSearchIndex(editor.getProdId());
                }
               //request.setAttribute("itemDetail", editorDAO.addItem(type, uuid));
                
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "save", e);
        }finally{
            //request=this.frameHandler.initPage(request);
        }
        return "panel/editor/resp/save_resp";
    }
    
    @RequestMapping(value = "/{langCode}/role/{key}/detail/search.html")
    protected String searchRoleDetail(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode, 
             @PathVariable int key
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/editor/dashboard.jsp");
        CommonHandler common=new CommonHandler();
        ProductInfo prod=null;
        EditorInfo editor=null;
        UserInfo user=null;
        EditorDAO editorDAO=null;
        String uuid="new";
        ResultBean result=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                request.setAttribute("userPhotoList", editorDAO.loadRoleDetail(request, key));
                request.setAttribute("isRole", "Y");
            }else{
                return this.frameHandler.logout(request);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "searchRoleDetail", e);
        }finally{
            //request=this.frameHandler.initPage(request);
        }
        return "panel/editor/search/imageResult";
    }
}
