/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ParaInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.MigrationBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Alex
 */
public class MigrationDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void migration(String rootPath, UserInfo user){
        File root=null;
        CommonHandler common=new CommonHandler();
        ArrayList<File> fileList=new ArrayList<File>();
        List<MigrationBean> list=new ArrayList<MigrationBean>();
        CategoryInfo cat=null;
        MigrationBean migration=null;
        String type="";
        String name="";
        
        try{
            root=new File(rootPath);
            File [] target=root.listFiles();
            for(int i=0;target!=null && i<target.length;i++){
                /*System.out.println("File: "+target[i].getName()+"("+target[i].getAbsolutePath()+")");
                System.out.println("Is Imag: "+common.isImageFileRex(target[i].getName()));
                System.out.println("Is Dir: "+target[i].isDirectory());*/
                fileList=new ArrayList<File>();
                fileList=this.list(target[i], fileList);
                
                if(fileList.size()>0){
                    if(target[i].isDirectory()){
                        cat=this.loadCat(target[i].getName());
                        System.out.println("Cat: "+cat.getName());
                        for(int j=0; fileList!=null && j<fileList.size();j++){
                            migration=this.convert(fileList.get(j), cat, user);
                            System.out.println(migration.getProd().getProductSrc());
                            list.add(migration);
                        }
                    }
                }
                     
            }
            
            this.save(list);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void migration2(String rootPath,String targetPath,  UserInfo user){
        File root=null;
        CommonHandler common=new CommonHandler();
        ArrayList<File> fileList=new ArrayList<File>();
        List<MigrationBean> list=new ArrayList<MigrationBean>();
        CategoryInfo cat=null;
        MigrationBean migration=null;
        String type="";
        String name="";
        
        
        String fileName="";
        String relPath="";
        String subPath[]=null;
        ArrayList<String> pathList=null;
        ArrayList<String> catList=null;
        String subItem[]=null;
        String imgItem[]=null;
        String imgName="";
        String imgElement[]=null;
        try{
            root=new File(rootPath);
            File [] target=root.listFiles();
            for(int i=0;target!=null && i<target.length;i++){
                System.out.println("********************* DIR("+target[i].getName()+"): "+i+"/"+target.length+" ************************");
                /*System.out.println("File: "+target[i].getName()+"("+target[i].getAbsolutePath()+")");
                System.out.println("Is Imag: "+common.isImageFileRex(target[i].getName()));
                System.out.println("Is Dir: "+target[i].isDirectory());*/
                fileList=new ArrayList<File>();
                
                fileList=this.list(target[i], fileList);
                
                if(fileList.size()>0){
                    for(int j=0; fileList!=null && j<fileList.size();j++){
                        fileName=fileList.get(j).getName();
                        System.out.println("_______________________________ FILE("+fileName+"): "+j+"/"+fileList.size()+" _______________________________");
                        System.out.println("File Name: "+fileName);
                        if(true|| fileList.get(i).getAbsolutePath().indexOf("Word常用圖案")>=0){
                        imgName=null;
                        catList=new ArrayList<String>();
                        pathList=new ArrayList<String>();
                        relPath=fileList.get(j).getAbsolutePath();
                        relPath=relPath.replace(rootPath+"\\", "");
                        relPath=relPath.replace("\\", "/");
                        System.out.println("Relate Path: "+relPath);
                        subPath=relPath.split("/");
                        for(int k=0; subPath!=null && k<subPath.length;k++){
                            //System.out.println("SubPath: "+subPath[k]);
                            subItem=null;
                            subPath[k]=subPath[k].replace(" ", "");
                            if(this.isImageFile(subPath[k])){
                                
                                imgItem=subPath[k].split("_");
                                //System.out.println("IMG Name len: "+imgItem.length);
                                if(imgItem!=null && imgItem.length==1){
                                    imgName=imgItem[0];
                                }else if(imgItem.length>1){
                                    imgElement=subPath[k].split(Pattern.quote("."));
                                    //System.out.println("Element Len: "+imgElement.length);
                                    imgName=imgItem[0]+"."+imgElement[1];
                                    imgElement[0]=imgElement[0].replace(imgItem[0]+"_", "");
                                    subItem=imgElement[0].split("_");
                                }
                                
                            }else{
                                subItem=subPath[k].split("_");
                                if(subItem.length>0){
                                    pathList.add(subItem[0]);
                                }
                            }
                            for(int h=0; subItem!=null && h<subItem.length;h++){
                                //System.out.println("subItem: "+subItem[h]);
                                //cat=this.loadCat(subItem[h]);
                                catList.add(subItem[h]);
                                
                            }
                        }
                        System.out.println("IMG Name: "+imgName);
                         System.out.println("--- Path:  ---");
                        for(int x=0;pathList!=null && x<pathList.size(); x++ ){
                            System.out.print("/"+pathList.get(x)+"");
                        }
                        System.out.println("");
                        System.out.println("--- HashTag ---");
                        for(int x=0;catList!=null && x<catList.size(); x++ ){
                            System.out.print("#"+catList.get(x)+" ");
                        }
                        System.out.println("");
                        System.out.println("_______________________________ FILE("+fileName+"): "+j+"/"+fileList.size()+" END_______________________________");
                        
                        if(imgName==null){
                            System.out.println("Break For Loop");
                            break;
                        }else{
                            list.add(this.convert2(fileList.get(j), imgName, pathList, catList, rootPath, targetPath, user));
                        }
                    }
                    
                    }
                    
                    
                    /*
                    if(target[i].isDirectory()){
                        //cat=this.loadCat(target[i].getName());
                        
                        //System.out.println("Cat: "+cat.getName());
                        for(int j=0; fileList!=null && j<fileList.size();j++){
                            migration=this.convert(fileList.get(j), cat, user);
                            System.out.println(migration.getProd().getProductSrc());
                            list.add(migration);
                        }
                    }
                */
                }
                System.out.println("********************* DIR: "+i+"/"+target.length+" END************************");     
            }
            
            if(list!=null && list.size()>0){
                        this.save2(list);
                    }
            
            //this.save(list);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<File> list(File file, ArrayList<File> output){
         CommonHandler common=new CommonHandler();
         File [] dir=null;
        try{
            if(file!=null){
                //System.out.println(file.getAbsoluteFile());
                //System.out.println("#"+common.isImageFileRex(file.getName().toLowerCase())+":"+file.isFile());
                if(file.isDirectory() ){
                    //System.out.println("********************* SUB: "+file.getName()+" ************************");     
                    dir=file.listFiles();
                     for(int i=0;dir!=null && i<dir.length;i++){
                         output=this.list(dir[i],output);
                     }
                     //System.out.println("********************* SUB: "+file.getName()+" END************************");     
                }else if(file.isFile() && common.isImageFileRex(file.getName().toLowerCase().replace(" ", ""))){
                    //System.out.println("Add: "+file.getAbsoluteFile());
                    output.add(file);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }
    
    public MigrationBean convert(File file, CategoryInfo cat, UserInfo user){
        MigrationBean result=null;
        UploadInfo upload=null;
        ProductInfo photo=null;
        String uuid="";
        String fileName="";
        String fileType="";
        CommonHandler common=new CommonHandler();
        String path="";
        try{
            if(cat!=null && file!=null  && file.exists()){
                result=new MigrationBean();
                fileName=file.getName();
                fileType=common.getFileType(file.getName());
                
                upload=new UploadInfo();
                upload.setIsImage((this.isImageFile(file.getName())?1:0));
                path=file.getAbsolutePath();
                //path=path.replace("P:\\temp\\Ernest\\HongChi\\migration\\教學圖庫", "/opt/tomcat9/webapps/hongchi/migration/教學圖庫");
                path=path.replace("\\","/");
                upload.setAbsPath(path);
                upload.setName(fileName.replace(fileType, ""));
                upload.setFileStatus(1);
                upload.setFileType(fileType.replace(".",""));
                upload.setUploadFileName(fileName);
                upload.setUploadDate(common.getLocalTime());
                upload.setUploadFileName(file.getName());
                upload.setIsImage((this.isImageFile(fileName)?1:0));
                upload.setUploadBucket("");
                upload.setUuid(common.generateUUID());
                upload.setUrl("/panel/upload/file/"+upload.getUuid()+"/preview.html");
                upload.setUserId(user);
                result.setCat(cat);
                result.setUpload(upload);
                
                
                photo=new ProductInfo();
                    photo.setCreateDate(common.getLocalTime());
                    photo.setCreateUser(user);
                    photo.setStatus(1);
                    photo.setUuid(common.generateUUID());
                    photo.setSearchKey("");
                    photo.setProductCreateMethod(-1);
                    photo.setProductFileName("");
                    photo.setProductRef(0);
                    photo.setProductSrc("");
                    photo.setProductUrl("");
                    photo.setProductCat("");
                    photo.setProductTag("");
                    photo.setName(upload.getName());
                    photo.setDesc("");
                    photo.setProductCreateMethod(1);
                    photo.setProductRef(0);
                    photo.setProductUUID(upload.getUuid());
                    photo.setStatus(1);
                    photo.setProductCreateMethod(ProductInfo.UPLOAD);
                    photo.setProductRef(upload.getId());
                    photo.setProductFileName(upload.getName());
                    photo.setProductSrc(upload.getAbsPath());
                    photo.setProductUrl(upload.getUrl());
                    photo.setEditorUuid("");
                    photo.setModifyDate(common.getLocalTime());
                    photo.setModifyUser(user);
                    photo.setIsShare(1);
                    result.setProd(photo);
            }
        } catch (Exception e) {
            //throw e;
        } finally {
            //try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public MigrationBean convert2(File file,String fileName,  List<String> pathList, List<String> tagList, String rootPath,String targetPath,  UserInfo user){
        MigrationBean result=null;
        UploadInfo upload=null;
        ProductInfo photo=null;
        String uuid="";
        //String fileName="";
        String fileType="";
        CommonHandler common=new CommonHandler();
        String path="";
        try{
            if( file!=null  && file.exists()){
                result=new MigrationBean();
                //fileName=file.getName();
                fileType=common.getFileType(file.getName());
                
                upload=new UploadInfo();
                upload.setIsImage((this.isImageFile(file.getName())?1:0));
                path=file.getAbsolutePath();
                //path=path.replace("P:\\temp\\Ernest\\HongChi\\migration\\教學圖庫", "/opt/tomcat9/webapps/hongchi/migration/教學圖庫");
                path=(rootPath!=null && !rootPath.isEmpty()?path.replace(rootPath, targetPath):path);
                path=path.replace("\\","/");
                
                upload.setAbsPath(path);
                upload.setName(fileName.replace(fileType, ""));
                upload.setFileStatus(1);
                upload.setFileType(fileType.replace(".",""));
                upload.setUploadFileName(fileName);
                upload.setUploadDate(common.getLocalTime());
                upload.setUploadFileName(file.getName());
                upload.setIsImage((this.isImageFile(fileName)?1:0));
                upload.setUploadBucket("");
                upload.setUuid(common.generateUUID());
                upload.setUrl("/panel/upload/file/"+upload.getUuid()+"/preview.html");
                upload.setUserId(user);
                //result.setCat(cat);
                result.setUpload(upload);
                
                
                photo=new ProductInfo();
                    photo.setCreateDate(common.getLocalTime());
                    photo.setCreateUser(user);
                    photo.setStatus(1);
                    photo.setUuid(common.generateUUID());
                    photo.setSearchKey("");
                    photo.setProductCreateMethod(-1);
                    photo.setProductFileName("");
                    photo.setProductRef(0);
                    photo.setProductSrc("");
                    photo.setProductUrl("");
                    photo.setProductCat("");
                    photo.setProductTag("");
                    photo.setName(upload.getName());
                    photo.setDesc("");
                    photo.setProductCreateMethod(1);
                    photo.setProductRef(0);
                    photo.setProductUUID(upload.getUuid());
                    photo.setStatus(1);
                    photo.setProductCreateMethod(ProductInfo.UPLOAD);
                    photo.setProductRef(upload.getId());
                    photo.setProductFileName(upload.getName());
                    photo.setProductSrc(upload.getAbsPath());
                    photo.setProductUrl(upload.getUrl());
                    photo.setEditorUuid("");
                    photo.setModifyDate(common.getLocalTime());
                    photo.setModifyUser(user);
                    photo.setIsShare(1);
                    result.setProd(photo);
                    
                    if(tagList!=null){
                        for(int i=0;i<tagList.size();i++ ){
                            result.addHashtag(this.loadtag(tagList.get(i), user));
                        }
                    }
                    
                    result.setFolder(this.loadFolder(pathList));
            }
        } catch (Exception e) {
            //throw e;
        } finally {
            //try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public void save(List<MigrationBean> list){
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery = null;
        Transaction tx=null;
        ProductInfo photo=null;
        UploadInfo upload=null;
        CategoryInfo cat=null;
        MigrationBean bean=null;
        String sql="";
        try{
            if(list!=null && list.size()>0){
                tx=session.beginTransaction();
                for(int j=0; list!=null && j<list.size();j++){
                    System.out.println((j+1)+"/"+list.size());
                    System.out.println("File: "+list.get(j).getProd().getName()+"("+list.get(j).getUpload().getAbsPath()+")");
                    //System.out.println("Is Imag: "+common.isImageFileRex(list.get(j).getUpload().getUploadFileName()));
                    bean=list.get(j);
                    if(list.get(j).getUpload()!=null){
                        upload=list.get(j).getUpload();
                        session.saveOrUpdate(upload);
                        bean.setUpload(upload);
                    }
                    
                    if(list.get(j).getProd()!=null){
                        photo=list.get(j).getProd();
                        photo.setProductRef(photo.getId());
                        session.saveOrUpdate(photo);
                        bean.setProd(photo);
                        
                        cat=list.get(j).getCat();
                        if(cat!=null && photo!=null){
                            sql="INSERT INTO product_category(`product_id`,`category_id`) VALUES (:prod,:cat)";
                            squery=session.createSQLQuery(sql);
                            squery.setInteger("prod", photo.getId());
                            squery.setInteger("cat", cat.getId());
                            squery.executeUpdate();
                        }
                        
                    }
                    list.set(j, bean);
                }
                tx.commit();
                /*
                for(int j=0; list!=null && j<list.size();j++){
                    this.generateSearchIndex(list.get(j).getProd());
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
    }
    
    public void save2(List<MigrationBean> list){
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery = null;
        Transaction tx=null;
        ProductInfo photo=null;
        UploadInfo upload=null;
        CategoryInfo cat=null;
        MigrationBean bean=null;
        List<HashtagInfo> tagList=null;
        String sql="";
        try{
            if(list!=null && list.size()>0){
                tx=session.beginTransaction();
                for(int j=0; list!=null && j<list.size();j++){
                    System.out.println((j+1)+"/"+list.size());
                    System.out.println("File: "+list.get(j).getProd().getName()+"("+list.get(j).getUpload().getAbsPath()+")");
                    //System.out.println("Is Imag: "+common.isImageFileRex(list.get(j).getUpload().getUploadFileName()));
                    bean=list.get(j);
                    if(list.get(j).getUpload()!=null){
                        upload=list.get(j).getUpload();
                        session.saveOrUpdate(upload);
                        bean.setUpload(upload);
                    }
                    
                    if(list.get(j).getProd()!=null){
                        photo=list.get(j).getProd();
                        photo.setProductRef(photo.getId());
                        session.saveOrUpdate(photo);
                        bean.setProd(photo);
                        
                        cat=list.get(j).getFolder();
                        if(cat!=null && photo!=null){
                            sql="INSERT INTO product_category(`product_id`,`category_id`) VALUES (:prod,:cat)";
                            squery=session.createSQLQuery(sql);
                            squery.setInteger("prod", photo.getId());
                            squery.setInteger("cat", cat.getId());
                            squery.executeUpdate();
                        }
                        
                        tagList=null;
                        tagList=list.get(j).getHashtagList();
                        for(int k=0; tagList!=null && k<tagList.size();k++){
                            sql="INSERT INTO product_hashtag(`product_id`,`hash_id`) VALUES (:prod,:tag)";
                            squery=session.createSQLQuery(sql);
                            squery.setInteger("prod", photo.getId());
                            squery.setInteger("tag", tagList.get(k).getId());
                            squery.executeUpdate();
                        }
                        
                    }
                    list.set(j, bean);
                }
                tx.commit();
                /*
                for(int j=0; list!=null && j<list.size();j++){
                    this.generateSearchIndex(list.get(j).getProd());
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
    }
    
    public void generateIndexKey(){
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery = null;
        Transaction tx=null;
        ProductInfo photo=null;
        List<ProductInfo> list=null;
        try{
            query=session.getNamedQuery("ProductInfo.findAll");
            list=(List<ProductInfo>)query.list();
            
            tx=session.beginTransaction();
            for(int i=0; list!=null && i<list.size();i++){
                System.out.println("WorkIng: "+(i+1)+"/"+list.size());
                //this.generateSearchIndex(list.get(i));
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
    }
    
    public HashtagInfo loadtag(String tagValue, UserInfo user){
        HashtagInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        Transaction tx=null;
        CommonHandler common=new CommonHandler();
        HashtagInfo tag=null;
        List<HashtagInfo> tagList=null;
        try{
            query=session.getNamedQuery("HashtagInfo.findByName");
            query.setString("name", tagValue);
            tagList=(List<HashtagInfo>)query.list();
            System.out.println(tagList.size()+":"+tagValue);
            if(tagList!=null && tagList.size()>0){
                result=tagList.get(0);
            }else{ //Hashtag Not found, Create a new one
                tag = new HashtagInfo();
                tag.setCreateDate(common.getLocalTime());
                tag.setCreateUser(user);
                tag.setHitRate(0);
                tag.setModifyDate(common.getLocalTime());
                tag.setModifyUser(user);
                tag.setName(tagValue);
                tag.setUuid(common.generateUUID());
                
                if(tag!=null){
                    tx=session.beginTransaction();
                    session.saveOrUpdate(tag);
                    tx.commit();
                    result=tag;
                }
            }
            
            
         } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public CategoryInfo loadFolder(List<String> pathList) throws Exception{
        CategoryInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        SQLQuery squery2=null;
        Transaction tx=null;
        CommonHandler common=new CommonHandler();
        String sql="";
        List<CategoryInfo> folderList=null;
        CategoryInfo folder=null;
        CategoryInfo parent=null;
        try{
            sql="Select {c.*} from category_info c where c.name =:name and c.family_id=:family and c.parent_id=:parent ";
            
            //query=session.getNamedQuery("CategoryInfo.findByName");
            //query.setString("name", key);
            squery=session.createSQLQuery(sql);
            squery.addEntity("c", CategoryInfo.class);
            
            sql="Select {c.*} from category_info c where c.name =:name and c.parent_id=:parent ";
            squery2=session.createSQLQuery(sql);
            squery2.addEntity("c", CategoryInfo.class);
            
             tx=session.beginTransaction();
            for(int i=0; pathList!=null && i<pathList.size();i++){
                if(i==0){
                    squery2.setString("name", pathList.get(i));
                    squery2.setInteger("parent", 0);
                    folderList=(List<CategoryInfo>)squery2.list();
                    
                    if(folderList!=null && folderList.size()>0){
                        
                        parent=folderList.get(0);
                    }else{
                        folder=new CategoryInfo();
                        folder.setDesc("");
                        folder.setName(pathList.get(i));
                        folder.setSeq(99);
                        folder.setUrl(common.generateUUID());
                        folder.setUuid(common.generateUUID());
                        folder.setFamilyId(0);
                        folder.setParentId(0);
                        session.saveOrUpdate(folder);
                        folder.setFamilyId(folder.getId());
                        session.saveOrUpdate(folder);
                        parent=folder;
                    }
                }else if(parent!=null){
                    squery.setString("name", pathList.get(i));
                    squery.setInteger("family", parent.getFamilyId());
                    squery.setInteger("parent", parent.getId());
                    folder=(CategoryInfo)squery.uniqueResult();
                    
                    if(folder==null){
                        folder=new CategoryInfo();
                        folder.setDesc("");
                        folder.setName(pathList.get(i));
                        folder.setSeq(99);
                        folder.setUrl(common.generateUUID());
                        folder.setUuid(common.generateUUID());
                        folder.setFamilyId(parent.getFamilyId());
                        folder.setParentId(parent.getId());
                        
                        session.saveOrUpdate(folder);
                        parent=folder;
                    }else{
                        parent=folder;
                    }
                    
                }
            }
             tx.commit();
            
           
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return parent;
    }
    
    public CategoryInfo loadCat(String key){
        CategoryInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        Transaction tx=null;
        CommonHandler common=new CommonHandler();
        try{
            query=session.getNamedQuery("CategoryInfo.findByName");
            query.setString("name", key);
            result=(CategoryInfo)query.uniqueResult();
            if(result==null){
                result=new CategoryInfo();
                result.setDesc("");
                result.setName(key);
                result.setSeq(99);
                result.setUrl(common.generateUUID());
                result.setUuid(common.generateUUID());
                //result.setFamilyId(0);
                //result.setParentId(0);
                
                tx=session.beginTransaction();
                session.saveOrUpdate(result);
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
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
        }else{
           str =  str.toLowerCase();
        }

        // Pattern class contains matcher() method 
        // to find matching between given string 
        // and regular expression. 
        Matcher m = p.matcher(str);

        // Return if the string 
        // matched the ReGex 
        return m.matches();
    }
    
    public ResultBean generateSearchIndex(int id)throws Exception{
        ResultBean result=new ResultBean();
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery query2 = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        Transaction tx = null;
        
        String searchIndex="";
        String tagIndex="";
        String catIndex="";
        
        ProductInfo prod=null;
        
        List<HashtagInfo> tagList=null;
        List<CategoryInfo> catList=null;
        List<ProductInfo> list=null;
        HashtagInfo tag=null;
        CategoryInfo cat=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            query2=session.createSQLQuery("Select {p.*} from product_info p where id>:id and search_key=''  ");
            query2.addEntity("p", ProductInfo.class);
            query2.setInteger("id", id);
            list=(List<ProductInfo>)query2.list();
            
            for(int x=0; list!=null && x<list.size();x++){
                searchIndex="";
                tagIndex="";
                catIndex="";
                prod=list.get(x);
                System.out.println("Processing ("+prod.getName()+"):"+(x+1)+"/"+list.size());
                
                sql="SELECT {hi.*} "
                + " FROM product_hashtag pc LEFT JOIN hashtag_info hi ON hi.id=pc.hash_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by hi.name ";
                squery=session.createSQLQuery(sql);
                squery.addEntity("hi", HashtagInfo.class);
                squery.setInteger("prod", prod.getId());
                tagList=(List<HashtagInfo>)squery.list();
                
                for(int i=0;tagList!=null && i<tagList.size();i++ ){
                    tag=tagList.get(i);
                    tagIndex+="#"+tag.getName()+" ";
                }
                
                sql="SELECT {ci.*} "
                + " FROM product_category pc LEFT JOIN category_info ci ON ci.id=pc.category_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by ci.seq, ci.name ";
                squery=session.createSQLQuery(sql);
                squery.addEntity("ci", CategoryInfo.class);
                squery.setInteger("prod", prod.getId());
                catList=(List<CategoryInfo>)squery.list();
                
                for(int i=0;catList!=null && i<catList.size();i++ ){
                    cat=catList.get(i);
                    catIndex+="#"+cat.getName()+" ";
                }
                
                searchIndex="#"+prod.getName()+" "+catIndex+" "+tagIndex;
                
                prod.setSearchKey(searchIndex);
                prod.setProductCat(catIndex);
                prod.setProductTag(tagIndex);
                
                list.set(x, prod);
            }
            
            if(result.getCode()==0){
                tx=session.beginTransaction();
                for(int x=0; list!=null && x<list.size();x++){
                    prod=list.get(x);
                    session.saveOrUpdate(prod);
                }
                
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public void addChatImage(String folderPath)throws Exception{
        ResultBean result=new ResultBean();
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery query2 = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        Transaction tx = null;
        
        File folder=null;
        ParaInfo para=null;
        List<ParaInfo> paraList=new ArrayList<ParaInfo>();
        try{
            if(folderPath!=null && !folderPath.isEmpty()){
                folder=new File(folderPath);
                if(folder!=null && folder.isDirectory()){
                    File [] target=folder.listFiles();
                    for(int i=0; target!=null && i<target.length;i++){
                        System.out.println("---------------------- Process "+i+"/"+target.length+"------------------------------");
                        System.out.println("File: "+target[i].getAbsolutePath());
                        System.out.println("File Name: "+target[i].getName());
                        para=new ParaInfo();
                        para.setCode("EDITOR");
                        para.setDd01(0);
                        para.setDd02(0);
                        para.setDd03(0);
                        para.setParaStatus(1);
                        para.setSeq((i+1));
                        para.setStr01("");
                        para.setStr02(target[i].getName());
                        para.setStr03("");
                        para.setSubcode("OBJ");
                        para.setUrl("");
                        para.setUuid(lib.generateUUID());
                        para.setValue(0);
                        
                        paraList.add(para);
                        System.out.println("ID: "+para.getId());
                        System.out.println("---------------------- Process "+i+"/"+target.length+" Complete------------------------------");
                    }
                    
                    tx=session.beginTransaction();
                    for(int i=0; paraList!=null && i<paraList.size();i++){
                        para=paraList.get(i);
                        session.saveOrUpdate(para);
                    }
                    tx.commit();
                }else{
                    System.out.println("Folder not exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
    }
}
