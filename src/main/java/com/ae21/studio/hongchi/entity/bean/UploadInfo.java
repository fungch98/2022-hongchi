/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.bean;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "upload_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UploadInfo.findAll", query = "SELECT u FROM UploadInfo u"),
    @NamedQuery(name = "UploadInfo.findById", query = "SELECT u FROM UploadInfo u WHERE u.id = :id"),
    @NamedQuery(name = "UploadInfo.findByUuid", query = "SELECT u FROM UploadInfo u WHERE u.uuid = :uuid")
})
public class UploadInfo implements Serializable {

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
    @Size(max = 1000)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "file_type")
    private String fileType;
    @Size(max = 1000)
    @Column(name = "url")
    private String url;
    @Size(max = 1000)
    @Column(name = "upload_bucket")
    private String uploadBucket;
    @Size(max = 1000)
    @Column(name = "upload_file_name")
    private String uploadFileName;
    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "file_status")
    private int fileStatus;
    @Size(max = 1000)
    @Column(name = "abs_path")
    private String absPath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_image")
    private int isImage;
   

    public UploadInfo() {
    }

    public UploadInfo(Integer id) {
        this.id = id;
    }

    public UploadInfo(Integer id, String uuid, int fileStatus, int isImage) {
        this.id = id;
        this.uuid = uuid;
        this.fileStatus = fileStatus;
        this.isImage = isImage;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUploadBucket() {
        return uploadBucket;
    }

    public void setUploadBucket(String uploadBucket) {
        this.uploadBucket = uploadBucket;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(int fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public int getIsImage() {
        return isImage;
    }

    public void setIsImage(int isImage) {
        this.isImage = isImage;
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
        if (!(object instanceof UploadInfo)) {
            return false;
        }
        UploadInfo other = (UploadInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.UploadInfo[ id=" + id + " ]";
    }
    
}
