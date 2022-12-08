/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.system;

import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.HashtagInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class MigrationBean {
    UploadInfo upload=null;
    ProductInfo prod=null;
    CategoryInfo cat=null;
    CategoryInfo folder=null;
    List<HashtagInfo> hashtagList=null;

    public MigrationBean() {
    }

    public UploadInfo getUpload() {
        return upload;
    }

    public void setUpload(UploadInfo upload) {
        this.upload = upload;
    }

    public ProductInfo getProd() {
        return prod;
    }

    public void setProd(ProductInfo prod) {
        this.prod = prod;
    }

    public CategoryInfo getCat() {
        return cat;
    }

    public void setCat(CategoryInfo cat) {
        this.cat = cat;
    }

    public CategoryInfo getFolder() {
        return folder;
    }

    public void setFolder(CategoryInfo folder) {
        this.folder = folder;
    }

    public List<HashtagInfo> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<HashtagInfo> hashtagList) {
        this.hashtagList = hashtagList;
    }
    
    public void addHashtag(HashtagInfo tag){
        try{
            if(this.hashtagList==null){
                this.hashtagList=new ArrayList<HashtagInfo>();
                
            }
            this.hashtagList.add(tag);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
