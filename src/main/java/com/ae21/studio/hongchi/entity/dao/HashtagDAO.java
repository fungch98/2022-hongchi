/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    
    
}
