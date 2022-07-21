/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.form.SelectedBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
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
    
    public List<HashtagInfo> getProdHashtag(ProductInfo prod)throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<HashtagInfo> result = null;
        String sql="SELECT {hi.*} "
                + " FROM product_hashtag pc LEFT JOIN hashtag_info hi ON hi.id=pc.hash_id "
                + " WHERE pc.product_id=:prod"
                + " ORDER by hi.name ";
        try{
            if(prod!=null){
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
    
    public List<SelectedBean> loadSelectedCat(List<CategoryInfo> catList, ProductInfo photo){
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
                            + " ORDER by c.seq, c.name ";
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
    
    public List<ProductInfo> queryProd(String search, int size) throws Exception{
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        List<ProductInfo> result = null;
        String sql="";
        String key="";
        CommonHandler common=new CommonHandler();
        try {
            key=((search!=null && !search.isEmpty())?"%"+search.trim()+"%":"%");
            key=key.replace(" ", "%");
            
            sql="Select {p.*} from product_info p where p.prod_status>0 "
                    + " and search_key like :key "
                    + " order by p.modify_date desc ";
            query =session.createSQLQuery(sql);
            query.addEntity("p", ProductInfo.class);
            query.setString("key", key);
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
        
        String name=request.getParameter("photo-name");
        String desc=request.getParameter("desc");
        String photoURL=request.getParameter("photo");
        String photoUUID=request.getParameter("photo-uuid");
        String statusVal=request.getParameter("status");
        String [] catArray=request.getParameterValues("cat");
        
        String productCat="";
        
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
                        
                        
                        photo.setProductCreateMethod(1);
                        photo.setProductRef(0);
                        photo.setProductUUID(photoUUID);
                        
                        result.setObj(photo);
                        try{
                            int status=Integer.parseInt(statusVal);
                            photo.setStatus(status);
                            result.setObj(photo);
                        }catch(Exception ingore){
                            result.setCode(-2001);
                            result.setMsg("ERROR.STATUS.INVALID");
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
                            String temp="";
                            CategoryInfo cat=null;
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
                            }
                        }catch(Exception ignore){
                            result.setCode(-2003);
                            result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
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
                    
                    sql="DELETE FROM product_category WHERE product_id=:prod ";
                    squery=session.createSQLQuery(sql);
                    squery.setInteger("prod", photo.getId());
                    squery.executeUpdate();
                    
                    sql="INSERT INTO product_category(`product_id`,`category_id`) VALUES (:prod,:cat)";
                    squery=session.createSQLQuery(sql);
                    
                    for(int i=0;catList!=null &&i< catList.size();i++){
                        squery.setInteger("prod", photo.getId());
                        squery.setInteger("cat", catList.get(i).getId());
                        squery.executeUpdate();
                    }
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
        return true;
    }
    
    public ResultBean generateSearchIndex(ProductInfo prod)throws Exception{
        ResultBean result=new ResultBean();
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
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
                
                for(int i=0;catList!=null && i<catList.size();i++ ){
                    cat=catList.get(i);
                    catIndex+="#"+cat.getName()+" ";
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
    
    public ResultBean saveHashtag(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
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
        
        String [] tagValList=request.getParameterValues("tagValue");
        List<HashtagInfo> tagList=null;
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
                        if(tagValList!=null && tagValList.length>0){
                            tagList=new ArrayList<HashtagInfo>();
                            
                            sql="SELECT {hi.*} FROM hashtag_info hi WHERE hi.uuid=:uuid ";
                            squery=session.createSQLQuery(sql);
                            squery.addEntity("hi", HashtagInfo.class);
                            
                            
                            for(int i=0; i<tagValList.length; i++){
                                tagVal=tagValList[i];
                                tagVal=(tagVal!=null?tagVal.trim():"");
                                
                                isExist=false;
                                for(int j=0;!isExist && tagList!=null && j<tagList.size();j++ ){
                                    //System.out.println(tagList.get(j).getName()+":"+tagVal);
                                    if(tagList.get(j).getName()!=null && tagList.get(j).getName().equalsIgnoreCase(tagVal)){
                                        isExist=true;
                                        break;  
                                    }
                                    
                                    if(tagList.get(j).getUuid()!=null && tagList.get(j).getUuid().equalsIgnoreCase(tagVal)){
                                        isExist=true;
                                        break;  
                                    }
                                }
                                
                                if(!isExist){
                                    squery.setString("uuid", tagVal);
                                    tag=(HashtagInfo)squery.uniqueResult();

                                    if(tag==null){
                                        tag=new HashtagInfo();
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
}
