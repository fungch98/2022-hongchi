/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.form.SelectedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.SearchBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Alex
 */
public class ProdDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public ProductInfo loadProd(String uuid) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = null;
        ProductInfo result = null;
        try {
            query = session.getNamedQuery("ProductInfo.findByUuid");
            query.setString("uuid", uuid);
            result = (ProductInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public ProductInfo loadProd(int id) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = null;
        ProductInfo result = null;
        try {
            query = session.getNamedQuery("ProductInfo.findById");
            query.setInteger("id", id);
            result = (ProductInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<ProductInfo> loadAllProd(int size) throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<ProductInfo> result = null;
        String sql="";
        try {
            sql="Select {p.*} from product_info p where p.prod_status>0 order by p.modify_date desc ";
            query =session.createSQLQuery(sql);
            query.addEntity("p", ProductInfo.class);
            if(size>0){
                query.setMaxResults(size);
            }
            result = (List<ProductInfo>) query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<ProductInfo> loadUserProd(UserInfo user, int size) throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<ProductInfo> result = null;
        String sql="SELECT {p.*} FROM product_info p "
                + " WHERE modify_user=:user AND prod_status=1 "
                + " ORDER BY modify_date desc, p.name ";
        try{
            if(user!=null){
                query=session.createSQLQuery(sql);
                query.addEntity("p", ProductInfo.class);
                query.setInteger("user", user.getId());
                if(size>0){
                    query.setMaxResults(size);
                }
                result=(List<ProductInfo>)query.list();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<CategoryInfo> getProdCat(ProductInfo prod)throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<CategoryInfo> result = null;
        String sql="SELECT {ci.*} "
                + " FROM product_category pc LEFT JOIN category_info ci ON ci.id=pc.category_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by ci.seq, ci.name ";
        try{
            if(prod!=null){
                query=session.createSQLQuery(sql);
                query.addEntity("ci", CategoryInfo.class);
                query.setInteger("prod", prod.getId());
                result=(List<CategoryInfo>)query.list();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
        
    }
    
    public List<List<CategoryInfo>> getProdFolder(ProductInfo prod)throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        List<CategoryInfo> catList = null;
        List<List<CategoryInfo>> result=null;
        List<CategoryInfo> subList=null;
        CategoryInfo cat=null;
        CategoryInfo parent=null;
        String sql="SELECT {ci.*} "
                + " FROM product_category pc LEFT JOIN category_info ci ON ci.id=pc.category_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by ci.seq, ci.name ";
        int parentID=0;
        try{
            if(prod!=null){
               query=session.getNamedQuery("CategoryInfo.findById");
               catList=this.getProdCat(prod);
               for(int i=0;catList!=null &&  i<catList.size(); i++){
                   cat=catList.get(i);
                   if(cat!=null){
                        subList=new ArrayList<CategoryInfo>();
                        subList.add(cat);
                        parentID=cat.getParentId();

                        while(parentID!=0){
                            query.setInteger("id", parentID);
                            parent=(CategoryInfo)query.uniqueResult();
                            if(parent!=null){
                                parentID=parent.getParentId();
                                subList.add(0, parent);
                            }else{
                                parentID=0; //If parent not find, assumbe it is root
                            }
                        }
                   }
                   if(subList!=null && subList.size()>0){
                       if(result==null){
                           result=new ArrayList<List<CategoryInfo>>();
                       }
                       result.add(subList);
                   }
               }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
        
    }
    
    public List<HashtagInfo> getProdHashtag(ProductInfo prod)throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<HashtagInfo> result = null;
        String sql="SELECT {hi.*} "
                + " FROM product_hashtag pc LEFT JOIN hashtag_info hi ON hi.id=pc.hash_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by hi.name ";
        try{
            if(prod!=null && prod.getId()!=null){
                query=session.createSQLQuery(sql);
                query.addEntity("hi", HashtagInfo.class);
                query.setInteger("prod", prod.getId());
                result=(List<HashtagInfo>)query.list();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
        
    }
    
    public String getHashtagString(ProductInfo prod)throws Exception{
        String result="";
        List<HashtagInfo> tagList=null;
        
        try{
            if(prod!=null){
                tagList=this.getProdHashtag(prod);
                for(int i=0;tagList!=null && i<tagList.size();i++ ){
                    result+=(result!=null && !result.isEmpty()?", ":"")+tagList.get(i).getName();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public List<SelectedBean> loadSelectedCat(List<CategoryInfo> catList, ProductInfo photo, CategoryInfo curFolder){
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<SelectedBean> result = null;
        List<CategoryInfo> photoCatList=null;
        SelectedBean selected=null;
        String sql="";
        CategoryInfo cat=null;
        CategoryInfo photoCat=null;
        try{
            if(catList!=null && catList.size()>0){
                result=new ArrayList<SelectedBean>();
                if(photo!=null && !photo.getUuid().equalsIgnoreCase("new")){
                    sql="SELECT {c.*} "
                            + " FROM product_category pc left join category_info c ON c.id=pc.category_id "
                            + " WHERE  pc.product_id=:prod "
                            + " ORDER by c.family_id, c.parent_id, c.seq, c.name ";
                    query=session.createSQLQuery(sql);
                    query.addEntity("c", CategoryInfo.class);
                    query.setInteger("prod", (photo!=null && photo.getId()!=null?photo.getId():0));
                    photoCatList=(List<CategoryInfo>)query.list();
                }
                for(int i=0;catList!=null && i<catList.size();i++){
                    cat=catList.get(i);
                    if(cat!=null){
                        selected=new SelectedBean();
                        selected.setPara(cat);
                        selected.setSelected(false);
                        for(int j=0;photoCatList!=null && j<photoCatList.size();j++){
                            photoCat=photoCatList.get(j);
                            if(photoCat!=null && cat!=null
                                    && cat.getId().intValue()==photoCat.getId().intValue()){
                                selected.setSelected(true);
                            }
                        }
                        
                        if(!selected.isSelected() && curFolder!=null && curFolder.getId()!=null){
                            if(cat!=null && cat.getId().intValue()==curFolder.getId().intValue()){
                                selected.setSelected(true);
                            }
                        }
                        result.add(selected);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<HashtagInfo> loadProdHashtag(ProductInfo prod)throws Exception{
        List<HashtagInfo> result=null;
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        String sql="";
        try{
            if(prod!=null){
                sql="SELECT {hi.*} "
                        + "FROM product_hashtag ph left join hashtag_info hi ON hi.id=ph.hash_id  "
                        + "WHERE ph.product_id=:prod ORDER BY hi.name";
                query=session.createSQLQuery(sql);
                query.addEntity("hi", HashtagInfo.class);
                query.setInteger("prod", prod.getId());
                result=(List<HashtagInfo>)query.list();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public SearchBean searchProduct(String key, int size) throws Exception{
        return this.searchProduct(key,null,  size, 24,null);
    }
    
    public SearchBean searchProduct(String key,String type,UserInfo user, int size) throws Exception{
        return this.searchProduct(key,type,  size, 24,user);
    }
    
    public SearchBean searchProduct(String key,String type,  int size, int max, UserInfo user) throws Exception{
        SearchBean search=null;
        double needPage=0;
        try{
            search=new SearchBean();
            search.setKey(key);
            search.setCurPage(0);
            search.setPageItems(max);
            //System.out.println(type+":"+type.equalsIgnoreCase("personal"));
            if(type!=null && (type.equalsIgnoreCase("personal") || type.equalsIgnoreCase("PERSONAL_FOLDER"))){
                search.setResultList(this.queryProd(key,user,true, (user!=null && user.getIsAdmin()==1?true:false),  size));
            }else{
                search.setResultList(this.queryProd(key,user,false, (user!=null && user.getIsAdmin()==1?true:false),   size));
            }
            //System.out.println("size: "+search.getResultList().size());
            search.generatePageList();
            
            this.addHotKeyWord(search);
        }catch(Exception e){
            e.printStackTrace();
        }
        return search;
    }
    
   
    
    public List<ProductInfo> queryProd(String search,UserInfo user,boolean isPersonal, boolean isAdmin,  int size) throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<ProductInfo> result = null;
        String sql="";
        String subSql="";
        String key="";
        String keyList []=null;
        CommonHandler common=new CommonHandler();
        try {
            //key=((search!=null && !search.isEmpty())?"%"+search.trim()+"%":"%");
            //key=key.replace(" ", "%");
            key=((search!=null && !search.isEmpty())?search.trim():"");
            if(key!=null && !key.isEmpty()){
                keyList=key.split(" ");
            }
            //System.out.println("Key: "+key);
            sql="Select {p.*} from product_info p where p.prod_status>=0 ";
                    //+ " and search_key like :key "
                    
                    for(int i=0;keyList!=null &&  i<keyList.length; i++){
                        if(keyList[i]!=null){
                            subSql+=(subSql!=null && !subSql.isEmpty()?" and ":"")+" search_key like :key"+i+" ";
                        }
                        
                    }
                    if(subSql!=null && !subSql.isEmpty()){
                        sql += " and ("+ subSql+") ";
                    }
                    sql += (user!=null && isPersonal? " and  p.create_user=:user ":" and p.prod_status>0  "+(isAdmin?"":" and  (p.is_share=1 || p.create_user=:user) ")+"   ")
                    + " order by p.modify_date desc ";
                    //System.out.println("Search sql("+user.getDisplayName()+":"+isPersonal+":"+user.getId()+"): "+sql);
                    
            query =session.createSQLQuery(sql);
            query.addEntity("p", ProductInfo.class);
            for(int i=0;keyList!=null &&  i<keyList.length; i++){
                if(keyList[i]!=null){
                    key="%"+keyList[i].trim()+"%";
                    key=key.replace("+", "%");
                    query.setString("key"+i, key);
                    //System.out.println(key);
                }
            }
            
            if(user!=null && isPersonal){
                query.setInteger("user", user.getId());
                //System.out.println("User: "+user.getId()+":"+user.getDisplayName());
            }else if(!isAdmin && user!=null){
                query.setInteger("user", user.getId());
                //System.out.println("User"+user.getId());
            }
            if(size>0){
                query.setMaxResults(size);
            }
            result = (List<ProductInfo>) query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public ResultBean saveProd(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        ProductInfo photo=null;
        UploadInfo up=null;
        List<CategoryInfo> catList=null;
        List<HashtagInfo> tagList=null;
        
        String name=request.getParameter("photo-name");
        String desc=request.getParameter("desc");
        String photoURL=request.getParameter("photo");
        String photoUUID=request.getParameter("photo-uuid");
        String statusVal=request.getParameter("status");
        String shareVal=request.getParameter("isShare");
        String [] catArray=request.getParameterValues("cat");
        String folderUUID=request.getParameter("folder");
        String hashtagVal=request.getParameter("hashtag");
        
        String productCat="";
        EditorInfo editor=null;
        CategoryInfo cat=null;
        
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    photo=new ProductInfo();
                    photo.setCreateDate(lib.getLocalTime());
                    photo.setCreateUser(user);
                    photo.setStatus(1);
                    photo.setUuid(uuid);
                    photo.setSearchKey("");
                    photo.setProductCreateMethod(-1);
                    photo.setProductFileName("");
                    photo.setProductRef(0);
                    photo.setProductSrc("");
                    photo.setProductUrl("");
                    photo.setProductCat("");
                    photo.setProductTag("");
                    photo.setProductCreateMethod(1);
                    photo.setIsShare(0);
                }else{
                    photo=this.loadProd(uuid);
                }
                
                if(photo!=null){
                    if(this.checkAllowEdit(photo, user)){
                        name=(name!=null?name.trim():"");
                        desc=(desc!=null?desc.trim():"");
                        photoURL=(photoURL!=null?photoURL.trim():"");
                        photoUUID=(photoUUID!=null?photoUUID.trim():"");
                        
                        photo.setName(name);
                        photo.setDesc(desc);
                        
                       
                        
                        photo.setProductRef(0);
                        photo.setProductUUID(photoUUID);
                        
                        result.setObj(photo);
                       
                        
                        if(user!=null && user.getIsAdmin()==1){
                             try{
                                //System.out.println("Status: "+statusVal);
                                int status=Integer.parseInt(statusVal);
                                photo.setStatus(status);
                                result.setObj(photo);
                            }catch(Exception ingore){
                                result.setCode(-2001);
                                result.setMsg("ERROR.STATUS.INVALID");
                            }
                            
                            try{
                                int isShare=Integer.parseInt(shareVal);
                                photo.setIsShare(isShare);
                                result.setObj(photo);
                            }catch(Exception ingore){
                                ingore.printStackTrace();
                                result.setCode(-2021);
                                result.setMsg("ERROR.SHARE.INVALID");
                            }

                            try{
                                String temp="";
                                

                                if(folderUUID!=null){
                                     query=session.getNamedQuery("CategoryInfo.findByUuid");
                                     query.setString("uuid", folderUUID);
                                     cat=(CategoryInfo)query.uniqueResult();

                                     
                                     
                                }
                                /*
                                if(catArray!=null && catArray.length>0){
                                    catList=new ArrayList<CategoryInfo>();

                                    query=session.getNamedQuery("CategoryInfo.findByUuid");

                                    for(int i=0; catArray!=null && i<catArray.length;i++ ){
                                        temp=catArray[i];
                                        query.setString("uuid", temp);
                                        cat=(CategoryInfo)query.uniqueResult();

                                        if(cat!=null){
                                            catList.add(cat);
                                            productCat+="#"+cat.getName()+" ";
                                        }else{
                                            result.setCode(-20031);
                                            result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                                        }
                                    }
                                    photo.setProductCat(productCat);
                                }*/
                            }catch(Exception ignore){
                                result.setCode(-2003);
                                result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                            }
                        }else{
                            try{
                                if(photo!=null && photo.getId()!=null){
                                    sql="SELECT {c.*} FROM product_category pc LEFT JOIN category_info c ON c.id=pc.category_id WHERE pc.product_id=:prod LIMIT 1";
                                    squery=session.createSQLQuery(sql);
                                    squery.addEntity("c", CategoryInfo.class);
                                    squery.setInteger("prod", photo.getId());
                                    cat=(CategoryInfo)squery.uniqueResult();
                                }
                                
                                if(cat==null){                                
                                    sql="SELECT {c.*} FROM category_info c LEFT JOIN para_info p ON p.value=c.id WHERE p.code='FOLDER' AND p.subcode='PERSONAL' AND p.para_status=1 ";
                                    squery=session.createSQLQuery(sql);
                                    squery.addEntity("c", CategoryInfo.class);
                                    cat=(CategoryInfo)squery.uniqueResult();
                                }
                                
                               //System.out.println("Prod: "+photo.getId()+(cat!=null?"exist":"null"));
                            }catch(Exception ignore){
                                result.setCode(-2114);
                                    result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                            }
                        }
                        
                        if(cat!=null){
                            catList=new ArrayList<CategoryInfo>();
                             catList.add(cat);
                             productCat+="#"+cat.getName()+" ";
                             photo.setProductCat(productCat);
                         }else{
                             result.setCode(-20031);
                             result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                         }
                        
                        try{
                            sql="SELECT {up.*} FROM upload_info up WHERE UUID=:uuid ";
                            squery=session.createSQLQuery(sql);
                            squery.addEntity("up", UploadInfo.class);
                            squery.setString("uuid", photoUUID);
                            up=(UploadInfo)squery.uniqueResult();
                            
                            if(up!=null){
                                photo.setProductCreateMethod(ProductInfo.UPLOAD);
                                photo.setProductRef(up.getId());
                                photo.setProductFileName(up.getName());
                                photo.setProductSrc(up.getAbsPath());
                                photo.setProductUrl(up.getUrl());
                            }else{
                                photo.setProductUrl(photoURL);
                            }
                        }catch(Exception ignore){
                            result.setCode(-2002);
                            result.setMsg("ERROR.PHOTO.UPLOAD.UUID");
                        }
                        
                        
                        
                        
                        try{
                            tagList=this.strToHashtag(hashtagVal, user, session);
                        }catch(Exception ignore){
                        }
                        
                        
                    }else{
                        result.setCode(-1002);
                        result.setMsg("ERROR.ACCESS");
                    }
                }else{
                    result.setCode(-1003);
                    result.setMsg("ERROR.NOFOUND");
                }
                
                if(photo!=null && result.getCode()==0){
                    tx=session.beginTransaction();
                    photo.setModifyDate(lib.getLocalTime());
                    photo.setModifyUser(user);
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        photo.setUuid(lib.generateUUID());
                    }
                    session.saveOrUpdate(photo);
                     if(photo.getEditorUuid()!=null){
                        try{
                            sql="update editor_info set name=:name, editor_desc=:desc where uuid=:uuid ";
                            squery=session.createSQLQuery(sql);
                            squery.setString("name", photo.getName());
                            squery.setString("desc", photo.getDesc());
                            squery.setString("uuid", photo.getEditorUuid());
                             squery.executeUpdate();
                             //System.out.println("update editor_info set name=:name, editor_desc=:desc where uuid='"+photo.getEditorUuid()+"' ");
                        }catch(Exception ingore){
                            ingore.printStackTrace();
                        }
                    }
                    
                    sql="DELETE FROM product_category WHERE product_id=:prod ";
                    squery=session.createSQLQuery(sql);
                    squery.setInteger("prod", photo.getId());
                    squery.executeUpdate();
                    
                    sql="INSERT INTO product_category(`product_id`,`category_id`) VALUES (:prod,:cat)";
                    squery=session.createSQLQuery(sql);
                    
                    for(int i=0;catList!=null &&i< catList.size();i++){
                        //System.out.println();
                        squery.setInteger("prod", photo.getId());
                        squery.setInteger("cat", catList.get(i).getId());
                        squery.executeUpdate();
                    }
                    
                    this.saveHashtagAction(session, tagList, photo);
                    tx.commit();
                    this.generateSearchIndex(photo);
                    result.setCode(1);
                    result.setMsg("label.success");
                    result.setObj(photo);
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public ResultBean delProd(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        ProductInfo photo=null;
        UploadInfo up=null;
        List<CategoryInfo> catList=null;
        
        String name=request.getParameter("name");
        String desc=request.getParameter("desc");
        String photoURL=request.getParameter("photo");
        String photoUUID=request.getParameter("photo-uuid");
        String statusVal=request.getParameter("status");
        String [] catArray=request.getParameterValues("cat");
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                photo=this.loadProd(uuid);
                
                if(photo!=null ){
                    if(this.checkAllowEdit(photo, user)){
                        photo.setStatus(-1);
                    }else{
                        result.setCode(-1002);
                        result.setMsg("ERROR.ACCESS");
                    }
                }else{
                    result.setCode(-1003);
                    result.setMsg("ERROR.NOFOUND");
                }
                
                if(photo!=null && result.getCode()==0){
                    tx=session.beginTransaction();
                    photo.setModifyDate(lib.getLocalTime());
                    photo.setModifyUser(user);
                    session.saveOrUpdate(photo);
                    
                    
                    tx.commit();
                    
                    result.setCode(1);
                    result.setMsg("label.success");
                    result.setObj(photo);
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public boolean checkAllowEdit(ProductInfo photo, UserInfo user)throws Exception{
        boolean result=false;
        if(user!=null  && user.getId()!=null){
            if(user.getIsAdmin()==1){
                return true;
            }else{
                if(photo!=null){
                    if(photo.getUuid()!=null && photo.getUuid().equalsIgnoreCase("new")){
                        return true;
                    }else if(photo.getCreateUser()!=null && photo.getCreateUser().getId()!=null && 
                            photo.getCreateUser().getId().intValue()==user.getId().intValue()){
                        return true;
                    }else{
                        //System.out.println(photo.getCreateUser().getId()+":"+user.getId());
                    }
                }
            }
        }
        return result;
    }
    
    public ResultBean generateSearchIndex(ProductInfo prod)throws Exception{
        ResultBean result=new ResultBean();
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        SQLQuery squery2=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        Transaction tx = null;
        
        String searchIndex="";
        String tagIndex="";
        String catIndex="";
        
        List<HashtagInfo> tagList=null;
        List<CategoryInfo> catList=null;
        HashtagInfo tag=null;
        CategoryInfo cat=null;
        CategoryInfo parent=null;
        
        int parentID=0;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(prod!=null){
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
                
                sql="Select {ci.*} from category_info ci where ci.id=:id";
                squery2=session.createSQLQuery(sql);
                squery2.addEntity("ci", CategoryInfo.class);
                
                
                for(int i=0;catList!=null && i<catList.size();i++ ){
                    cat=catList.get(i);
                    catIndex="#"+cat.getName()+" "+catIndex;
                    parentID=cat.getParentId();
                    while(parentID!=0){
                        //System.out.println("ParentID: "+parentID);
                        squery2.setInteger("id", parentID);
                        parent=(CategoryInfo)squery2.uniqueResult();
                        
                        if(parent!=null){
                            if(catIndex.indexOf("#"+parent.getName().trim())<0){
                                 catIndex="#"+parent.getName()+" "+catIndex;
                            }
                            parentID=parent.getParentId();
                        }else{
                            parentID=0;
                        }
                    }
                }
                
                searchIndex="#"+prod.getName()+" "+catIndex+" "+tagIndex;
                
                prod.setSearchKey(searchIndex);
                prod.setProductCat(catIndex);
                prod.setProductTag(tagIndex);
                
            }else{
                result.setCode(-1003);
                result.setMsg("ERROR.NOFOUND");
            }
            
            if(result.getCode()==0){
                tx=session.beginTransaction();
                session.saveOrUpdate(prod);
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
    
    public List<HashtagInfo> strToHashtag(String tagStr, UserInfo user, Session session)throws Exception{
        List<HashtagInfo> tagList=null;
        String [] tagValList=null;
        try{
            if(tagStr!=null){
                tagStr=tagStr.trim();
                tagStr=tagStr.replace(";", ",");
                //System.out.println("tag: |"+tagStr+"|");
                tagValList=tagStr.split(",");
                tagList=this.strToHashtag(tagValList, user, session);
            }
            
        }catch(Exception ignore){
        }
        return tagList;
    }
    
    public List<HashtagInfo> strToHashtag(String tagValList[], UserInfo user, Session session)throws Exception{
        List<HashtagInfo> tagList=null;
        
       
        Query query = null;
        SQLQuery squery=null;
        SQLQuery squery2=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        List<HashtagInfo> repeatList=null;
        String tagVal="";
        HashtagInfo tag=null;
        boolean isExist=false;
        try{
            if (tagValList != null && tagValList.length > 0) {
                tagList = new ArrayList<HashtagInfo>();

                sql = "SELECT {hi.*} FROM hashtag_info hi WHERE hi.uuid=:uuid ";
                squery = session.createSQLQuery(sql);
                squery.addEntity("hi", HashtagInfo.class);

                sql = "SELECT {hi.*} FROM hashtag_info hi WHERE hi.name=:name ";
                squery2 = session.createSQLQuery(sql);
                squery2.addEntity("hi", HashtagInfo.class);
                //System.out.println("TagLen: "+tagValList.length);
                for (int i = 0; i < tagValList.length; i++) {
                    tagVal = tagValList[i];
                    tagVal = (tagVal != null ? tagVal.trim() : "");
                    if(tagVal!=null && !tagVal.isEmpty()){
                        isExist = false;
                        for (int j = 0; !isExist && tagList != null && j < tagList.size(); j++) {
                            //System.out.println(tagList.get(j).getName()+":"+tagVal);
                            if (tagList.get(j).getName() != null && tagList.get(j).getName().equalsIgnoreCase(tagVal)) {
                                isExist = true;
                                break;
                            }

                            if (tagList.get(j).getUuid() != null && tagList.get(j).getUuid().equalsIgnoreCase(tagVal)) {
                                isExist = true;
                                break;
                            }
                        }

                        if (!isExist) {
                            squery.setString("uuid", tagVal);
                            tag = (HashtagInfo) squery.uniqueResult();

                            if (tag == null) {
                                squery2.setString("name", tagVal);
                                repeatList = (List<HashtagInfo>) squery2.list();
                                if (repeatList != null && repeatList.size() > 0) {
                                    tag = repeatList.get(0);
                                }
                            }

                            if (tag == null) {
                                tag = new HashtagInfo();
                                tag.setCreateDate(lib.getLocalTime());
                                tag.setCreateUser(user);
                                tag.setHitRate(0);
                                tag.setModifyDate(lib.getLocalTime());
                                tag.setModifyUser(user);
                                tag.setName(tagVal);
                                tag.setUuid(tagVal);
                            }
                            tagList.add(tag);
                        }
                    }
                }
            }
        }catch(Exception ignore){
        }
        return tagList;
    }
    
    public ResultBean saveHashtag(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        SQLQuery squery2=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        ProductInfo photo=null;
        UploadInfo up=null;
        List<CategoryInfo> catList=null;
        
        String [] tagValList=request.getParameterValues("tagValue");
        List<HashtagInfo> tagList=null;
        List<HashtagInfo> repeatList=null;
        String tagVal="";
        HashtagInfo tag=null;
        boolean isExist=false;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                photo=this.loadProd(uuid);
                
                if(photo!=null){
                    if(this.checkAllowEdit(photo, user)){
                        tagList=this.strToHashtag(tagValList, user, session);
                        result.setObj(tagList);
                    }else{
                        result.setCode(-1002);
                        result.setMsg("ERROR.ACCESS");
                    }
                }else{
                    result.setCode(-1003);
                    result.setMsg("ERROR.NOFOUND");
                }
                
                if(photo!=null && result.getCode()==0){
                    tx=session.beginTransaction();
                    
                    this.saveHashtagAction(session, tagList, photo);
                    tx.commit();
                    this.generateSearchIndex(photo);
                    result.setCode(1);
                    result.setMsg("label.success");
                    result.setObj(tagList);
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public void saveHashtagAction(Session session,List<HashtagInfo> tagList , ProductInfo photo)throws Exception{
        HashtagInfo tag=null;
        String sql="";
        SQLQuery squery=null;
        CommonHandler lib=new CommonHandler();
        for(int i=0; tagList!=null && i<tagList.size();i++){
            tag=tagList.get(i);
            if(tag!=null && tag.getId()!=null  && tag.getId().intValue()>0){

            }else{
                tag.setUuid(lib.generateUUID());
                session.saveOrUpdate(tag);
            }
            tagList.set(i, tag);
        }

        sql="DELETE FROM product_hashtag ph where ph.product_id=:prod ";
        squery=session.createSQLQuery(sql);
        squery.setInteger("prod", photo.getId());
        squery.executeUpdate();

        sql="INSERT INTO product_hashtag (`product_id`,`hash_id`) VALUES (:prod, :tag)  ";
        squery=session.createSQLQuery(sql);
        for(int i=0; tagList!=null && i<tagList.size();i++){
            tag=tagList.get(i);
            squery.setInteger("prod", photo.getId());
            squery.setInteger("tag", tag.getId());
            squery.executeUpdate();
        }
                    
    }
    
    public ResultBean addHotKeyWord(SearchBean search)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        String key="";
        String keyList[]=null;
        try{
            if(search!=null && search.getKey()!=null  && !search.getKey().isEmpty()){
                sql="update hashtag_info SET hit_rate=hit_rate+1 WHERE `name` =:tag ";
                squery=session.createSQLQuery(sql);
                key=search.getKey();
                key=(key!=null?key.trim():"");
                keyList=search.getKey().split(" ");
                
                tx=session.beginTransaction();
                for(int i=0;keyList!=null && i<keyList.length;i++ ){
                    squery.setString("tag", (keyList[i]!=null?keyList[i].trim():""));
                    squery.executeUpdate();
                }
                tx.commit();
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
}
