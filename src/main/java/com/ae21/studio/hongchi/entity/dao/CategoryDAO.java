/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import java.util.List;
import java.util.regex.Pattern;
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
    
    public CategoryInfo loadCategory(String uuid) throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        CategoryInfo result=null;
        try {
            if(uuid!=null && uuid.equalsIgnoreCase("new")){
                result=new CategoryInfo();
                result.setUuid(uuid);
                result.setDesc("");
                result.setName("");
                result.setUrl("");
                result.setSeq(99);
            }else{
                query = session.getNamedQuery("CategoryInfo.findByUuid");
                query.setString("uuid", uuid);
                result = (CategoryInfo) query.uniqueResult();
            }
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
    
    public ResultBean saveCategory(HttpServletRequest request,String uuid,  UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
        String name=request.getParameter("name");
        String desc=request.getParameter("desc");
        String url=request.getParameter("url");
        System.out.println("Input: "+desc+":"+url+":"+name);
        CategoryInfo category=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null && user.getIsAdmin()==1){
                category=this.loadCategory(uuid);
                
                desc=(desc!=null?desc.trim():"");
                
                if(category!=null){
                    category.setDesc(desc);
                    category.setName(name);
                    
                    if(name!=null && !name.isEmpty()){
                        category.setName(name.trim());
                    }else{
                        result.setCode(-3001);
                        result.setMsg("ERROR.CAT.NAME.EMPTY");
                    }
                    
                    
                    
                    
                    try{
                        url=(url!=null?url.trim():"");
                        category.setUrl(url);
                        
                        if (!url.isEmpty() && Pattern.matches("^[-a-zA-Z0-9]+$", url)) {
                        
                            sql="Select {c.*} from category_info c where c.url=:url and id!=:id ";
                            squery=session.createSQLQuery(sql);
                            squery.addEntity("c", CategoryInfo.class);
                            squery.setString("url", url);
                            squery.setInteger("id", (category.getId()!=null?category.getId():0));
                            List temp=squery.list();
                            
                            if(temp!=null && temp.size()>0){
                                result.setCode(-3011);
                                result.setMsg("ERROR.CAT.URL.UNIQUE");
                            }
                        }else{
                            result.setCode(-3012);
                            result.setMsg("ERROR.CAT.URL.PATTERN");
                        }
                    }catch(Exception ingore){
                        result.setCode(-3013);
                        result.setMsg("ERROR.CAT.NAME.EMPTY");
                    }
                    
                    result.setObj(category);
                    
                }else{
                    result.setCode(-2001);
                    result.setMsg("ERROR.NOFOUND");
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
            if(result.getCode()==0){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    category.setUuid(lib.generateUUID());
                }
                tx=session.beginTransaction();
                session.saveOrUpdate(category);
                tx.commit();
                
                result.setCode(1);
                result.setObj(category);
                result.setMsg("label.success");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
            result=new ResultBean();
            result.setCode(-9999);
            result.setMsg("ERROR.NULL");
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
 }
