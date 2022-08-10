/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.dao.CategoryDAO;
import com.ae21.studio.hongchi.entity.dao.HashtagDAO;
import com.ae21.studio.hongchi.entity.dao.ProdDAO;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import com.ae21.studio.hongchi.entity.system.SearchBean;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/panel")
public class PanelSearchController {
     private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/{langCode}/search/query.html")
    protected String searchQuery(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/result.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        //String search=request.getParameter("search");
        String key=request.getParameter("key");
        
        SearchBean search=null;
         try{ 
            request.setAttribute("pageLink", "search/query");
            request.setAttribute("pagePrefix", "panel/");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                
                search=prodDAO.searchProduct(key, 0);
                if(search!=null){
                    request.getSession().setAttribute("SEARCH_RESULT", search);
                }
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "searchQuery", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return "redirect:/panel/"+langCode+"/search.html";
    }
    
    @RequestMapping(value = "/{langCode}/search.html")
    protected String search(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/result.jsp");
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
            request.setAttribute("pagePrefix", "panel/");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                if(request.getSession().getAttribute("SEARCH_RESULT")!=null){
                    search=(SearchBean)request.getSession().getAttribute("SEARCH_RESULT");
                }else{
                    search=prodDAO.searchProduct(key, 0);
                    request.getSession().setAttribute("SEARCH_RESULT", search);
                }
                
                if(search!=null){
                    request.setAttribute("key", search.getKey());
                    curPage=search.getCurPage();
                    prodList=(search.getPageList()!=null?search.getPageList().get(curPage):null);
                    request.setAttribute("prodList", prodList);
                    
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
         this.frameHandler=new CustFrameHandler(request, "panel/search/result.jsp");
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
            request.setAttribute("pagePrefix", "panel/");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                if(request.getSession().getAttribute("SEARCH_RESULT")!=null){
                    search=(SearchBean)request.getSession().getAttribute("SEARCH_RESULT");
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
                    search=prodDAO.searchProduct(key, 0);
                    request.getSession().setAttribute("SEARCH_RESULT", search);
                }
                
                if(search!=null){
                    request.getSession().setAttribute("SEARCH_RESULT", search);
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
        return "redirect:/panel/"+langCode+"/search.html";
    }
    
    @RequestMapping(value = "/{langCode}/search/tag.html")
    protected String tag(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/tag.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        HashtagDAO tagDAO=null;
        String search=request.getParameter("search");
         try{ 
            request.setAttribute("pageLink", "search/tag");
            request.setAttribute("pagePrefix", "panel/");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                tagDAO=(HashtagDAO)common.getDAOObject(request, "tagDAO");
                request.setAttribute("tagList", tagDAO.loadTagList(0));
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "tag", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/search/category.html")
    protected String category(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/category.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        CategoryDAO catDAO=null;
        String search=request.getParameter("search");
         try{ 
            request.setAttribute("pageLink", "search/tag");
            request.setAttribute("pagePrefix", "panel/");
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                catDAO=(CategoryDAO)common.getDAOObject(request, "catDAO");
                request.setAttribute("tagList", catDAO.loadCategoryList(0));
            }else{
                return this.frameHandler.logout(request);
            }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "category", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return this.frameHandler.getReturnPath(request);
    }
    
    @RequestMapping(value = "/{langCode}/editor/search.html")
    protected String editorSearch(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/result.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        //String search=request.getParameter("search");
        String key=request.getParameter("searchKey");
        
        SearchBean search=null;
        int curPage=0;
        List<ProductInfo> prodList=null;
        try{ 
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                System.out.println("Key: "+key+":"+common.getLocalTime());
                search=prodDAO.searchProduct(key, 0,32);
                
                if(search!=null){
                    request.setAttribute("key", search.getKey());
                    curPage=search.getCurPage();
                    prodList=(search.getPageList()!=null?search.getPageList().get(curPage):null);
                    request.setAttribute("userPhotoList", prodList);
                    
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
        return "panel/editor/search/imageResult";
    }
    
    @RequestMapping(value = "/{langCode}/editor/page/{page}/next.html")
    protected String editorSearchNext(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String langCode, 
             @PathVariable int page
    )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "panel/search/result.jsp");
        CommonHandler common=new CommonHandler();
        ResultBean result=null;
        ProdDAO prodDAO=null;
        //String search=request.getParameter("search");
        String key=request.getParameter("key");
        
        SearchBean search=null;
        int curPage=0;
        List<ProductInfo> prodList=null;
         try{ 
           
          
            this.frameHandler.loadTesting(request, 0);
            if(this.frameHandler.isLogin(request)){
                prodDAO=(ProdDAO)common.getDAOObject(request, "prodDAO");
                if(request.getSession().getAttribute("SEARCH_EDITOR_RESULT")!=null){
                    search=(SearchBean)request.getSession().getAttribute("SEARCH_EDITOR_RESULT");
                    if(page>search.getMaxPage()){
                        search.setCurPage(search.getMaxPage());
                    }else if(page<0){
                        search.setCurPage(0);
                    }else{
                        search.setPage(page);
                    }
                }else{
                    search=prodDAO.searchProduct(key, 0);
                    request.getSession().setAttribute("SEARCH_EDITOR_RESULT", search);
                }
                
                if(search!=null){
                    request.setAttribute("key", search.getKey());
                    curPage=search.getCurPage();
                    prodList=(search.getPageList()!=null?search.getPageList().get(curPage):null);
                    request.setAttribute("prodList", prodList);
                    
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
        return "panel/editor/search/imageResult";
    }
}
