/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.handler.CommonHandler;
import com.ae21.handler.ImageHandler;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.AWSBean;
import com.ae21.studio.hongchi.entity.system.AWSLib;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Alex
 */
public class UploadDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public UploadInfo LoadUpload(String uuid)throws Exception{
        UploadInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try{
            query=session.getNamedQuery("UploadInfo.findByUuid");
            query.setString("uuid", uuid);
            result=(UploadInfo)query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
        }
        
        return result;
    }
    
    public ResultBean upload(HttpServletRequest request,String uuid,  UserInfo user,SystemConfigBean config )throws Exception{
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        ResultBean result=new ResultBean();
        
        MultipartFile file = null;
        MultipartHttpServletRequest multipartRequest;
        ImageHandler imgLib=new ImageHandler();
        BufferedImage uploadFile=null;
        InputStream is=null;
        File target=null;
        File targetDir=null;
        
        String fileType="";
        String fileName="";
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2=new SimpleDateFormat("HHmmss");
        String url="";
        
        UploadInfo upload=null;
        String pathTime="";
        
        try{
            result.setCode(0);
            
            if(user==null){
                result.setCode(-1);
                result.setMsg("upload.err.system");
            }
            
            try{
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    upload=new UploadInfo();
                    
                    upload.setUserId(user);
                    upload.setUuid(UUID.randomUUID().toString().toUpperCase());
                    upload.setUploadDate(lib.getLocalTime());
                    
                }else{
                    upload=this.LoadUpload(uuid);
                }
            }catch(Exception ignore){
                ignore.printStackTrace();
                upload=null;
            }
            
            if(upload==null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    result.setCode(-101);
                    result.setMsg("upload.error.create");
                }else{
                    result.setCode(-102);
                    result.setMsg("upload.error.load");
                }
            }
            
            if(config!=null){
                targetDir=new File(config.getAbsPath()+"/"+user.getUuid()+"/"+sf.format(lib.getLocalTime()));
                //System.out.println("Dir: "+targetDir.getAbsolutePath());
                try{
                    if(!targetDir.exists()){
                        //targetDir.mkdirs();
                        FileUtils.forceMkdir(targetDir);
                    }
                }catch(Exception ioe){
                    targetDir=null;
                }
            }else{
                result.setCode(-201);
                result.setMsg("upload.error.config");
            }
            
            if(targetDir==null || !targetDir.exists()){
                result.setCode(-202);
                result.setMsg("upload.error.FOLDER_NOT_FOUND");
                
            }
            
            if(result.getCode()==0){
                multipartRequest
                        = (MultipartHttpServletRequest) request;
                file = multipartRequest.getFile("file");
                fileName=file.getOriginalFilename();
                fileName=fileName.replace(" ", "_");
                fileType=""+lib.getFileType(file.getOriginalFilename()).replace(".","");
                //System.out.println("Is Image: "+lib.isImageFile(fileName));
                
                is=file.getInputStream();
                
                //upload.setIsImage(lib.isImageFile(fileName));
                    /*
                if(lib.isImageFile(fileName)){
                    uploadFile=imgLib.resizeImageWithHint(file.getInputStream());
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(uploadFile, fileType, os);
                    is = new ByteArrayInputStream(os.toByteArray());
                    upload.setIsImage(1);
                }else{
                    is=file.getInputStream();
                    upload.setIsImage(0);
                }*/
               pathTime=sf.format(lib.getLocalTime());
               target=new File(targetDir.getAbsoluteFile()+"/"+fileName);
               if(target.exists()){
                   target=new File(targetDir.getAbsoluteFile()+"/"+sf2.format(lib.getLocalTime())+"_"+fileName);
               }
               //System.out.println("Folder: "+fileName+":"+fileType);
               FileUtils.copyInputStreamToFile(is, target);
               //FileUtils.
               
               //System.out.println("Uplaod File: "+target.getAbsolutePath());
               //System.out.println("Uplaod File: "+target.exists());
               
               upload.setIsImage((this.isImageFile(fileName)?1:0));
                upload.setAbsPath(target.getAbsolutePath());
                upload.setName(fileName);
                upload.setFileStatus(1);
                upload.setFileType(fileType);
                upload.setUploadFileName(fileName);
                upload.setUploadDate(lib.getLocalTime());
                upload.setUploadFileName(target.getName());
                //url=config.getImgHost()+user.getUuid()+"/"+pathTime+"/"+URLEncoder.encode(target.getName(), "UTF-8");
                url="/panel/upload/file/"+upload.getUuid()+"/preview.html";
                //System.out.println("URI: "+url);
                //upload.setUploadUrl(url);
                upload.setUrl(url);
                //upload.setAbsPath(pathTime);
                //upload.setUploadType("UPLOAD");
                //upload.setIsImage(1);
                
                tx=session.beginTransaction();
                session.saveOrUpdate(upload);
                tx.commit();
                
                result.setObj(upload);
                result.setCode(1);
                
                
                if(config.getBuiltPath()!=null && !config.getBuiltPath().isEmpty()){
                    target=new File(config.getBuiltPath()+"/"+user.getUuid()+"/"+pathTime+"/"+fileName);
                    //System.out.println("Target: "+target);
                    FileUtils.copyInputStreamToFile(is, target);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
            
            
        }
        return result;
    }
    /*
    public ResultBean uploadAWS(HttpServletRequest request, UserInfo user,DocInfo doc, String uuid,    AWSBean aws)throws Exception{
        ResultBean result=new ResultBean();
        ResultBean upResult=null;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        DocAttach att=null;
        UploadInfo upload=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if (doc != null) {
                upResult = this.uploadAWS(request, user, uuid, aws);
                if (upResult.getCode() == 1) {
                    upload = (UploadInfo) upResult.getObj();
                    if (uuid != null && uuid.equalsIgnoreCase("new")) {
                        att = new DocAttach();
                        att.setAttachStatus(1);
                        if (upload.getIsImage() == 1) {
                            att.setCoverImage(upload.getUrl());
                            
                        }else{
                            att.setCoverImage("");
                            
                        }
                        att.setPreviewUrl(upload.getUrl());
                        att.setDocId(doc);
                        att.setFileType(upload.getFileType());
                        att.setFileUrl(upload.getAbsPath());
                        att.setName(upload.getName());
                        att.setSeq(99);
                        att.setUploadDate(lib.getLocalTime());
                        att.setUploadId(upload);
                        att.setUploadUser(upload.getUserId());
                        att.setUuid(lib.generateUUID());
                    } else {

                    }

                    if (att != null) {
                        tx = session.beginTransaction();
                        session.saveOrUpdate(att);
                        tx.commit();

                        result.setObj(att);
                        result.setCode(1);
                        result.setMsg("label.success");

                    } else {
                        result.setCode(-1003);
                    }
                } else {
                    result.setCode(-1001);
                }
            } else {
                result.setCode(-1002);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
        }
            
            
        }
        return result;
    }
*/
    public ResultBean uploadAWS(HttpServletRequest request, UserInfo user,String uuid,    AWSBean aws)throws Exception{
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        
        MultipartFile image = null;
        MultipartHttpServletRequest multipartRequest;
        ImageHandler imgLib=new ImageHandler();
        AmazonS3 s3=null;
        AWSLib awsLib=new AWSLib();
        AmazonS3Client s3Client=null;
        BufferedImage uploadImage=null;
        ObjectMetadata metadata=new ObjectMetadata();
        CommonHandler common=new CommonHandler();
        ResultBean result=new ResultBean();
        
        InputStream is=null;
        String fileType="";
        String fileName="";
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHH");
        DecimalFormat df=new DecimalFormat("0000000000");
        
        UploadInfo upload=null;
        try{
            result.setCode(0);
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    upload=new UploadInfo();
                    upload.setUserId(user);
                    upload.setUuid(uuid);
                    upload.setAbsPath("");
                    upload.setUploadBucket("");
                    upload.setFileStatus(0);
                    upload.setFileType("");
                    upload.setIsImage(0);
                    upload.setName("");
                    upload.setUploadDate(lib.getLocalTime());
                    upload.setUploadFileName("");
                    upload.setUrl("");
                    
                }else{
                    upload=this.LoadUpload(uuid);
                }
            }else{
                result.setCode(-1001);
                result.setMsg("UPLOAD_INVALID_LOGIN");
            }
            
           
            
            if(upload!=null && result.getCode()==0){
                upload.setUploadDate(lib.getLocalTime());
                //upload.setUploadTime(lib.getLocalTime());
                //upload.setAwsBasket(aws.getPrefix()+"/"+user.getUuid().toUpperCase()+"/"+sf.format(lib.getLocalTime())+"/");

                multipartRequest
                        = (MultipartHttpServletRequest) request;
                image = multipartRequest.getFile("file");
                //uploadImage=imgLib.resizeImageWithHint(image.getInputStream());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                fileName=image.getOriginalFilename();
                //fileName=lib.getFileType(image.getOriginalFilename());
                fileType=""+lib.getFileType(image.getOriginalFilename()).replace(".","");
                if(this.isImageFile(fileName)){
                    //uploadImage=imgLib.resizeImageWithHint(image.getInputStream());
                    uploadImage=ImageIO.read(image.getInputStream());
                    ImageIO.write(uploadImage, fileType, os);
                    is = new ByteArrayInputStream(os.toByteArray());
                }else{
                     is=image.getInputStream();
                }
                //System.out.println("Size("+this.isImageFile(fileName)+"): "+is.available());
                
                //upload.setFileName(fileName);
                upload.setFileType(fileType);
                //upload.setUploadFileName(fileName);
                upload.setUploadFileName(image.getOriginalFilename());
                upload.setName(fileName);
                upload.setIsImage((this.isImageFile(fileName)?1:0));
                upload.setUploadBucket("");
                try{
                    upload.setUploadBucket(sf.format(common.getLocalTime())+"/");
                }catch(Exception ignore){}

                s3=aws.getS3();
                try {
                    try{
                        if(this.isImageFile(fileName)){
                        metadata.setContentLength(os.toByteArray().length);
                        }
                        metadata.setContentType(fileType);
                    }catch(Exception ingore){
                        System.err.printf("Failed while reading bytes from %s", ingore.getMessage());
                    }

                    AccessControlList acl = new AccessControlList();
                    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
                    acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Write);
                    System.out.println("Upload FIle Size: "+image.getSize());
                    //System.out.println("Upload FIle Size: "+upload.getTotalSpace());
                    //PutObjectRequest putObject = new PutObjectRequest(aws.getBucketName(), "user/" + user.getUid().toUpperCase() + lib.getFileType(image.getOriginalFilename()), lib.convert(image)).withAccessControlList(acl);
                    PutObjectRequest putObject = new PutObjectRequest(aws.getBucketName(), upload.getUploadBucket()+ fileName,is,metadata).withAccessControlList(acl);
                    System.out.println("Upload File: "+putObject.getBucketName()+putObject.getKey()+":"+putObject.getRedirectLocation());
                    PutObjectResult putResult=s3.putObject(putObject);
                    
                    System.out.println("Upload Result: "+putResult.getVersionId()+":"+putResult.getMetadata().getContentType());
                    //upload.setAwsPath(""+s3.getUrl(aws.getBucketName(), upload.getAwsPath()+fileName));
                    upload.setAbsPath(""+s3.getUrl(aws.getBucketName(), upload.getUploadBucket()+fileName));
                    
                    System.out.println("URL: "+s3.getUrl(aws.getBucketName(), upload.getUploadBucket()+fileName));
                    //upload.deleteOnExit();
                } catch (AmazonServiceException ase) {
                    System.out.println("Caught an AmazonServiceException, which means your request made it "
                            + "to Amazon S3, but was rejected with an error response for some reason.");
                    System.out.println("Error Message:    " + ase.getMessage());
                    System.out.println("HTTP Status Code: " + ase.getStatusCode());
                    System.out.println("AWS Error Code:   " + ase.getErrorCode());
                    System.out.println("Error Type:       " + ase.getErrorType());
                    System.out.println("Request ID:       " + ase.getRequestId());
                    result.setCode(-3);
                    result.setMsg("UPLOAD_INVALID_FILE");
                } catch (AmazonClientException ace) {
                    System.out.println("Caught an AmazonClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with S3, "
                            + "such as not being able to access the network.");
                    System.out.println("Error Message: " + ace.getMessage());
                }
                
                //System.out.println("Result: "+result.getCode());
                if(result.getCode()==0){
                    
                    tx=session.beginTransaction();
                    //upload.setIsValid(1);
                    if(upload.getUuid()!=null && upload.getUuid().equalsIgnoreCase("new")){
                        upload.setUuid(UUID.randomUUID().toString().toUpperCase());
                    }
                    upload.setUrl(request.getContextPath()+"/panel/upload/cloud/"+upload.getUuid()+"/preview.html");
                    session.saveOrUpdate(upload);
                    tx.commit();
                    result.setCode(1);
                    result.setObj(upload);
                }
            }else{
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
            
            try{com.amazonaws.http.IdleConnectionReaper.shutdown();}catch(Exception ignore){}
        }
        return result;
    }
    
    public ResultBean getFile(HttpServletRequest request,HttpServletResponse response,  String uuid, boolean isDownload)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        UploadInfo upRecord=null;
        File file=null;
        String mime="image/jpg";
        
        try{
            if(uuid!=null){
                query=session.getNamedQuery("UploadInfo.findByUuid");
                query.setString("uuid", uuid);
                upRecord=(UploadInfo)query.uniqueResult();
                
                if(upRecord!=null){
                    file=new File(upRecord.getAbsPath());
                    try{
                        //System.out.println("Path: "+file.getAbsolutePath());
                        mime=Files.probeContentType(Paths.get(upRecord.getAbsPath()));
                        response.setContentType(mime+"; charset=utf-8");
                        response.setContentLength((int)file.length());
                    }catch(Exception e){}
                   
                     // Set to expire far in the past.
                    response.setDateHeader("Expires", 0);
                    // Set standard HTTP/1.1 no-cache headers.
                    
                    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
                    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                    // Set standard HTTP/1.0 no-cache header.
                    response.setHeader("Pragma", "no-cache");
                    
                    response.setHeader("Content-Disposition",(isDownload?"attach;":"")+"filename=\""+URLEncoder.encode(upRecord.getName(),"utf-8")+"\"");
                    
                    
                    
                    OutputStream out = response.getOutputStream();
                    //FileOutputStream fos   = new FileOutputStream(upRecord.getAbsPath());
                    
                    //System.out.println("Size: "+file.length());
                    FileInputStream fileInputStream = new FileInputStream(file);
                    
                    // Copy the contents of the file to the output stream
                     byte[] buf = new byte[1024];
                     int count = 0;
                     while ((count = fileInputStream.read(buf)) >= 0) {
                       out.write(buf, 0, count);
                    }
                    //out=fos;
                    //File file=new File(upRecord.getAbsPath());
                    //System.out.println("len: "+file.exists()+":"+fileInputStream.available()+":"+upRecord.getAbsPath());
                    out.close();
                    fileInputStream.close();
                    
                    result.setCode(1);
                }
            }
        } catch (Exception e) {
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
            //try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public ResultBean getEditorFile(HttpServletRequest request,HttpServletResponse response,  String uuid, boolean isDownload)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        //UploadInfo upRecord=null;
        File file=null;
        String mime="image/jpg";
        EditorInfo editor=null;
        
        try{
            if(uuid!=null){
                query=session.getNamedQuery("EditorInfo.findByUuid");
                query.setString("uuid", uuid);
                editor=(EditorInfo)query.uniqueResult();
                
                if(editor!=null){
                    file=new File(editor.getFileAbsSrc());
                    try{
                        //System.out.println("Path: "+file.getAbsolutePath());
                        mime=Files.probeContentType(Paths.get(editor.getFileAbsSrc()));
                        response.setContentType(mime+"; charset=utf-8");
                        response.setContentLength((int)file.length());
                    }catch(Exception e){}
                   
                     // Set to expire far in the past.
                    response.setDateHeader("Expires", 0);
                    // Set standard HTTP/1.1 no-cache headers.
                    
                    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
                    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                    // Set standard HTTP/1.0 no-cache header.
                    response.setHeader("Pragma", "no-cache");
                    
                    response.setHeader("Content-Disposition",(isDownload?"attach;":"")+"filename=\""+URLEncoder.encode(editor.getName()+".png","utf-8")+"\"");
                    
                    
                    
                    OutputStream out = response.getOutputStream();
                    //FileOutputStream fos   = new FileOutputStream(upRecord.getAbsPath());
                    
                    //System.out.println("Size: "+file.length());
                    if(file.exists()){
                        FileInputStream fileInputStream = new FileInputStream(file);

                        // Copy the contents of the file to the output stream
                         byte[] buf = new byte[1024];
                         int count = 0;
                         while ((count = fileInputStream.read(buf)) >= 0) {
                           out.write(buf, 0, count);
                        }
                         if(fileInputStream!=null){
                            fileInputStream.close();
                         }
                    }
                    //out=fos;
                    //File file=new File(upRecord.getAbsPath());
                    //System.out.println("len: "+file.exists()+":"+fileInputStream.available()+":"+upRecord.getAbsPath());
                    out.close();
                   
                    
                    result.setCode(1);
                }
            }
        } catch (Exception e) {
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
            //try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public ResultBean getCloudFile(HttpServletRequest request,HttpServletResponse response,  String uuid, AWSBean aws)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        UploadInfo upRecord=null;
        
        AmazonS3 s3=null;
        AWSLib awsLib=new AWSLib();
        AmazonS3Client s3Client=null;
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        File output=null;
        
        ServletContext cntx= null;
        InputStream fileInputStream = null;
        OutputStream out=null;
        try{
            result.setCode(0);
            query=session.getNamedQuery("UploadInfo.findByUuid");
            query.setString("uuid", uuid);
            upRecord=(UploadInfo)query.uniqueResult();
            
            if(upRecord!=null){
                s3=aws.getS3();
                try{
                    fullObject=s3.getObject(new GetObjectRequest(aws.getBucketName(), upRecord.getUploadBucket()+upRecord.getName()));
                    System.out.println("Content-Type: " + fullObject.getKey());
                    System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
                    System.out.println("Content: "+ fullObject.getObjectContent());
                    //output=new File(""+upRecord.getName());
                    
                    //cntx= request.getServletContext();
                    String mime = fullObject.getObjectMetadata().getContentType();//cntx.getMimeType(fullObject.getKey());
                    System.out.println("Mime: "+mime);
                    response.setContentType(mime);
                    if(this.isImageFile(mime)){
                    }else{
                          // Set to expire far in the past.
                        response.setDateHeader("Expires", 0);
                        // Set standard HTTP/1.1 no-cache headers.
                        //response.setContentType("text/html; charset=utf-8");
                        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
                        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                        // Set standard HTTP/1.0 no-cache header.
                        response.setHeader("Pragma", "no-cache");
                        //response.setHeader("Content-Disposition","inline");
                        if(upRecord.getIsImage()==1){
                            response.setHeader("Content-Disposition","filename=\""+upRecord.getName()+"\"");
                        }else{
                            if(upRecord.getFileType()!=null && upRecord.getFileType().equalsIgnoreCase("pdf")){
                                response.setContentType("application/pdf");
                                response.setHeader("Content-Disposition","inline;filename=\""+upRecord.getName()+"\"");
                            }else{
                                response.setContentType("application/x-download");
                                response.setHeader("Content-Disposition","attach;filename=\""+lib.encodeFilename(upRecord.getName())+"\"");
                                //System.out.println("Content-Disposition"+"attach;filename=\""+upRecord.getName()+"\"");
                                
                                //response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
                            }
                            
                        }
                        
                    }
                    
                    
                    out = response.getOutputStream();
                    fileInputStream=fullObject.getObjectContent();

                    // Copy the contents of the file to the output stream
                     byte[] buf = new byte[1024];
                     int count = 0;
                     while ((count = fileInputStream.read(buf)) >= 0) {
                       out.write(buf, 0, count);
                    }
                    out.close();
                    fileInputStream.close();
                    
                    result.setCode(1);
                    
                } catch (AmazonServiceException ase) {
                    System.out.println("Caught an AmazonServiceException, which means your request made it "
                            + "to Amazon S3, but was rejected with an error response for some reason.");
                    System.out.println("Error Message:    " + ase.getMessage());
                    System.out.println("HTTP Status Code: " + ase.getStatusCode());
                    System.out.println("AWS Error Code:   " + ase.getErrorCode());
                    System.out.println("Error Type:       " + ase.getErrorType());
                    System.out.println("Request ID:       " + ase.getRequestId());
                    result.setCode(-1);
                    result.setMsg("INVALID_UPLOAD_FILE");
                } catch (AmazonClientException ace) {
                    System.out.println("Caught an AmazonClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with S3, "
                            + "such as not being able to access the network.");
                    System.out.println("Error Message: " + ace.getMessage());
                }
            }else{
                result.setCode(-2);
            }
            
        } catch (Exception e) {
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
            
            try{com.amazonaws.http.IdleConnectionReaper.shutdown();}catch(Exception ignore){}
        }
        return result;
         
     }
    
    /*
    public ResultBean delDocAttach(HttpServletRequest request,HttpServletResponse response,  String uuid, AWSBean aws, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        UploadInfo upRecord=null;
        DocAttach att=null;
        AmazonS3 s3=null;
        AWSLib awsLib=new AWSLib();
        AmazonS3Client s3Client=null;
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        File output=null;
        
        ServletContext cntx= null;
        InputStream fileInputStream = null;
        OutputStream out=null;
        
        String fileName="";
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            query=session.getNamedQuery("DocAttach.findByUuid");
            query.setString("uuid", uuid);
            att=(DocAttach)query.uniqueResult();
            upRecord=(att!=null?att.getUploadId():null);
            fileName=(att!=null?att.getName():"");
            result.setObj(att);
            
            if(upRecord!=null){
                
                s3=aws.getS3();
                try{
                    if(user!=null && user.getIsAdmin()==1){
                        s3.deleteObject(new DeleteObjectRequest(aws.getBucketName(), upRecord.getUploadBucket()+upRecord.getName()));
                        
                        tx=session.beginTransaction();
                        session.delete(upRecord);
                        session.delete(att);
                        tx.commit();
                        
                        result.setCode(1);
                        result.setMsg(""+fileName);
                        //result.setMsg("label.success");
                    }else{
                        result.setCode(-1001);
                        result.setMsg("ERROR.ACCESS");
                    }
                 
                    
                } catch (AmazonServiceException ase) {
                    System.out.println("Caught an AmazonServiceException, which means your request made it "
                            + "to Amazon S3, but was rejected with an error response for some reason.");
                    System.out.println("Error Message:    " + ase.getMessage());
                    System.out.println("HTTP Status Code: " + ase.getStatusCode());
                    System.out.println("AWS Error Code:   " + ase.getErrorCode());
                    System.out.println("Error Type:       " + ase.getErrorType());
                    System.out.println("Request ID:       " + ase.getRequestId());
                    result.setCode(-1);
                    result.setMsg("INVALID_UPLOAD_FILE");
                } catch (AmazonClientException ace) {
                    System.out.println("Caught an AmazonClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with S3, "
                            + "such as not being able to access the network.");
                    System.out.println("Error Message: " + ace.getMessage());
                }
            }else{
                result.setCode(-2);
            }
            
        } catch (Exception e) {
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
            
            try{com.amazonaws.http.IdleConnectionReaper.shutdown();}catch(Exception ignore){}
        }
        return result;
         
     }
    */
    
    
    // Function to validate image file extension . 
    public boolean isImageFile(String str) {
        // Regex to check valid image file extension. 
        String regex
                = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp|tiff))$)";

        // Compile the ReGex 
        Pattern p = Pattern.compile(regex);

        // If the string is empty 
        // return false 
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher() method 
        // to find matching between given string 
        // and regular expression. 
        Matcher m = p.matcher(str);

        // Return if the string 
        // matched the ReGex 
        return m.matches();
    }
    
    
   
    
    public ResultBean getCloudKeyFile(HttpServletRequest request,HttpServletResponse response,  String key, AWSBean aws)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        CommonHandler lib = new CommonHandler();
        
        AmazonS3 s3=null;
        AWSLib awsLib=new AWSLib();
        AmazonS3Client s3Client=null;
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        File output=null;
        
        ServletContext cntx= null;
        InputStream fileInputStream = null;
        OutputStream out=null;
        
        try{
            result.setCode(0);
           
                s3=aws.getS3();
                try{
                    fullObject=s3.getObject(new GetObjectRequest(aws.getBucketName(), key));
                    //System.out.println("Content-Type: " + fullObject.getKey());
                    //System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
                    //System.out.println("Content: "+ fullObject.getObjectContent());
                    //output=new File(""+upRecord.getName());
                    
                    //cntx= request.getServletContext();
                    String mime = cntx.getMimeType(fullObject.getKey());
                    //System.out.println("Mime: "+mime);
                
                    response.setContentType(mime);
                    
                    out = response.getOutputStream();
                    fileInputStream=fullObject.getObjectContent();

                    // Copy the contents of the file to the output stream
                     byte[] buf = new byte[1024];
                     int count = 0;
                     while ((count = fileInputStream.read(buf)) >= 0) {
                       out.write(buf, 0, count);
                    }
                    out.close();
                    fileInputStream.close();
                    
                    result.setCode(1);
                    
                } catch (AmazonServiceException ase) {
                    System.out.println("Caught an AmazonServiceException, which means your request made it "
                            + "to Amazon S3, but was rejected with an error response for some reason.");
                    System.out.println("Error Message:    " + ase.getMessage());
                    System.out.println("HTTP Status Code: " + ase.getStatusCode());
                    System.out.println("AWS Error Code:   " + ase.getErrorCode());
                    System.out.println("Error Type:       " + ase.getErrorType());
                    System.out.println("Request ID:       " + ase.getRequestId());
                    result.setCode(-1);
                    result.setMsg("INVALID_UPLOAD_FILE");
                } catch (AmazonClientException ace) {
                    System.out.println("Caught an AmazonClientException, which means the client encountered "
                            + "a serious internal problem while trying to communicate with S3, "
                            + "such as not being able to access the network.");
                    System.out.println("Error Message: " + ace.getMessage());
                }
            
            
        } catch (Exception e) {
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {
                session.close();
            } catch (Exception ignore) {
            }
            
            try{com.amazonaws.http.IdleConnectionReaper.shutdown();}catch(Exception ignore){}
        }
        return result;
         
     }
}
