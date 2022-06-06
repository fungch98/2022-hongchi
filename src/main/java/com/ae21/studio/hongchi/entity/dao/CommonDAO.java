/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.studio.hongchi.entity.bean.ParaInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Alex
 */
public class CommonDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public ParaInfo loadPara(int id)throws Exception{
        ParaInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try {
            query = session.getNamedQuery("ParaInfo.findById");
            query.setInteger("id", id);
            result = (ParaInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public ParaInfo loadPara(String uuid)throws Exception{
        ParaInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try {
            query = session.getNamedQuery("ParaInfo.findById");
            query.setString("uuid", uuid);
            result = (ParaInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<ParaInfo> getParaList(String code, String subcode, int size)throws Exception{
        List<ParaInfo> result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try {
            query = session.getNamedQuery("ParaInfo.findByCode");
            query.setString("code", code);
            query.setString("subcode", subcode);
            if(size>0){
                query.setMaxResults(size);
            }
            result = (List<ParaInfo>) query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public ParaInfo loadParaVal(String code, String subcode, int value)throws Exception{
        ParaInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try {
            query = session.getNamedQuery("ParaInfo.findByVal");
            query.setString("code", code);
            query.setString("subcode", subcode);
            query.setInteger("value", value);
            result = (ParaInfo) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
}
