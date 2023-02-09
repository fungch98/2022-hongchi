/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.dao;

import com.ae21.bean.ResultBean;
import com.ae21.bean.SystemConfigBean;
import com.ae21.config.SystemConfig;
import com.ae21.handler.CommonHandler;
import com.ae21.handler.ImageHandler;
import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.EditorItem;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ParaInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.CustImageHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
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
public class EditorDAO {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public EditorInfo loadEditor(String uuid)throws Exception{
        EditorInfo result=null;
        Session session = sessionFactory.openSession();
        Query query = null;
        try{
            query=session.getNamedQuery("EditorInfo.findByUuid");
            query.setString("uuid", uuid);
            result=(EditorInfo)query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<ProductInfo> loadRoleDetail(HttpServletRequest request, int key)throws Exception{
        List<ProductInfo> result=null;
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        String sql="";
        List temp=null;
        try{
            sql="SELECT {p.*} FROM para_info i LEFT JOIN product_info p ON p.id=i.value "
                    + "WHERE i.code='CHAR' AND i.subcode='DETAIL' AND i.dd01=:id AND p.id>0 "
                    + "ORDER BY i.seq ";
            //sql="SELECT {p.*} FROM para_info i LEFT JOIN product_info prod ON p.id=i.value WHERE i.code='CHAR' AND i.subcode='DETAIL' AND i.dd01=5 ORDER BY i.seq";
            query=session.createSQLQuery(sql);
            query.addEntity("p", ProductInfo.class);
            query.setInteger("id", key);
            result=(List<ProductInfo>)query.list();
            
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
    }
    
    public List<EditorItem> loadEditorItem(EditorInfo editor, boolean isDisplay)throws Exception{
        List<EditorItem> result=null;
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        try{
            if(editor!=null ){
                if(editor.getId()!=null){
                    query=session.createSQLQuery("Select {i.*} from editor_item i where i.editor_id=:id ORDER by i.z_index "+(isDisplay?" desc ":""));
                    query.addEntity("i", EditorItem.class);
                    query.setInteger("id", editor.getId());
                    result=(List<EditorItem>)query.list();
                }else{
                    result=editor.getEditorItemList();
                }
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {session.close();} catch (Exception ignore) {            }
        }
        return result;
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
                            bg.setHeight((double)450);
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
                            bg.setName("");
                            bg.setFontName("");
                            bg.setFontSize(0);
                            bg.setRotate((double)0);

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
        SQLQuery squery=null;
        EditorItem result=null;
        CommonHandler common=new CommonHandler();
        String uuid="";
        String sql="";
        ProductInfo photo=null;
        ParaInfo para=null;
        try{
            type=(type!=null?type.toLowerCase():"");
            if(type.equalsIgnoreCase("bg") || type.equalsIgnoreCase("photo")
                    || type.equalsIgnoreCase("upload") || type.equalsIgnoreCase("text") 
                    || type.equalsIgnoreCase("material") || type.equalsIgnoreCase("role") ){
                uuid="new-"+common.generateUUID();
                
                result=new EditorItem();
                
                
                
                result.setHeight((double)450);
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
                result.setName("");
                
                result.setRotate((double)0);
                result.setTextAlign("left");
                result.setTextBold(0);
                result.setTextItalic(0);
                result.setFontName("");
                result.setFontSize(14);
                result.setIsHidden(0);
                result.setTextUnder(0);
                result.setIsFilp(0);
                
                if(type.equalsIgnoreCase("text")){
                    result.setColor("#000000");
                    result.setBgColor("#FFFFFF");
                    result.setOpacity((double)0);
                    result.setFontName("arial");
                    result.setFontSize(14);
                    result.setWidth((double)200);
                    result.setHeight((double)50);
                }else if(type.equalsIgnoreCase("role")){
                    result.setWidth((double)200);
                }else{
                    result.setBgColor("");
                    result.setColor("");
                    result.setFontName("");
                    result.setFontSize(0);
                }
                
                if(type.equalsIgnoreCase("photo")){
                    query=session.getNamedQuery("ProductInfo.findByUuid");
                    query.setString("uuid", target);
                    photo=(ProductInfo)query.uniqueResult();
                    //System.out.println("Photo UUID: "+photo+":"+target);
                    if(photo!=null){
                        result.setImgSrc(photo.getProductSrc());
                        result.setImgUrl(photo.getProductUrl());
                        result.setImgUuid(target);
                        result.setName(photo.getName());
                    }
                    
                }else if(type.equalsIgnoreCase("material") || type.equalsIgnoreCase("role")){
                    try{
                        sql="SELECT p.* FROM para_info p "
                                + " WHERE para_status=1 AND CODE='EDITOR' AND subcode=:subcode "
                                + " AND id=:id ORDER BY seq ";
                        squery=session.createSQLQuery(sql);
                        squery.addEntity("p", ParaInfo.class);
                        squery.setInteger("id", Integer.parseInt(target));
                        squery.setString("subcode", (type.equalsIgnoreCase("role")?"CHAR":"OBJ"));
                        
                        para=(ParaInfo)squery.uniqueResult();
                        if(para!=null){
                            //System.out.println(type+":"+para.getStr01());
                            if(type.equalsIgnoreCase("role")){
                                result.setImgSrc("images/"+type+"/emotion/"+para.getStr03()+para.getUrl()+".png");
                                result.setImgUrl("images/"+type+"/emotion/"+para.getStr03()+para.getUrl()+".png");
                                result.setImgUploadSrc("/images/"+type+"/emotion/"+para.getStr03());
                                result.setImgUploadUuid(para.getStr03()+para.getUrl());
                            }else{
                                result.setImgSrc("images/"+type+"/"+para.getStr02());
                                result.setImgUrl("images/"+type+"/"+para.getStr02());
                            }
                            
                            result.setImgUuid(target);
                            result.setName(para.getStr01());
                        }
                    }catch(Exception ignore){
                        
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
    
    public ResultBean save(HttpServletRequest request,String uuid,  UserInfo user, ProdDAO prodDAO)throws Exception{
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler common = new CommonHandler();
        ResultBean result=new ResultBean();
        String sql="";
        
        String posX[]=request.getParameterValues("posx");
        String posY[]=request.getParameterValues("posy");
        String width[]=request.getParameterValues("width");
        String height[]=request.getParameterValues("height");
        String name[]=request.getParameterValues("name");
        String color[]=request.getParameterValues("color");
        String itemType[]=request.getParameterValues("itemType");
        String itemUUID[]=request.getParameterValues("uuid");
        String seq[]=request.getParameterValues("seq");
        String bgColor[]=request.getParameterValues("bg-color");
        String imgSrc[]=request.getParameterValues("imgSrc");
        String upSrc[]=request.getParameterValues("upSrc");
        String upUUID[]=request.getParameterValues("upUUID");
        String imgURL[]=request.getParameterValues("imgURL");
        String imgUUID[]=request.getParameterValues("imgUUID");
        String text[]=request.getParameterValues("text");
        String textDesc[]=request.getParameterValues("textDesc");
        String zIndex[]=request.getParameterValues("zIndex");
        String editorName=request.getParameter("photo-name");
        String editorDesc=request.getParameter("photo-desc");
        String fontName[]=request.getParameterValues("fontName");
        String fontSize[]=request.getParameterValues("fontSize");
        String rotate[]=request.getParameterValues("rotate");
        
        String textAlign[]=request.getParameterValues("textAlign");
        String textBold[]=request.getParameterValues("textBold");
        String textItalic[]=request.getParameterValues("textItalic");
        
        String folderURL=request.getParameter("photo-info-folder");
        
        String isShareVal=request.getParameter("isShare");
        String hashtagVal=request.getParameter("hashtag");
        String folderVal=request.getParameter("folder");
        
        String textUnder[]=request.getParameterValues("textUnder");
        String isHidden[]=request.getParameterValues("isHidden");
        String isFilp[]=request.getParameterValues("isFilp");
        
        EditorInfo editor=null;
        EditorItem item=null;
        ProductInfo photo=null;
        CategoryInfo folder=null;
        boolean needAddFolder=false;
        
        
        List<EditorItem> itemList=new ArrayList<EditorItem>();
         List<HashtagInfo> tagList=null;
          List<CategoryInfo> catList=null;
        
        String colorCode="";
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            editorName=(editorName!=null?editorName.trim():"");
            editorDesc=(editorDesc!=null?editorDesc.trim():"");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    //System.out.println("Editor Create");
                    
                    photo=new ProductInfo();
                    photo.setCreateDate(common.getLocalTime());
                    photo.setCreateUser(user);
                    photo.setStatus(1);
                    photo.setUuid(uuid);
                    photo.setSearchKey("");
                    photo.setProductCreateMethod(ProductInfo.EDITOR);
                    photo.setProductFileName("");
                    photo.setProductRef(0);
                    photo.setProductSrc("");
                    photo.setProductUrl("");
                    photo.setProductCat("");
                    photo.setProductTag("");
                    photo.setIsShare(0);
                    
                    
                    
                    editor=new EditorInfo();
                    editor.setCreateDate(common.getLocalTime());
                    editor.setCreateUser(user);
                    editor.setFileAbsSrc("");
                    editor.setStatus(1);
                    editor.setUrl("");
                    editor.setUuid(uuid);
                    
                    
                }else{
                    editor=this.loadEditor(uuid);
                    if(editor!=null){
                        photo=editor.getProdId();
                    }
                    
                }
                
                if(editor!=null){
                    editor.setModifyDate(common.getLocalTime());
                    editor.setModifyUser(user);
                    photo.setModifyDate(common.getLocalTime());
                    photo.setModifyUser(user);
                    photo.setDesc(editorDesc);
                    photo.setName(editorName);
                    editor.setName(editorName);
                    editor.setEditorDesc(editorDesc);
                    
                    
                    for(int i=0; itemType!=null && i<itemType.length;i++){
                        try{
                            item=new EditorItem();
                            
                            colorCode=(bgColor!=null && bgColor.length>=i?bgColor[i]:"");
                            item.setBgColor("");
                            item.setOpacity((double)0);
                            //System.out.println("Color("+itemType[i]+"): "+colorCode);
                            if(colorCode!=null && colorCode.length()>=7){
                                item.setBgColor(colorCode.substring(0,7));
                            }
                            
                            if(colorCode!=null && colorCode.length()>=9){
                                item.setOpacityVal(colorCode.substring(7));
                            }
                            
                            //System.out.println("Color("+i+"): "+color.length+" val:"+color[i]);
                            item.setColor((color!=null && color.length>=i?color[i]:""));
                            item.setHeight((height!=null && height.length>=i?Double.parseDouble(height[i]):0));
                            item.setImgSrc((imgSrc!=null && imgSrc.length>=i?imgSrc[i]:""));
                            item.setImgUploadSrc((upSrc!=null && upSrc.length>=i?upSrc[i]:""));
                            item.setImgUploadUuid((upUUID!=null && upUUID.length>=i?upUUID[i]:""));
                            item.setImgUrl((imgURL!=null && imgURL.length>=i?imgURL[i]:""));
                            item.setImgUuid((imgUUID!=null && imgUUID.length>=i?imgUUID[i]:""));
                            item.setItemType((itemType!=null && itemType.length>=i?itemType[i]:""));
                            item.setModifyDate(common.getLocalTime());
                            item.setModifyUser(user);
                            
                            
                            item.setPosX((posX!=null && posX.length>=i?Double.parseDouble(posX[i]):0));
                            item.setPosY((posY!=null && posY.length>=i?Double.parseDouble(posY[i]):0));
                            item.setSeq((seq!=null && seq.length>=i?Integer.parseInt(seq[i]):0));
                            item.setText((textDesc!=null && textDesc.length>=i?textDesc[i]:""));
                            item.setTextDesc((text!=null && text.length>=i?text[i]:""));
                            
                            if(item.getItemType()!=null && item.getItemType().equalsIgnoreCase("text")){
                                if(item.getText()!=null && item.getText().length()>10){
                                    item.setName(item.getTextDesc().substring(0, 10));
                                }else{
                                    item.setName(item.getTextDesc());
                                }
                                
                            }else{
                                item.setName((name!=null && name.length>=i?name[i]:""));
                            }
                            
                            //System.out.println("("+i+")"+text[i]);
                            item.setUuid(common.generateUUID());
                            item.setWidth((width!=null && width.length>=i?Double.parseDouble(width[i]):0));
                            item.setZIndex((zIndex!=null && zIndex.length>=i?Integer.parseInt(zIndex[i]):0));
                            item.setFontSize((fontSize!=null && fontSize.length>=i?Integer.parseInt(fontSize[i]):0));
                            item.setFontName((fontName!=null && fontName.length>=i?fontName[i]:""));
                            item.setRotate((rotate!=null && rotate.length>=i?Double.parseDouble(rotate[i]):0));
                            
                            item.setTextAlign((textAlign!=null && textAlign.length>=i?textAlign[i]:""));
                            try{
                                item.setTextBold((textBold!=null && textBold[i]!=null && textBold[i].equalsIgnoreCase("1")?1:0));
                                item.setTextItalic((textItalic!=null && textItalic[i]!=null && textItalic[i].equalsIgnoreCase("1")?1:0));
                                item.setTextUnder((textUnder!=null && textUnder[i]!=null && textUnder[i].equalsIgnoreCase("1")?1:0));
                                //System.out.println(item.getName()+":"+item.getTextUnder());
                                item.setIsHidden((isHidden!=null && isHidden[i]!=null && isHidden[i].equalsIgnoreCase("1")?1:0));
                                item.setIsFilp((isFilp!=null && isFilp[i]!=null && isFilp[i].equalsIgnoreCase("1")?1:0));
                            }catch(Exception ignore){
                                ignore.printStackTrace();
                            }
                            
                            itemList.add(item);
                        }catch(Exception ingore){
                            ingore.printStackTrace();
                            result.setCode(-1002);
                            result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                        }
                        
                    }
                    //System.out.println(itemList.size()+":"+itemType.length);
                        if(itemList.size()!=itemType.length){  //Miss Some Item value
                            result.setCode(-1003);
                            result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                        }
                        
                        //If admin, they can select the folder and allow share to public 
                        //If normal user, they can save at personal folder and private only
                        
                        if(user!=null && user.getIsAdmin()==1){ 
                            try{
                                int isShare=Integer.parseInt(isShareVal);
                                photo.setIsShare(isShare);

                            }catch(Exception ingore){
                                ingore.printStackTrace();
                                result.setCode(-2103);
                                result.setMsg("ERROR.SHARE.INVALID");
                            }
                            
                            try{
                                    if(folderVal!=null){
                                         query=session.getNamedQuery("CategoryInfo.findByUuid");
                                         query.setString("uuid", folderVal);
                                         folder=(CategoryInfo)query.uniqueResult();

                                         
                                    }

                                }catch(Exception ignore){
                                    result.setCode(-2103);
                                    result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                                }
                        }else{
                            try{
                                if(photo!=null && photo.getId()!=null){
                                    sql="SELECT {c.*} FROM product_category pc LEFT JOIN category_info c ON c.id=pc.category_id WHERE pc.product_id=:prod LIMIT 1";
                                    squery=session.createSQLQuery(sql);
                                    squery.addEntity("c", CategoryInfo.class);
                                    squery.setInteger("prod", photo.getId());
                                    folder=(CategoryInfo)squery.uniqueResult();
                                }
                                
                                if(folder==null){                                
                                    sql="SELECT {c.*} FROM category_info c LEFT JOIN para_info p ON p.value=c.id WHERE p.code='FOLDER' AND p.subcode='PERSONAL' AND p.para_status=1 ";
                                    squery=session.createSQLQuery(sql);
                                    squery.addEntity("c", CategoryInfo.class);
                                    folder=(CategoryInfo)squery.uniqueResult();
                                }
                                
                               System.out.println("Folder ("+photo.getId()+"): "+(folder!=null?folder.getName():"NULL"));
                            }catch(Exception ignore){
                                result.setCode(-2114);
                                    result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                            }
                        }
                        
                         try{
                             if(prodDAO!=null){
                                tagList=prodDAO.strToHashtag(hashtagVal, user, session);

                             }else{
                                  result.setCode(-1101);
                                 result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                             }
                        }catch(Exception ignore){
                            result.setCode(-1101);
                            result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                        }
                         
                         
                         if(folder!=null){
                             String folderProductCat="";
                             catList=new ArrayList<CategoryInfo>();
                             catList.add(folder);
                             folderProductCat+="#"+folder.getName()+" ";
                              photo.setProductCat(folderProductCat);

                         }else{
                             result.setCode(-20031);
                             result.setMsg("ERROR.PHOTO.UPLOAD.CAT");
                         }
                                        
                          
                          
                          
                        
                        /*
                        if(folderURL!=null && !folderURL.isEmpty()){
                            sql="Select {c.*} from category_info c where c.url=:url ";
                            squery=session.createSQLQuery(sql);
                            squery.addEntity("c", CategoryInfo.class);
                            squery.setString("url", folderURL);
                            folder=(CategoryInfo)squery.uniqueResult();
                            
                            if(folder!=null){
                                photo.setProductCat("#"+folder.getName());
                                photo.setDefaultFolder(folderURL);
                                
                                
                                needAddFolder=false;
                            }
                        }
                        */
                }
                
                if(result.getCode()==0){
                    System.out.println("Editor: "+uuid);
                    if(uuid!=null && uuid.equalsIgnoreCase("new")){
                        photo.setUuid(common.generateUUID());
                        editor.setUuid(common.generateUUID());
                        photo.setProductUUID(editor.getUuid());
                    }
                    
                    tx=session.beginTransaction();
                    session.saveOrUpdate(photo);
                    editor.setProdId(photo);
                    
                    /*
                    if(folder!=null && folder.getId()!=null){
                        sql="DELETE from product_category where product_id=:prod and category_id=:cat ";
                        squery=session.createSQLQuery(sql);
                        squery.setInteger("prod", photo.getId());
                        squery.setInteger("cat", folder.getId());
                        squery.executeUpdate();
                        
                        sql="INSERT INTO product_category(`product_id`,`category_id`) VALUES (:prod,:cat)";
                        squery=session.createSQLQuery(sql);
                        squery.setInteger("prod", photo.getId());
                        squery.setInteger("cat", folder.getId());
                        squery.executeUpdate();
                    }
                    */
                    session.saveOrUpdate(editor);
                    
                    sql="Delete from editor_item where editor_id=:id ";
                    squery=session.createSQLQuery(sql);
                    squery.setInteger("id", editor.getId());
                    squery.executeUpdate();
                    
                    for(int i=0; itemList!=null && i<itemList.size();i++){
                        item=itemList.get(i);
                        item.setEditorId(editor);
                        session.saveOrUpdate(item);
                    }
                    
                    if(prodDAO!=null){
                        prodDAO.saveHashtagAction(session, tagList, photo);
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
                    
                    
                    tx.commit();
                    result.setObj(editor);
                    result.setCode(1);
                    result.setMsg("label.success");
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
    
    public ResultBean generatePhoto(EditorInfo editor, UserInfo user, SystemConfigBean config)throws Exception{
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        Query query = null;
        SQLQuery squery=null;
        CommonHandler common = new CommonHandler();
        CustImageHandler imgHandler=new CustImageHandler();
        ResultBean result=new ResultBean();
        String sql="";
        List<EditorItem> itemList=null;
        
        EditorItem item=null;
        ProductInfo prod=null;
        
        ArrayList<BufferedImage> imgList=new ArrayList<BufferedImage>();
        BufferedImage image=null;
        BufferedImage product=null;
        
        int w=0, h=0;
        int newW=0, newH=0;
        double scale=0;
        double scale2=0;
        int widthOfImage = 0;
        int heightOfImage = 0;
        
        Graphics2D g=null;
        File photo=null;
        File oPreview=null;
        File oProduct=null;
        String outoutPath="";
        String path="";
        Color color=null;
        
        float opacity=1f;
        int scaleAll=(1200/600);
        int nW=0;
        int nH=0;
        int newX=0;
        int newY=0;
        double sw=0;
        double sh=0;
        
        double cx = 0;
        double cy = 0;
        
        int alpha=0;
        int fontStyle=0;
        
        AffineTransform oldAT=null;
         Rectangle rect2=null;
         Font font=null;
         AlphaComposite alphaChannel=null;
         
         Map fontAttributes=null;
         
         AffineTransform txFilp=null;
         AffineTransformOp op=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            //System.out.println("Start to Generate Photo"+editor.getId());
            if(editor!=null && editor.getId()!=null && user!=null && config!=null){
                itemList=this.loadEditorItem(editor, false);
                product  = new BufferedImage(600*scaleAll, 450*scaleAll, BufferedImage.TYPE_INT_ARGB);
                //background= new BufferedImage(1200, 900, BufferedImage.TYPE_INT_RGB);
                g=product.createGraphics();
                g.setComposite(AlphaComposite.Clear);
                g.fillRect(0, 0, product.getWidth(), product.getHeight());
                oldAT = g.getTransform();
                for(int i=0; itemList!=null && i<itemList.size();i++){
                    item=itemList.get(i);
                    if(item.getIsHidden()!=null && item.getIsHidden().intValue()==0){
                    //System.out.println("Processing item ("+item.getItemType()+")");
                    if(item.getItemType()!=null  && item.getItemType().equalsIgnoreCase("bg")){
                        
                        g.setComposite(AlphaComposite.Src);
                        g.setColor(imgHandler.argbParse(item.getBgColor(), item.getOpacity()));
                        g.fillRect(0, 0, product.getWidth(), product.getHeight());
                    }else if(item.getItemType()!=null  && item.getItemType().equalsIgnoreCase("text")){
                        newX=item.getPosX().intValue()*scaleAll;
                        newY=item.getPosY().intValue()*scaleAll;
                        cx = (newY+((item.getWidth()*scaleAll)/ 2)) ;
                        cy = (newX+((item.getHeight()*scaleAll)/2)) ;
                        // System.out.println("X"+(cx)+ "Y" +(cy));
                        /*
                        AffineTransform trans = new AffineTransform();
                        //sw=item.getWidth()*scaleAll/image.getWidth();
                        //sh=item.getHeight()*scaleAll/image.getHeight();
                        newX=item.getPosX().intValue()*scaleAll;
                        newY=item.getPosY().intValue()*scaleAll;
                        cx = (item.getWidth()*scaleAll) / 2;
                        cy = (item.getHeight()*scaleAll)/2 ;
                        //nW=
                        if(item.getRotate()>0){
                            System.out.println("X"+(cx+newY)+ "Y" +(cy+newX));
                            //trans.translate(cx+newY, cy+newX);
                            //trans.rotate(Math.toRadians(item.getRotate()));
                            
                            //trans.translate(-cx, -cy);
                        
                        }
                        //trans.rotate(Math.toRadians(45), image.getWidth()/2, image.getHeight()/2);
                        //trans.scale(scaleAll,scaleAll );
                        System.out.println("center: "+ ((item.getWidth()*scaleAll)/2)+":"+( (item.getHeight()*scaleAll)/2));
                        System.out.println("center: "+ (image.getWidth()*trans.getScaleX())+": - :"+trans.getShearX());
                                    
                        //g.drawImage(image, trans, null);
                        System.out.println( (float)newX+":"+(float)newY);
                        
                        /*
                        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
                        g.setComposite(alphaChannel);
                        g.setFont(new Font("Arial",Font.BOLD, item.getFontSize()));
                        g.setColor(imgHandler.argbParse(item.getColor(), (double)1));
                        //g.setTransform(trans);
                        g.drawString("testing\n"+item.getTextDesc(), (int)newX, (int)newY);
                        */
                        /*
                        g.setComposite(AlphaComposite.Src);
                        g.setColor(imgHandler.argbParse(item.getBgColor(), item.getOpacity()));
                        if(item.getRotate()>0){
                            g.setTransform(trans);
                        }
                        System.out.println("Y:"+newY);
                        System.out.println("X:"+newX);
                        System.out.println("W:"+(item.getWidth().intValue()*scaleAll));
                        System.out.println("H:"+(item.getHeight().intValue()*scaleAll));
                        g.fillRect(newY, newX, item.getWidth().intValue()*scaleAll, item.getHeight().intValue()*scaleAll);
                      */
                        //g.setComposite(AlphaComposite.Src);
                        /*
                        alpha=(int)Math.ceil(item.getOpacity()*255);
                        alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha);
                        g.setComposite(alphaChannel);
                        g.setColor(imgHandler.argbParse(item.getBgColor(), item.getOpacity()));
                        rect2 = new Rectangle(newY, newX, item.getWidth().intValue()*scaleAll, item.getHeight().intValue()*scaleAll);
                        g.rotate(Math.toRadians(item.getRotate()), cx, cy);
                        g.draw(rect2);
                        g.fill(rect2);
                        */
                        g.setTransform(oldAT);
                       alphaChannel = AlphaComposite.getInstance(
                                        AlphaComposite.SRC_OVER, 1f);
                                g.setComposite(alphaChannel);
                        g.rotate(Math.toRadians(item.getRotate()), cx+15, cy+5);
                        g.setColor(imgHandler.argbParse(item.getColor(), (double)1));
                        double fSize=item.getFontSize()*2*1.34;
                        //System.out.println("fSize"+fSize+":"+item.getFontSize());
                        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(config.getOutputPath()+"/fonts/Free-HK-Kai_1_02.ttf" ));
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        //customFont
                        ge.registerFont(customFont);
                        customFont = Font.createFont(Font.TRUETYPE_FONT, new File(config.getOutputPath()+"/fonts/NotoSansTC-Bold.otf" ));
                        ge.registerFont(customFont);
                        customFont = Font.createFont(Font.TRUETYPE_FONT, new File(config.getOutputPath()+"/fonts/NotoSansTC-Regular.otf" ));
                        ge.registerFont(customFont);
                        customFont = Font.createFont(Font.TRUETYPE_FONT, new File(config.getOutputPath()+"/fonts/SIMSUN.ttf" ));
                        ge.registerFont(customFont);
                        /*String fonts[] = ge.getAvailableFontFamilyNames();
                        for(int a=0; fonts!=null && a<fonts.length;a++){
                            System.out.println(fonts[a]);
                        }*/
                                
                        
                        if(item.getTextBold().intValue()==0 && item.getTextItalic().intValue()==0){
                            font=new Font(this.getFontName(item.getFontName()),Font.PLAIN, (int)fSize);
                             //font=new Font("自由香港楷書 (4700字)",Font.PLAIN, (int)fSize);
                        }else if(item.getTextBold().intValue()==1 && item.getTextItalic().intValue()==1){
                            font=new Font(this.getFontName(item.getFontName()),Font.BOLD | Font.ITALIC, (int)fSize);
                        }else if(item.getTextBold().intValue()==0 && item.getTextItalic().intValue()!=0){
                            font=new Font(this.getFontName(item.getFontName()), Font.ITALIC, (int)fSize);
                        }else if(item.getTextBold().intValue()!=0 && item.getTextItalic().intValue()==0){
                            font=new Font(this.getFontName(item.getFontName()), Font.BOLD, (int)fSize);
                        }else{
                            font=new Font(this.getFontName(item.getFontName()),Font.PLAIN, (int)fSize);
                        }
                        
                        fontAttributes = font.getAttributes();
                        if(item.getTextUnder()!=null && item.getTextUnder().intValue()==1){
                            fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        }
                        //System.out.println("B+I: "+(TextAttribute.UNDERLINE)+":"+TextAttribute.UNDERLINE_ON);
                        
                       // FontMetrics m= g.getFontMetrics(font); // g is your current Graphics object
                        //double totalSize= (item.getFontSize()*2) * (m.getAscent() + m.getDescent()) / m.getAscent();
                        g.setFont(font.deriveFont(fontAttributes));
                        //System.out.println("new("+i+"): "+newY+"("+item.getPosY()+")"+":"+newX+"("+item.getPosX()+")");
                        drawString(g, item.getTextDesc(), newY,newX);
                        
                        
                      
                    }else if(item.getItemType()!=null  && (item.getItemType().equalsIgnoreCase("photo") || 
                             item.getItemType().equalsIgnoreCase("material") || item.getItemType().equalsIgnoreCase("role"))){
                        if(item.getItemType().equalsIgnoreCase("material") || item.getItemType().equalsIgnoreCase("role")){
                            path=config.getAbsPath();
                            path=path.replace("/upload", "/");
                            //System.out.println("Metrail: "+path+item.getImgSrc());
                            photo=new File(path+item.getImgSrc());
                        }else{
                            photo=new File(item.getImgSrc());
                            System.out.println("Img src: "+photo.getAbsolutePath());
                        }
                        
                         System.out.println("File: "+photo);
                        if(photo.exists()){
                            image=ImageIO.read(photo);
                            if(image!=null){
                                //System.out.println("x: "+item.getPosX().intValue());
                               // System.out.println("y: "+item.getPosY().intValue());
                                //System.out.println("W: "+item.getWidth().intValue());
                               // System.out.println("H: "+item.getHeight().intValue());
                                //g.setComposite(AlphaComposite.Xor);
                                alphaChannel = AlphaComposite.getInstance(
                                        AlphaComposite.SRC_OVER, 1f);
                                g.setComposite(alphaChannel);
                                
                                if(item.getIsFilp()!=null && item.getIsFilp().intValue()==1){
                                    // Flip the image horizontally
                                    txFilp = AffineTransform.getScaleInstance(-1, 1);
                                    txFilp.translate(-image.getWidth(null), 0);
                                    op = new AffineTransformOp(txFilp, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                                    image = op.filter(image, null);
                                }
                                
                                if(item.getRotate()>0){
                                    //System.out.println("i("+i+")"+item.getName());
                                    widthOfImage = image.getWidth()*scaleAll;
                                    heightOfImage = image.getHeight()*scaleAll;
                                    //g.rotate(Math.toRadians(45),widthOfImage / 2, heightOfImage / 2);
                                    //System.out.println("R: "+item.getPosX().intValue()+":"+scaleAll+":"+widthOfImage);
                                   // System.out.println("R: "+((item.getPosX().intValue()*scaleAll)+(widthOfImage / 2)));
                                   // System.out.println("R: "+(item.getPosY().intValue()*(double)scaleAll+(heightOfImage / 2)));
                                    
                                    newX=item.getPosX().intValue()*scaleAll;
                                    newY=item.getPosY().intValue()*scaleAll;
                                    
                                    AffineTransform trans = new AffineTransform();
                                    sw=item.getWidth()*scaleAll/image.getWidth();
                                    sh=item.getHeight()*scaleAll/image.getHeight();
                                    
                                    cx = (image.getWidth()*sw) / 2;
                                    cy = (image.getHeight()*sh) / 2;
                                    //nW=
                                    trans.translate(cx+newY, cy+newX);
                                    trans.rotate(Math.toRadians(item.getRotate()));
                                    trans.translate(-cx, -cy);
                                    //trans.rotate(Math.toRadians(45), image.getWidth()/2, image.getHeight()/2);
                                    trans.scale(sw,sh );
                                    //System.out.println("center: "+ ((item.getWidth()*scaleAll)/2)+":"+( (item.getHeight()*scaleAll)/2));
                                    //System.out.println("center: "+ (image.getWidth()*trans.getScaleX())+": - :"+trans.getShearX());
                                    //trans.rotate(Math.toRadians(45), (item.getWidth()*scaleAll)/2, (item.getHeight()*scaleAll)/2);
                                    
                                    //trans.rotate(Math.toRadians(45));
                                    //trans.translate(item.getPosX().intValue()*scaleAll, item.getPosY().intValue()*scaleAll);
                                    //trans.
                                    //trans.scale(item.getWidth().intValue()*scaleAll, item.getHeight().intValue());
                                    //g.scale((item.getWidth().intValue()*scaleAll/image.getWidth()), (item.getHeight().intValue()*scaleAll/image.getHeight()));
                                    
                                    
                                    //g.rotate(Math.toRadians(45),item.getPosX().intValue()*scaleAll+(widthOfImage / 2),item.getPosY().intValue()*scaleAll+(heightOfImage / 2) );
                                    g.drawImage(image, trans, null);
                                    //g.drawImage(image, trans, null);
                                    /*g.drawImage(image,item.getPosY().intValue()*scaleAll, item.getPosX().intValue()*scaleAll ,
                                        item.getWidth().intValue()*scaleAll, item.getHeight().intValue()*scaleAll, trans);*/
                                }else{
                                    g.drawImage(image,item.getPosY().intValue()*scaleAll, item.getPosX().intValue()*scaleAll ,
                                        item.getWidth().intValue()*scaleAll, item.getHeight().intValue()*scaleAll, null);    
                                }
                                
                                
                                
                            }
                        }
                    }
                    g.setTransform(oldAT);
                    }
                }
                
                g.dispose();
                
                outoutPath=config.getOutputPath()+"/"+user.getUuid()+"/"+editor.getUuid()+"/out_"+editor.getId()+".png";
                oProduct=new File(outoutPath);
                if(!oProduct.exists()){
                    oProduct.mkdirs();
                }
                ImageIO.write(product, "png", oProduct);
                System.out.println(oProduct.getAbsoluteFile());
                
                tx=session.beginTransaction();
                editor.setFileAbsSrc(oProduct.getAbsolutePath());
                editor.setUrl("/panel/upload/editor/"+editor.getUuid()+"/preview.html");
                session.saveOrUpdate(editor);
                prod=editor.getProdId();
                prod.setProductUrl(editor.getUrl());
                prod.setEditorUuid(editor.getUuid());
                prod.setProductFileName(oProduct.getName());
                prod.setProductSrc(outoutPath);
                System.out.println(prod.getId());
                session.saveOrUpdate(prod);
                tx.commit();
                
                
                
                result.setObj(editor);
                result.setCode(1);
                result.setMsg("label.success");
                
                
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
    
    private void drawString(Graphics2D g, String text, int x, int y) {
        int lineHeight = g.getFontMetrics().getHeight();
        //y=y-5;
        for (String line : text.split("\n")){
            g.drawString(line, x, y+= lineHeight);
            //y=y+lineHeight;
        }
    }
    
    public String getFontName(String code){
        String result="Arial";
        try{
            if(code!=null && code.equalsIgnoreCase("hk")){
                result="自由香港楷書 (4700字)";
            }else if(code!=null && code.equalsIgnoreCase("noto")){
                result="Noto Sans TC";
            }else if(code!=null && code.equalsIgnoreCase("times")){
                result="Times New Roman";
            }else if(code!=null && code.equalsIgnoreCase("SimSun")){
                result="SimSun";
            }else{
                result="Arial";
            }
            
            
        }catch(Exception e){
        }
        return result;
    }
}
