/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.panel;

import com.ae21.bean.ResultBean;
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
                    System.out.println("Result: "+result.getCode());
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
