
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
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
        try{ 
            
            request.setAttribute("pageLink", prod+"/"+uuid+"/dashboard.html");
            request.setAttribute("pagePrefix", "panel/editor/");
            request.setAttribute("isEditor", "Y");
            request.setAttribute("uuid", uuid);
          
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                
                photo=editorDAO.loadEditorProduct(prod, user);
                editor=editorDAO.loadEditor(uuid, photo, user, false);
                
                if(editor!=null){
                    request.setAttribute("photo", photo );
                    request.setAttribute("editor", editor );
                    if(editor.getEditorItemList()!=null){
                        request.setAttribute("itemList",editorDAO.loadEditorItem(editor) );
                    }
                    request.setAttribute("userPhotoList", prodDAO.loadUserProd(user, 32));
                    request.setAttribute("catList", catDAO.loadCategoryList(0));
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
        try{ 
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
               request.setAttribute("itemDetail", editorDAO.addItem(type, uuid));
                
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
            this.frameHandler.loadTesting(request, 0);
            //System.out.println("OK");
            if(this.frameHandler.isLogin(request)){
                user=this.frameHandler.getLoginUser(request);
                
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                editorDAO=(EditorDAO)common.getDAOObject(request, "editorDAO");
                config = (SystemConfigBean) common.getDAOObject(request, "defaultConfig");
               /* String [] nameList=request.getParameterValues("name");
                System.out.println("Save Action: "+(nameList!=null?nameList.length:"NA"));*/
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
}
