
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.FamilyBean;
import com.ae21.studio.hongchi.entity.system.SearchBean;
import java.util.ArrayList;
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
    
    public CategoryInfo loadCatURL(String url) throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        String sql="";
        CategoryInfo result=null;
        try{
            if(url!=null){
            sql="Select {c.*} from category_info c where c.url=:url ";
            squery = session.createSQLQuery(sql);
            squery.addEntity("c", CategoryInfo.class);
            squery.setString("url", url);
            result = (CategoryInfo) squery.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public CategoryInfo loadCategory(String uuid) throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        String sql="";
        CategoryInfo result=null;
        try {
            if(uuid!=null && uuid.equalsIgnoreCase("new")){
                result=new CategoryInfo();
                result.setUuid(uuid);
                result.setDesc("");
                result.setName("");
                result.setUrl("");
                result.setSeq(99);
                result.setFamilyId(0);
                result.setParentId(0);
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
    
     public boolean isEmptyFolder(CategoryInfo current) throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        String sql="";
        boolean result=false;
        List temp=null;
        try{
            if(current!=null){
                sql="SELECT 1 from category_info c where c.parent_id=:id ";
                squery=session.createSQLQuery(sql);
                squery.setInteger("id", current.getId());
                temp=squery.list();
                
                if(temp!=null && temp.size()>0){
                    System.out.println("Check Empty "+result+temp);
                    return false;
                }else{
                    result=true;
                }
                
                sql="SELECT 1 from product_category c where c.category_id=:id ";
                squery=session.createSQLQuery(sql);
                squery.setInteger("id", current.getId());
                temp=squery.list();
                
                if(temp!=null && temp.size()>0){
                    return false;
                }else{
                    result=true;
                }
                
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {}
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
        String parentUUID=request.getParameter("parentUUID");
        //System.out.println("Input: "+desc+":"+url+":"+name);
        CategoryInfo category=null;
        CategoryInfo parent=null;
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
                        
                        
                        try{
                            if(parentUUID!=null){
                                if(parentUUID.equalsIgnoreCase("root")){
                                    parent=new CategoryInfo();
                                    parent.setId(0);
                                    parent.setFamilyId(0);
                                }else{
                                    parent=this.loadCategory(parentUUID);

                                }
                            }
                            
                            if(parent!=null){
                                category.setFamilyId(parent.getFamilyId());
                                category.setParentId(parent.getId());
                            }else{
                                result.setCode(-3021);
                                result.setMsg("ERROR.CAT.URL.PATTERN");
                            }
                        }catch(Exception ignore){
                            ignore.printStackTrace();
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
    
    public ResultBean saveFolder(HttpServletRequest request,String uuid,String parentURL, UserInfo user)throws Exception{
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
        //System.out.println("Input: "+desc+":"+url+":"+name);
        CategoryInfo category=null;
        CategoryInfo parent=null;
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
                        url=(url!=null && !url.isEmpty()?url.trim():lib.generateUUID());
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
                        
                        
                        try{
                            if(parentURL!=null && parentURL.equalsIgnoreCase("root")){
                                parent=new CategoryInfo();
                                parent.setId(0);
                                parent.setFamilyId(0);
                                parent.setParentId(0);
                                parent.setUuid("root");
                                parent.setUrl("root");
                                parent.setName("主目錄");
                            }else{
                                parent=this.loadCatURL(parentURL);
                            }
               
                            
                            if(parent!=null){
                                if(parent.getId()!=null && parent.getId().intValue()==0){  //if parent is root
                                    if(category.getId()!=null && category.getId().intValue()>0){
                                        category.setFamilyId(category.getId());  
                                    }else{
                                        category.setFamilyId(0);  
                                    }
                                    
                                }else if(parent.getId()!=null && parent.getId().intValue()>0 && parent.getFamilyId().intValue()==0){ //Level One
                                    category.setFamilyId(parent.getId());
                                }else{
                                    category.setFamilyId(parent.getFamilyId());
                                }
                                
                                category.setParentId(parent.getId());
                            }else{
                                result.setCode(-3021);
                                result.setMsg("ERROR.CAT.URL.PATTERN");
                            }
                        }catch(Exception ignore){
                            ignore.printStackTrace();
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
                if(category.getFamilyId()==0 && category.getParentId()==0){
                    category.setFamilyId(category.getId());
                }
                
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
    
    public ResultBean delFolder(HttpServletRequest request,String uuid,String parentURL, UserInfo user)throws Exception{
        ResultBean result=new ResultBean();
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        String sql="";
        
       
        //System.out.println("Input: "+desc+":"+url+":"+name);
        CategoryInfo category=null;
        CategoryInfo parent=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            if(user!=null && user.getIsAdmin()==1){
                category=this.loadCategory(uuid);
                
                
              
                
                if(category!=null  && category.getUuid()!=null && !category.getUuid().equalsIgnoreCase("new")){
                   
                        System.out.println("Del: "+category.getName());
                        result.setObj(category);
                        try{
                            if(parentURL!=null && parentURL.equalsIgnoreCase("root")){
                                parent=new CategoryInfo();
                                parent.setId(0);
                                parent.setFamilyId(0);
                                parent.setParentId(0);
                                parent.setUuid("root");
                                parent.setUrl("root");
                                parent.setName("主目錄");
                            }else{
                                parent=this.loadCatURL(parentURL);
                            }
               
                            
                            if(parent!=null){
                                
                            }else{
                                result.setCode(-3021);
                                result.setMsg("ERROR.CAT.URL.PATTERN");
                            }
                        }catch(Exception ignore){
                            ignore.printStackTrace();
                        }
                   
                    
                        if(!this.isEmptyFolder(parent)){
                            result.setCode(-3031);
                                result.setMsg("ERROR.DEL.FOLDER.EMPTY");
                        }
                    
                }else{
                    result.setCode(-2001);
                    result.setMsg("ERROR.NOFOUND");
                }
                
            }else{
                result.setCode(-1001);
                result.setMsg("ERROR.ACCESS");
            }
            
            if(result.getCode()==0){
                
                tx=session.beginTransaction();
                session.delete(category);
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
    
    public List<ProductInfo> loadProductbyFolder(CategoryInfo current, UserInfo user)throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        CategoryInfo temp=null;
        String sql="";
        List<ProductInfo> result=null;
        try{
            if(current!=null){
                sql="SELECT {p.*} FROM product_category pc left join product_info p on pc.product_id=p.id WHERE pc.category_id=:cat AND p.prod_status>0 "
                        + (user!=null && user.getIsAdmin()==1?"  ":" AND (p.is_share=1 || p.create_user=:user) ") 
                        + " ORDER BY p.modify_date DESC, p.name ";
                squery=session.createSQLQuery(sql);
                squery.addEntity("p", ProductInfo.class);
                squery.setInteger("cat", current.getId());
                if(user!=null && user.getIsAdmin()==1){
                    
                }else{
                    squery.setInteger("user", user.getId());
                }
                
                result=(List<ProductInfo>)squery.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public FamilyBean initFamilyTree(CategoryInfo current)throws Exception{
        FamilyBean result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler lib = new CommonHandler();
        CategoryInfo temp=null;
        String sql="";
        ArrayList<CategoryInfo> path=null;
        List<CategoryInfo> subfolder=null;
        try{
            if(current!=null){
                result=new FamilyBean(current);
                System.out.println(current.getName()+":"+current.getParentId());
                if(result.getCurrent()!=null && result.getCurrent().getParentId()!=0){  //parent Id is zero, it is the root folder
                    query=session.getNamedQuery("CategoryInfo.findById");
                    query.setInteger("id", current.getParentId());
                    temp=(CategoryInfo)query.uniqueResult();

                    if(temp!=null){
                        System.out.println("Exist: "+temp.getId());
                        result.setParent(temp);
                        
                        path=new ArrayList<CategoryInfo>();
                        path.add(0, current);
                        path.add(0, temp);
                        
                        while(temp!=null && temp.getParentId().intValue()>0){
                            query.setInteger("id", temp.getParentId());
                            temp=(CategoryInfo)query.uniqueResult();
                            
                            if(temp!=null){
                                path.add(0, temp);
                            }
                        }

                    }else{ //Provide link for the root 
                        
                        temp=new CategoryInfo();
                        temp.setUrl("root");
                        temp.setUuid("root");
                        result.setParent(temp);
                         System.out.println("Not Exist: "+temp.getUrl());
                    }
                    
                    
                }else{
                    path=new ArrayList<CategoryInfo>();
                    path.add(current);
                    
                    temp=new CategoryInfo();
                    temp.setUrl("root");
                    temp.setUuid("root");
                    result.setParent(temp);
                }
                
                if(path!=null){
                    result.setPath(path);
                }
                
                //Find SubFolder
                if(current.getId()!=null){  //Curent object is not a new object, ID must be exist
                    sql="Select {c.*} from category_info c where c.parent_id=:parent order by c.seq, c.name ";
                    squery=session.createSQLQuery(sql);
                    squery.addEntity("c", CategoryInfo.class);
                    //System.out.println("Parent: "+current.getId());
                    squery.setInteger("parent", current.getId());
                    subfolder=(List<CategoryInfo>)squery.list();
                    
                    
                    if(subfolder!=null && subfolder.size()>0){
                        result.setHasSubfolder(true);
                        result.setSubFolder(subfolder);
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //try {if (tx != null) {tx.rollback();}} catch (Exception ex) {ex.printStackTrace();}
           
        } finally {
            try {session.close();} catch (Exception ignore) {}
        }
        return result;
    }
    
    public SearchBean searchProduct(FamilyBean family,  int size, int max, UserInfo user) throws Exception{
        SearchBean search=null;
        double needPage=0;
        try{
            if(family!=null && family.getCurrent()!=null){
                search=new SearchBean();
                search.setKey("");
                search.setCurPage(0);
                search.setPageItems(max);
                search.setResultList(this.loadProductbyFolder(family.getCurrent(), user));
                search.generatePageList();
                search.setFamily(family);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return search;
    }
 }
