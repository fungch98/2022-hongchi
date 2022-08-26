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
@Table(name = "group_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupInfo.findAll", query = "SELECT g FROM GroupInfo g"),
    @NamedQuery(name = "GroupInfo.findById", query = "SELECT g FROM GroupInfo g WHERE g.id = :id"),
    @NamedQuery(name = "GroupInfo.findByName", query = "SELECT g FROM GroupInfo g WHERE g.name = :name"),
    @NamedQuery(name = "GroupInfo.findByIsAdmin", query = "SELECT g FROM GroupInfo g WHERE g.isAdmin = :isAdmin"),
    @NamedQuery(name = "GroupInfo.findByGroupStatus", query = "SELECT g FROM GroupInfo g WHERE g.groupStatus = :groupStatus"),
    @NamedQuery(name = "GroupInfo.findByCreateDate", query = "SELECT g FROM GroupInfo g WHERE g.createDate = :createDate"),
    @NamedQuery(name = "GroupInfo.findByModifyDate", query = "SELECT g FROM GroupInfo g WHERE g.modifyDate = :modifyDate")})
public class GroupInfo implements Serializable {

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
    //@Lob
    @Size(max = 65535)
    @Column(name = "desc")
    private String desc;
    @Column(name = "is_admin")
    private Integer isAdmin;
    @Column(name = "group_status")
    private Integer groupStatus;
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

    public GroupInfo() {
    }

    public GroupInfo(Integer id) {
        this.id = id;
    }

    public GroupInfo(Integer id, String name, Date createDate, Date modifyDate) {
        this.id = id;
        this.name = name;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupInfo)) {
            return false;
        }
        GroupInfo other = (GroupInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.GroupInfo[ id=" + id + " ]";
    }
    
}
