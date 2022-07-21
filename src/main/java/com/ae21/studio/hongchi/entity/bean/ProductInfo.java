/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "product_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductInfo.findAll", query = "SELECT p FROM ProductInfo p"),
    @NamedQuery(name = "ProductInfo.findById", query = "SELECT p FROM ProductInfo p WHERE p.id = :id"),
    @NamedQuery(name = "ProductInfo.findByName", query = "SELECT p FROM ProductInfo p WHERE p.name = :name"),
    @NamedQuery(name = "ProductInfo.findByUuid", query = "SELECT p FROM ProductInfo p WHERE p.uuid = :uuid")
})
public class ProductInfo implements Serializable {
    
    public static final int UPLOAD=1;
    public static final int EDITOR=3;
    public static final int URL=2;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "prod_desc")
    private String desc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "product_url")
    private String productUrl;
    @Size(max = 2000)
    @Column(name = "product_src")
    private String productSrc;
    @Size(max = 2000)
    @Column(name = "product_file_name")
    private String productFileName;
    @Size(max = 250)
    @Column(name = "product_uuid")
    private String productUUID;
    @Column(name = "product_ref")
    private Integer productRef;
    @Column(name = "product_create_method")
    private Integer productCreateMethod;
    @Size(max = 1000)
    @Column(name = "search_key")
    private String searchKey;
     @Basic(optional = false)
    @NotNull
    @Column(name = "prod_status")
    private int status=1;
     @Size(max = 1000)
    @Column(name = "product_cat")
    private String productCat;
     @Size(max = 1000)
    @Column(name = "product_tag")
    private String productTag;
    
    
    
    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo createUser;
    @JoinColumn(name = "modify_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo modifyUser;

    public ProductInfo() {
    }

    public ProductInfo(Integer id) {
        this.id = id;
    }

    public ProductInfo(Integer id, String name, String uuid, String desc, Date createDate, Date modifyDate) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.desc = desc;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductSrc() {
        return productSrc;
    }

    public void setProductSrc(String productSrc) {
        this.productSrc = productSrc;
    }

    public String getProductFileName() {
        return productFileName;
    }

    public void setProductFileName(String productFileName) {
        this.productFileName = productFileName;
    }

    public Integer getProductRef() {
        return productRef;
    }

    public void setProductRef(Integer productRef) {
        this.productRef = productRef;
    }

    public Integer getProductCreateMethod() {
        return productCreateMethod;
    }

    public void setProductCreateMethod(Integer productCreateMethod) {
        this.productCreateMethod = productCreateMethod;
    }



    public UserInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserInfo createUser) {
        this.createUser = createUser;
    }

    public UserInfo getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(UserInfo modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public String getProductCat() {
        return productCat;
    }

    public void setProductCat(String productCat) {
        this.productCat = productCat;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }
    
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductInfo)) {
            return false;
        }
        ProductInfo other = (ProductInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.ProductInfo[ id=" + id + " ]";
    }
    
}
