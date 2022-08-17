/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.system;

import com.ae21.studio.hongchi.entity.bean.CategoryInfo;
import com.ae21.studio.hongchi.entity.bean.ProductInfo;
import com.ae21.studio.hongchi.entity.bean.UploadInfo;

/**
 *
 * @author Alex
 */
public class MigrationBean {
    UploadInfo upload=null;
    ProductInfo prod=null;
    CategoryInfo cat=null;

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
    
    
}
