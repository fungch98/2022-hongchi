/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
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
public class HashtagDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<HashtagInfo> loadTagList(int size)throws Exception{
        List<HashtagInfo> result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try{
            query=session.getNamedQuery("HashtagInfo.findAll");
            if(size>0){
                query.setMaxResults(size);
            }
            result=(List<HashtagInfo>)query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public HashtagInfo loadTag(String uuid)throws Exception{
        HashtagInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try{
            if(uuid!=null){
                query=session.getNamedQuery("HashtagInfo.findByUuid");
                query.setString("uuid", uuid);
                //System.out.println("UUID: "+uuid);
                result=(HashtagInfo)query.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public List<ProductInfo> getTagProduct(HashtagInfo tag)throws Exception{
        List<ProductInfo> result=null;
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        String sql="";
        try{
            if(tag!=null && tag.getId()!=null){
                sql="SELECT {p.*} "
                        + " FROM product_hashtag ph LEFT JOIN product_info p ON p.id=ph.product_id "
                        + " WHERE ph.hash_id=:id AND p.prod_status=1 "
                        + " ORDER BY p.name, p.modify_date desc";
                query=session.createSQLQuery(sql);
                query.addEntity("p", ProductInfo.class);
                query.setInteger("id", tag.getId());
                result=(List<ProductInfo>)query.list();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public ResultBean saveTag(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        HashtagInfo tag=null;
        String name=request.getParameter("tag-name");
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    tag=new HashtagInfo();
                    tag.setCreateDate(lib.getLocalTime());
                    tag.setCreateUser(user);
                    tag.setHitRate(0);
                    
                }else{
                    tag=this.loadTag(uuid);
                }
                
                
                if(tag!=null){
                    if(name==null || name.isEmpty()){
                        result.setCode(-2001);
                        result.setMsg("ERROR.TAG.SAVE.EMPTY");
                    }else{
                        name=(name!=null? name.trim():"");
                    }
                    
                    tag.setName(name);
                    
                    try{
                        sql="SELECT {hi.*} FROM hashtag_info hi WHERE `name` =:name and uuid!=:uuid ";
                        squery=session.createSQLQuery(sql);
                        squery.addEntity("hi", HashtagInfo.class);
                        squery.setString("name", name);
                        squery.setString("uuid", uuid);
                        Object temp=squery.uniqueResult();
                        if(temp!=null){
                            result.setCode(-2021);
                            result.setMsg("ERROR.TAG.SAVE.DUPLICATE");
                        }
                    }catch(Exception ignore){
                        result.setCode(-2022);
                        result.setMsg("ERROR.TAG.SAVE.DUPLICATE");
                    }
                    
                    result.setObj(tag);
                }else{
                    result.setCode(-1003);
                    result.setMsg("ERROR.NOFOUND");
                }
                
                if(tag!=null && result.getCode()==0){
                    tx=session.beginTransaction();
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        tag.setUuid(lib.generateUUID());
                    }
                    tag.setModifyDate(lib.getLocalTime());
                    tag.setModifyUser(user);
                    session.saveOrUpdate(tag);
                    tx.commit();
                    
                    result.setCode(1);
                    result.setMsg("label.success");
                    result.setObj(tag);
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
    
    public ResultBean delTag(HttpServletRequest request, String uuid, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        HashtagInfo tag=null;
        String name=request.getParameter("tag-name");
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null){
                tag=this.loadTag(uuid);
                
                
                if(tag!=null){
                    
                }else{
                    result.setCode(-1003);
                    result.setMsg("ERROR.NOFOUND");
                }
                
                if(tag!=null && result.getCode()==0){
                    tx=session.beginTransaction();
                     session.delete(tag);
                    tx.commit();
                    
                    result.setCode(1);
                    result.setMsg("label.success");
                    result.setObj(tag);
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
