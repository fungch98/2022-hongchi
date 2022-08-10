/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.handler.CommonHandler;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.EditorItem;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Alex
 */
public class EditorDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public ProductInfo loadEditorProduct(String uuid, UserInfo user)throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        ProductInfo result=null;
        CommonHandler common=new CommonHandler();
        try{
            if(uuid!=null && uuid.equalsIgnoreCase("new")){
                result=new ProductInfo();
                result.setCreateDate(common.getLocalTime());
                result.setCreateUser(user);
                result.setModifyDate(common.getLocalTime());
                result.setModifyUser(user);
                result.setUuid(uuid);
                result.setDesc("");
                result.setEditorUuid("");
                result.setName("");
                result.setProductCat("");
                result.setProductCreateMethod(ProductInfo.EDITOR);
                result.setProductFileName("");
                result.setProductRef(-1);
                result.setProductSrc("");
                result.setProductTag("");
                result.setProductUUID("");
                result.setProductUrl("");
                result.setSearchKey("");
                result.setStatus(0);
                
            }else{
                query=session.getNamedQuery("ProductInfo.findByUuid");
                query.setString("uuid", uuid);
                result=(ProductInfo)query.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public EditorInfo loadEditor(String uuid,ProductInfo prod,  UserInfo user, boolean isSaveAction)throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        EditorInfo result=null;
        EditorItem bg=null;
        List<EditorItem> itemList=null;
        CommonHandler common=new CommonHandler();
        try{
            if(prod!=null && user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    result=new EditorInfo();
                    result.setCreateDate(common.getLocalTime());
                    result.setCreateUser(user);
                    result.setModifyDate(common.getLocalTime());
                    result.setModifyUser(user);
                    result.setFileAbsSrc("");
                    result.setName(prod.getName());
                    result.setProdId(prod);
                    result.setStatus(1);
                    result.setUrl("");
                    result.setUuid(uuid);
                    
                    if(!isSaveAction){
                        itemList=new ArrayList<EditorItem>();
                        
                        //while(itemList.size()<3){
                            bg=new EditorItem();
                            bg.setBgColor("FFFFFF");
                            bg.setColor("");
                            bg.setHeight((double)0);
                            bg.setImgSrc("");
                            bg.setImgUploadSrc("");
                            bg.setImgUploadUuid("");
                            bg.setImgUrl("");
                            bg.setImgUuid("");
                            bg.setItemType("bg");
                            bg.setModifyDate(common.getLocalTime());
                            bg.setModifyUser(user);
                            bg.setOpacity((double)1);
                            bg.setPosX((double)0);
                            bg.setPosY((double)0);
                            bg.setSeq(0);
                            bg.setText("");
                            bg.setTextDesc("");
                            bg.setUuid(uuid+itemList.size());
                            bg.setWidth((double)600);
                            bg.setZIndex(0);

                            itemList.add(bg);
                        //}
                        result.setEditorItemList(itemList);
                                
                    }
                    
                }else{
                    query=session.getNamedQuery("EditorInfo.findByUuid");
                    query.setString("uuid", uuid);
                    result=(EditorInfo)query.uniqueResult();
                }
            }
            
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public EditorItem addItem(String type, String target)throws Exception{
        Session session = sessionFactory.openSession();
        Query query = null;
        EditorItem result=null;
        CommonHandler common=new CommonHandler();
        String uuid="";
        ProductInfo photo=null;
        try{
            type=(type!=null?type.toLowerCase():"");
            if(type.equalsIgnoreCase("bg") || type.equalsIgnoreCase("photo")
                    || type.equalsIgnoreCase("upload") || type.equalsIgnoreCase("text") ){
                uuid="new-"+common.generateUUID();
                
                result=new EditorItem();
                result.setBgColor("");
                result.setColor("");
                result.setHeight((double)0);
                result.setImgSrc("");
                result.setImgUploadSrc("");
                result.setImgUploadUuid("");
                result.setImgUrl("");
                result.setImgUuid("");
                result.setItemType(type);
                result.setModifyDate(common.getLocalTime());
                result.setModifyUser(null);
                result.setOpacity((double)1);
                result.setPosX((double)0);
                result.setPosY((double)0);
                result.setSeq(0);
                result.setText("");
                result.setTextDesc("");
                result.setUuid(uuid);
                result.setWidth((double)600);
                result.setZIndex(0);
                
                if(type.equalsIgnoreCase("photo")){
                    query=session.getNamedQuery("ProductInfo.findByUuid");
                    query.setString("uuid", target);
                    photo=(ProductInfo)query.uniqueResult();
                    System.out.println("Photo UUID: "+photo+":"+target);
                    if(photo!=null){
                        result.setImgSrc(photo.getProductSrc());
                        result.setImgUrl(photo.getProductUrl());
                        result.setImgUuid(target);
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
}
