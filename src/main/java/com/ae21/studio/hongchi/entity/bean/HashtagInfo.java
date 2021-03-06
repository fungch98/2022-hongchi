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
@Table(name = "hashtag_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HashtagInfo.findAll", query = "SELECT h FROM HashtagInfo h ORDER BY h.name, h.modifyDate desc"),
    @NamedQuery(name = "HashtagInfo.findById", query = "SELECT h FROM HashtagInfo h WHERE h.id = :id"),
    @NamedQuery(name = "HashtagInfo.findByName", query = "SELECT h FROM HashtagInfo h WHERE h.name = :name"),
    @NamedQuery(name = "HashtagInfo.findByUuid", query = "SELECT h FROM HashtagInfo h WHERE h.uuid = :uuid")
})
public class HashtagInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "uuid")
    private String uuid;
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

    @JoinColumn(name = "create_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo createUser;
    @JoinColumn(name = "modify_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserInfo modifyUser;
    
    @Basic(optional = false)
    @Column(name = "hit_rate")
    private Integer hitRate;

    public HashtagInfo() {
    }

    public HashtagInfo(Integer id) {
        this.id = id;
    }

    public HashtagInfo(Integer id, String name, String uuid, Date createDate, Date modifyDate) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
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

    public Integer getHitRate() {
        return hitRate;
    }

    public void setHitRate(Integer hitRate) {
        this.hitRate = hitRate;
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
        if (!(object instanceof HashtagInfo)) {
            return false;
        }
        HashtagInfo other = (HashtagInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.HashtagInfo[ id=" + id + " ]";
    }
    
}
