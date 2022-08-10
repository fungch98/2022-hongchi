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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "editor_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EditorItem.findAll", query = "SELECT e FROM EditorItem e"),
    @NamedQuery(name = "EditorItem.findById", query = "SELECT e FROM EditorItem e WHERE e.id = :id"),
    @NamedQuery(name = "EditorItem.findBySeq", query = "SELECT e FROM EditorItem e WHERE e.seq = :seq"),
    @NamedQuery(name = "EditorItem.findByUuid", query = "SELECT e FROM EditorItem e WHERE e.uuid = :uuid")
})
public class EditorItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "seq")
    private Integer seq;
    @Size(max = 250)
    @Column(name = "uuid")
    private String uuid;
    @Size(max = 250)
    @Column(name = "item_type")
    private String itemType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pos_x")
    private Double posX;
    @Column(name = "pos_y")
    private Double posY;
    @Column(name = "width")
    private Double width;
    @Column(name = "height")
    private Double height;
    @Column(name = "opacity")
    private Double opacity;
    @Size(max = 50)
    @Column(name = "bg_color")
    private String bgColor;
    @Size(max = 50)
    @Column(name = "color")
    private String color;
    @Column(name = "z_index")
    private Integer zIndex;
    @Size(max = 2000)
    @Column(name = "text")
    private String text;
    @Lob
    @Size(max = 65535)
    @Column(name = "text_desc")
    private String textDesc;
    @Size(max = 2000)
    @Column(name = "img_url")
    private String imgUrl;
    @Size(max = 250)
    @Column(name = "img_uuid")
    private String imgUuid;
    @Size(max = 5000)
    @Column(name = "img_src")
    private String imgSrc;
    @Size(max = 250)
    @Column(name = "img_upload_uuid")
    private String imgUploadUuid;
    @Size(max = 5000)
    @Column(name = "img_upload_src")
    private String imgUploadSrc;
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    @JoinColumn(name = "editor_id", referencedColumnName = "id")
    @ManyToOne
    private EditorInfo editorId;
    @JoinColumn(name = "modify_user", referencedColumnName = "id")
    @ManyToOne
    private UserInfo modifyUser;
    
    private String opacityVal="FF";

    public EditorItem() {
    }

    public EditorItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }



    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
        this.opacityVal=this.getOpacityHex(opacity);
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getZIndex() {
        return zIndex;
    }

    public void setZIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextDesc() {
        return textDesc;
    }

    public void setTextDesc(String textDesc) {
        this.textDesc = textDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUuid() {
        return imgUuid;
    }

    public void setImgUuid(String imgUuid) {
        this.imgUuid = imgUuid;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgUploadUuid() {
        return imgUploadUuid;
    }

    public void setImgUploadUuid(String imgUploadUuid) {
        this.imgUploadUuid = imgUploadUuid;
    }

    public String getImgUploadSrc() {
        return imgUploadSrc;
    }

    public void setImgUploadSrc(String imgUploadSrc) {
        this.imgUploadSrc = imgUploadSrc;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public EditorInfo getEditorId() {
        return editorId;
    }

    public void setEditorId(EditorInfo editorId) {
        this.editorId = editorId;
    }

    public UserInfo getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(UserInfo modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public String getOpacityVal() {
        return this.getOpacityHex(this.opacity);
    }

    public void setOpacityVal(String opacityVal) {
        this.opacityVal = opacityVal;
        this.opacity=this.getOpacityVal(opacityVal);
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
        if (!(object instanceof EditorItem)) {
            return false;
        }
        EditorItem other = (EditorItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.EditorItem[ id=" + id + " ]";
    }
    
    public String getOpacityHex(Double value){
        String result="FF";
        try{
            
            result=String.format("%02X", (int)Math.ceil(value.intValue()*255));
            //System.out.println("HEX: "+result+":"+value);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public double getOpacityVal(String hex){
        double result=1;
        try{
            int decimal = Integer.parseInt(hex, 16);
            if(decimal>=0 && decimal<=255){
                result=Math.ceil((decimal/255));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
}
