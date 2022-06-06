/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ae21.studio.hongchi.entity.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "para_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParaInfo.findAll", query = "SELECT p FROM ParaInfo p"),
    @NamedQuery(name = "ParaInfo.findById", query = "SELECT p FROM ParaInfo p WHERE p.id = :id"),
    @NamedQuery(name = "ParaInfo.findByCode", query = "SELECT p FROM ParaInfo p WHERE p.code = :code and p.subcode = :subcode and p.paraStatus =1  ORDER BY p.seq "),
    @NamedQuery(name = "ParaInfo.findByVal", query = "SELECT p FROM ParaInfo p WHERE p.code = :code and p.subcode = :subcode and p.value=:value and p.paraStatus =1  ORDER BY p.seq "),
    @NamedQuery(name = "ParaInfo.findByUuid", query = "SELECT p FROM ParaInfo p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "ParaInfo.findByUrl", query = "SELECT p FROM ParaInfo p WHERE p.url = :url")})
public class ParaInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "subcode")
    private String subcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seq")
    private int seq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private int value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "para_status")
    private int paraStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "str01")
    private String str01;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "str02")
    private String str02;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "str03")
    private String str03;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dd01")
    private double dd01;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dd02")
    private double dd02;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dd03")
    private double dd03;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2500)
    @Column(name = "url")
    private String url;

    public ParaInfo() {
    }

    public ParaInfo(Integer id) {
        this.id = id;
    }

    public ParaInfo(Integer id, String code, String subcode, String uuid, int seq, int value, int paraStatus, String str01, String str02, String str03, double dd01, double dd02, double dd03, String url) {
        this.id = id;
        this.code = code;
        this.subcode = subcode;
        this.uuid = uuid;
        this.seq = seq;
        this.value = value;
        this.paraStatus = paraStatus;
        this.str01 = str01;
        this.str02 = str02;
        this.str03 = str03;
        this.dd01 = dd01;
        this.dd02 = dd02;
        this.dd03 = dd03;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getParaStatus() {
        return paraStatus;
    }

    public void setParaStatus(int paraStatus) {
        this.paraStatus = paraStatus;
    }

    public String getStr01() {
        return str01;
    }

    public void setStr01(String str01) {
        this.str01 = str01;
    }

    public String getStr02() {
        return str02;
    }

    public void setStr02(String str02) {
        this.str02 = str02;
    }

    public String getStr03() {
        return str03;
    }

    public void setStr03(String str03) {
        this.str03 = str03;
    }

    public double getDd01() {
        return dd01;
    }

    public void setDd01(double dd01) {
        this.dd01 = dd01;
    }

    public double getDd02() {
        return dd02;
    }

    public void setDd02(double dd02) {
        this.dd02 = dd02;
    }

    public double getDd03() {
        return dd03;
    }

    public void setDd03(double dd03) {
        this.dd03 = dd03;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof ParaInfo)) {
            return false;
        }
        ParaInfo other = (ParaInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ae21.studio.hongchi.entity.bean.ParaInfo[ id=" + id + " ]";
    }
    
}
