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
import com.ae21.studio.hongchi.entity.bean.EditorInfo;
import com.ae21.studio.hongchi.entity.bean.EditorItem;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UserInfo;
import com.ae21.studio.hongchi.entity.system.CustImageHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    
    public List<EditorItem> loadEditorItem(EditorInfo editor)throws Exception{
        List<EditorItem> result=null;
        Session session = sessionFactory.openSession();
        SQLQuery query = null;
        try{
            if(editor!=null ){
                if(editor.getId()!=null){
                    query=session.createSQLQuery("Select {i.*} from editor_item i where i.editor_id=:id ORDER by i.z_index");
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
                result.setFontName("");
                result.setFontSize(0);
                
                if(type.equalsIgnoreCase("photo")){
                    query=session.getNamedQuery("ProductInfo.findByUuid");
                    query.setString("uuid", target);
                    photo=(ProductInfo)query.uniqueResult();
                    System.out.println("Photo UUID: "+photo+":"+target);
                    if(photo!=null){
                        result.setImgSrc(photo.getProductSrc());
                        result.setImgUrl(photo.getProductUrl());
                        result.setImgUuid(target);
                        result.setName(photo.getName());
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
    
    public ResultBean save(HttpServletRequest request,String uuid,  UserInfo user)throws Exception{
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
        
        EditorInfo editor=null;
        EditorItem item=null;
        ProductInfo photo=null;
        
        List<EditorItem> itemList=new ArrayList<EditorItem>();
        
        String colorCode="";
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            
            editorName=(editorName!=null?editorName.trim():"");
            editorDesc=(editorDesc!=null?editorDesc.trim():"");
            
            if(user!=null){
                if(uuid!=null && uuid.equalsIgnoreCase("new")){
                    System.out.println("Editor Create");
                    
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
                            System.out.println("Color: "+colorCode);
                            if(colorCode!=null && colorCode.length()>=7){
                                item.setBgColor(colorCode.substring(0,7));
                            }
                            
                            if(colorCode!=null && colorCode.length()>=9){
                                item.setOpacityVal(colorCode.substring(7));
                            }
                            
                            
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
                            item.setName((name!=null && name.length>=i?name[i]:""));
                            item.setPosX((posX!=null && posX.length>=i?Double.parseDouble(posX[i]):0));
                            item.setPosY((posY!=null && posY.length>=i?Double.parseDouble(posY[i]):0));
                            item.setSeq((seq!=null && seq.length>=i?Integer.parseInt(seq[i]):0));
                            item.setText((text!=null && text.length>=i?text[i]:""));
                            item.setTextDesc((textDesc!=null && textDesc.length>=i?textDesc[i]:""));
                            item.setUuid(common.generateUUID());
                            item.setWidth((width!=null && width.length>=i?Double.parseDouble(width[i]):0));
                            item.setZIndex((zIndex!=null && zIndex.length>=i?Integer.parseInt(zIndex[i]):0));
                            item.setFontSize((fontSize!=null && fontSize.length>=i?Integer.parseInt(fontSize[i]):0));
                            item.setFontName((fontName!=null && fontName.length>=i?fontName[i]:""));
                            
                            itemList.add(item);
                        }catch(Exception ingore){
                            ingore.printStackTrace();
                            result.setCode(-1002);
                            result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                        }
                        
                    }
                    System.out.println(itemList.size()+":"+itemType.length);
                        if(itemList.size()!=itemType.length){  //Miss Some Item value
                            result.setCode(-1003);
                            result.setMsg("ERROR.EDITOR.SAVE.DIFF");
                        }
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
        Color color=null;
        
        float opacity=1f;
        int scaleAll=(1200/600);
        
        AffineTransform oldAT=null;
        try{
            result.setCode(0);
            result.setMsg("ERROR.NULL");
            System.out.println("Start to Generate Photo"+editor.getId());
            if(editor!=null && editor.getId()!=null && user!=null && config!=null){
                itemList=this.loadEditorItem(editor);
                product  = new BufferedImage(600*scaleAll, 450*scaleAll, BufferedImage.TYPE_INT_ARGB);
                //background= new BufferedImage(1200, 900, BufferedImage.TYPE_INT_RGB);
                g=product.createGraphics();
                g.setComposite(AlphaComposite.Clear);
                g.fillRect(0, 0, product.getWidth(), product.getHeight());
                oldAT = g.getTransform();
                for(int i=0; itemList!=null && i<itemList.size();i++){
                    item=itemList.get(i);
                    System.out.println("Processing item ("+item.getItemType()+")");
                    if(item.getItemType()!=null  && item.getItemType().equalsIgnoreCase("bg")){
                        
                        g.setComposite(AlphaComposite.Src);
                        g.setColor(imgHandler.argbParse(item.getBgColor(), item.getOpacity()));
                        g.fillRect(0, 0, product.getWidth(), product.getHeight());
                    }else if(item.getItemType()!=null  && item.getItemType().equalsIgnoreCase("photo")){
                        photo=new File(item.getImgSrc());
                        System.out.println("File: "+photo);
                        if(photo.exists()){
                            image=ImageIO.read(photo);
                            if(image!=null){
                                System.out.println("x: "+item.getPosX().intValue());
                                System.out.println("y: "+item.getPosY().intValue());
                                System.out.println("W: "+item.getWidth().intValue());
                                System.out.println("H: "+item.getHeight().intValue());
                                
                                
                                if(i==3){
                                    System.out.println("i("+i+")"+item.getName());
                                    widthOfImage = image.getWidth()*scaleAll;
                                    heightOfImage = image.getHeight()*scaleAll;
                                    //g.rotate(Math.toRadians(45),widthOfImage / 2, heightOfImage / 2);
                                    System.out.println("R: "+item.getPosX().intValue()+":"+scaleAll+":"+widthOfImage);
                                    System.out.println("R: "+((item.getPosX().intValue()*scaleAll)+(widthOfImage / 2)));
                                    System.out.println("R: "+(item.getPosY().intValue()*(double)scaleAll+(heightOfImage / 2)));
                                    int nW=0;
                                    int nH=0;
                                    int newX=item.getPosX().intValue()*scaleAll;
                                    int newY=item.getPosY().intValue()*scaleAll;
                                    double sw=0;
                                    double sh=0;
                                    /*
                                    AffineTransform affineTransform = new AffineTransform();
                                    affineTransform.rotate(Math.toRadians(45),item.getPosX().intValue()*scaleAll+(widthOfImage / 2),item.getPosY().intValue()*scaleAll+(heightOfImage / 2));
                                    AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
                                    affineTransformOp.filter(image);
*/                      
                                    AffineTransform trans = new AffineTransform();
                                    //trans.rotate(Math.toRadians(45),item.getPosX().intValue()*scaleAll+(widthOfImage / 2),item.getPosY().intValue()*scaleAll+(heightOfImage / 2));
                                    sw=item.getWidth()*scaleAll/image.getWidth();
                                    sh=item.getHeight()*scaleAll/image.getHeight();
                                    
                                    double cx = (image.getWidth()*sw) / 2;
                                    double cy = (image.getHeight()*sh) / 2;
                                    //nW=
                                    trans.translate(cx+newY, cy+newX);
                                    trans.rotate(Math.toRadians(45));
                                    trans.translate(-cx, -cy);
                                    //trans.rotate(Math.toRadians(45), image.getWidth()/2, image.getHeight()/2);
                                    trans.scale(sw,sh );
                                    System.out.println("center: "+ ((item.getWidth()*scaleAll)/2)+":"+( (item.getHeight()*scaleAll)/2));
                                    System.out.println("center: "+ (image.getWidth()*trans.getScaleX())+": - :"+trans.getShearX());
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
}
