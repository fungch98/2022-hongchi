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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "editor_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EditorInfo.findAll", query = "SELECT e FROM EditorInfo e"),
    @NamedQuery(name = "EditorInfo.findById", query = "SELECT e FROM EditorInfo e WHERE e.id = :id"),
    @NamedQuery(name = "EditorInfo.findByUuid", query = "SELECT e FROM EditorInfo e WHERE e.uuid = :uuid")})
public class EditorInfo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "editor_status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "file_abs_src")
    private String fileAbsSrc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "prod_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProductInfo prodId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "editor_desc")
    private String editorDesc;
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
    @OneToMany(mappedBy = "editorId")
    @OrderBy(clause = "seq asc")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<EditorItem> editorItemList;
    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo createUser;
    @JoinColumn(name = "modify_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo modifyUser;

    public EditorInfo() {
    }

    public EditorInfo(Integer id) {
        this.id = id;
    }

    public EditorInfo(Integer id, String uuid, String name, String editorDesc, Date createDate, Date modifyDate) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.editorDesc = editorDesc;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditorDesc() {
        return editorDesc;
    }

    public void setEditorDesc(String editorDesc) {
        this.editorDesc = editorDesc;
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

    @XmlTransient
    public List<EditorItem> getEditorItemList() {
        return editorItemList;
    }

    public void setEditorItemList(List<EditorItem> editorItemList) {
        this.editorItemList = editorItemList;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!(object instanceof EditorInfo)) {
            return false;
        }
        EditorInfo other = (EditorInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.EditorInfo[ id=" + id + " ]";
    }



    public String getFileAbsSrc() {
        return fileAbsSrc;
    }

    public void setFileAbsSrc(String fileAbsSrc) {
        this.fileAbsSrc = fileAbsSrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductInfo getProdId() {
        return prodId;
    }

    public void setProdId(ProductInfo prodId) {
        this.prodId = prodId;
    }
    
}
