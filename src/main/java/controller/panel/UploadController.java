/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.bean.UserAuthorizedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.dao.UploadDAO;
import com.ae21.studio.hongchi.entity.dao.UserDAO;
import com.ae21.studio.hongchi.entity.system.AWSBean;
import com.ae21.studio.hongchi.entity.system.CustFrameHandler;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping(value = "/panel/upload")
public class UploadController {
    private Logger logger=Logger.getLogger(this.getClass().getName());
    private CustFrameHandler frameHandler=new CustFrameHandler();
    
    @RequestMapping(value = "/file/{uuid}/submit.html")
    //@ResponseBody
    protected String uploadSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String uuid) throws Exception {
        this.frameHandler = new CustFrameHandler(request, "upload/upload.jsp");
        CommonHandler common = new CommonHandler();
        UserDAO userDAO = null;
        UserAuthorizedBean auth = null;
        String result = "-1";
        UploadDAO upDAO = null;
        ResultBean uploadResult = null;
        SystemConfigBean config = null;
        UploadInfo upload = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.frameHandler.invalidURLHandler(response, uuid);
            request.setCharacterEncoding("utf-8");
            userDAO = (UserDAO) common.getDAOObject(request, "userDAO");
            config = (SystemConfigBean) common.getDAOObject(request, "defaultConfig");
            auth = this.frameHandler.checkLoginStatus(request);
            if (auth.isLogined()) {
                upDAO = (UploadDAO) common.getDAOObject(request, "uploadDAO");
                uploadResult = upDAO.upload(request, uuid, (UserInfo) auth.getLoginedUser(), config);
                //System.out.println("Upload Result: "+uploadResult.getCode()+":"+uploadResult.getMsg());
                upload = (UploadInfo) uploadResult.getObj();
                response.setHeader("Content-Type", "application/json; charset=UTF-8");
                response.setContentType("text/html; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                //System.out.println("Result: "+uploadResult.getCode());
                request.setAttribute("upload", upload);
                request.setAttribute("result", uploadResult);
                /*return "{\"code\":\"" + uploadResult.getCode() + "\",\"msg\":\"" + uploadResult.getMsg() + "\",\"src\":\"" + (upload != null ? upload.getUrl() : "") + "\""
                        + ",\"uuid\":\"" + (upload != null ? upload.getUuid() : "") + "\""
                        + ",\"user\":\"" + (upload != null && upload.getUserId() != null ? upload.getUserId().getDisplayName() : "") + "\""
                        + ",\"name\":\"" + (upload != null ? upload.getUploadFileName() : "") + "\""
                        + ",\"fileType\":\"" + (upload != null ? upload.getFileType() : "") + "\""
                        + ",\"modify\":\"" + (upload != null ? sf.format(upload.getUploadDate()) : "") + "\""
                        + "}";*/
                
            }else{
                uploadResult=new ResultBean();
                uploadResult.setCode(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Exception: " + e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        } finally {
            request = this.frameHandler.initPage(request);
        }
        return "panel/upload/jsonResp";
    }
    
    @RequestMapping(value = "/cloud/item/upload.html")
     @ResponseBody
     protected String uploadCloudSubmit(
            HttpServletRequest request,
            HttpServletResponse response
     )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "upload/upload.jsp");
         UserDAO userDAO=null;
         UploadDAO uploadDAO=null;
         UserInfo user=null;
         UploadInfo upload=null;
         AWSBean aws=null;
         CommonHandler common=new CommonHandler();
         UserAuthorizedBean auth=null;
         String path="/images/No_Image.jpg";
         String rtnResult = "{\"code\":-1,\"msg\":\"\"}";
        
         ResultBean result=new ResultBean();
         SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         try{
            userDAO=(UserDAO)common.getDAOObject(request, "userDAO");
            //designDAO=(DesignDAO)common.getDAOObject(request, "designDAO");
           this.frameHandler.loadTesting(request, 0);
           if(this.frameHandler.isLogin(request)){  //check Login Status
                user=this.frameHandler.getLoginUser(request);
                
                aws=(AWSBean)common.getDAOObject(request,"awsConfig");
                uploadDAO=(UploadDAO)common.getDAOObject(request, "uploadDAO");
           
                //System.out.println("Design: "+design.getSubject());
                result=uploadDAO.uploadAWS(request, user, "new", aws);
                if(result!=null && result.getCode()==1){
                    upload=(UploadInfo)result.getObj();
                  
                }
                //System.out.println("Result: "+result.getCode()+": 2: "+result2.getCode());
                 
                response.setHeader("Content-Type", "application/json; charset=UTF-8");
                response.setContentType("text/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                return "{\"code\":\"" + result.getCode() + "\",\"msg\":\"" + result.getMsg() + "\",\"src\":\"" + (upload != null ? upload.getUrl() : "") + "\""
                        + ",\"uuid\":\"" + (upload != null ? upload.getUuid() : "") + "\""
                        + ",\"user\":\"" + (upload != null && upload.getUserId() != null ? upload.getUserId().getDisplayName() : "") + "\""
                        + ",\"name\":\"" + (upload != null ? upload.getUploadFileName() : "") + "\""
                        + ",\"fileType\":\"" + (upload != null ? upload.getFileType() : "") + "\""
                        + ",\"modify\":\"" + (upload != null ? sf.format(upload.getUploadDate()) : "") + "\""
                        + "}";
            }
           
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "uploadCloudSubmit", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return rtnResult;
     }
     
     @RequestMapping(value = "/{type}/{uuid}/{action}.html")
     @ResponseBody
     protected String viewFile(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String uuid, 
             @PathVariable String action, 
             @PathVariable String type
             )throws Exception{
         this.frameHandler=new CustFrameHandler(request, "upload/upload.jsp");
         String rtnResult = "{\"code\":-1,\"msg\":\"\"}";
        
         UploadDAO uploadDAO=null;
        
         UserInfo user=null;
         UploadInfo upload=null;
         //AWSBean aws=null;
         ResultBean result=null;
         CommonHandler common=new CommonHandler();
         try{
           this.frameHandler.loadTesting(request, 0);
           if(this.frameHandler.isLogin(request)){  //check Login Status
                user=this.frameHandler.getLoginUser(request);
                //docDAO=(DocDAO)common.getDAOObject(request, "docDAO");
                //aws=(AWSBean)common.getDAOObject(request,"awsConfig");
                //att=docDAO.loadDocAttach(uuid);
                //if(att!=null){
                    //upload=att.getUploadId();
                    //aws=(AWSBean)common.getDAOObject(request,"awsConfig");
                    uploadDAO=(UploadDAO)common.getDAOObject(request, "uploadDAO");
                    //System.out.println(action+":"+type+":"+uuid);
                    if(type!=null && type.equalsIgnoreCase("editor")){
                        result=uploadDAO.getEditorFile(request, response, uuid, (action!=null && action.equalsIgnoreCase("download")));
                    }else{
                        result=uploadDAO.getFile(request, response, uuid, (action!=null && action.equalsIgnoreCase("download")));
                    }
                    
                    //System.out.println("Result: "+result.getCode()+":"+ "");
                    if(result!=null && result.getCode()==1){
                    }else{
                    }
                //}
           }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return rtnResult;
     }
     
     @RequestMapping(value = "/cloud/{uuid}/preview.html")
     @ResponseBody
     protected String viewCloudFile(
            HttpServletRequest request,
            HttpServletResponse response, 
             @PathVariable String uuid)throws Exception{
         this.frameHandler=new CustFrameHandler(request, "upload/upload.jsp");
         String rtnResult = "{\"code\":-1,\"msg\":\"\"}";
        
         UploadDAO uploadDAO=null;
        
         UserInfo user=null;
         UploadInfo upload=null;
         AWSBean aws=null;
         ResultBean result=null;
         CommonHandler common=new CommonHandler();
         try{
           this.frameHandler.loadTesting(request, 0);
           if(this.frameHandler.isLogin(request)){  //check Login Status
                user=this.frameHandler.getLoginUser(request);
                //docDAO=(DocDAO)common.getDAOObject(request, "docDAO");
                aws=(AWSBean)common.getDAOObject(request,"awsConfig");
                //att=docDAO.loadDocAttach(uuid);
                //if(att!=null){
                    //upload=att.getUploadId();
                    aws=(AWSBean)common.getDAOObject(request,"awsConfig");
                    uploadDAO=(UploadDAO)common.getDAOObject(request, "uploadDAO");
                    
                    result=uploadDAO.getCloudFile(request,response,uuid, aws);
                    //System.out.println("Result: "+result.getCode());
                    if(result!=null && result.getCode()==1){
                    }else{
                    }
                //}
           }
         }catch(Exception e){
            e.printStackTrace();
            logger.severe("Exception: "+e.getMessage());
            logger.throwing(this.getClass().getName(), "index", e);
        }finally{
            request=this.frameHandler.initPage(request);
        }
        return rtnResult;
     }
}
