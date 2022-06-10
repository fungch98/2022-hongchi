/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Alex
 */
public class CategoryDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<CategoryInfo> loadCategoryList(int size) throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        List<CategoryInfo> result=null;
        try {
            query = session.getNamedQuery("CategoryInfo.findAll");
            if(size>0){
                query.setMaxResults(size);
            }
            result = (List<CategoryInfo>) query.list();
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
 }
