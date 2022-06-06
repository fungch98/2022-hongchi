/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
