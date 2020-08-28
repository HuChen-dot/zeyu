package com.rewin.swhysc.bean.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "VJJR_SW")
public class Marketer {
    @Column(name = "JJRBH")
    private String jjrbh;

    @Column(name = "JJRXM")
    private String jjrxm;

    @Column(name = "JJRLB")
    private String jjrlb;

    @Column(name = "JJRZSBH")
    private String jjrzsbh;

    @Column(name = "HTQJ")
    private String htqj;

    @Column(name = "YYB_4")
    private Short yyb4;

    @Column(name = "HDFW")
    private String hdfw;

    @Column(name = "RZRQ")
    private Integer rzrq;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "CERT_NO")
    private String certNo;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "YYB_NAME")
    private String yybName;

    @Column(name = "YYB_PHONE")
    private String yybPhone;

    @Column(name = "YYB_ADDRESS")
    private String yybAddress;

    /**
     * @return JJRBH
     */
    public String getJjrbh() {
        return jjrbh;
    }

    /**
     * @param jjrbh
     */
    public void setJjrbh(String jjrbh) {
        this.jjrbh = jjrbh == null ? null : jjrbh.trim();
    }

    /**
     * @return JJRXM
     */
    public String getJjrxm() {
        return jjrxm;
    }

    /**
     * @param jjrxm
     */
    public void setJjrxm(String jjrxm) {
        this.jjrxm = jjrxm == null ? null : jjrxm.trim();
    }

    /**
     * @return JJRLB
     */
    public String getJjrlb() {
        return jjrlb;
    }

    /**
     * @param jjrlb
     */
    public void setJjrlb(String jjrlb) {
        this.jjrlb = jjrlb == null ? null : jjrlb.trim();
    }

    /**
     * @return JJRZSBH
     */
    public String getJjrzsbh() {
        return jjrzsbh;
    }

    /**
     * @param jjrzsbh
     */
    public void setJjrzsbh(String jjrzsbh) {
        this.jjrzsbh = jjrzsbh == null ? null : jjrzsbh.trim();
    }

    /**
     * @return HTQJ
     */
    public String getHtqj() {
        return htqj;
    }

    /**
     * @param htqj
     */
    public void setHtqj(String htqj) {
        this.htqj = htqj == null ? null : htqj.trim();
    }

    /**
     * @return YYB_4
     */
    public Short getYyb4() {
        return yyb4;
    }

    /**
     * @param yyb4
     */
    public void setYyb4(Short yyb4) {
        this.yyb4 = yyb4;
    }

    /**
     * @return HDFW
     */
    public String getHdfw() {
        return hdfw;
    }

    /**
     * @param hdfw
     */
    public void setHdfw(String hdfw) {
        this.hdfw = hdfw == null ? null : hdfw.trim();
    }

    /**
     * @return RZRQ
     */
    public Integer getRzrq() {
        return rzrq;
    }

    /**
     * @param rzrq
     */
    public void setRzrq(Integer rzrq) {
        this.rzrq = rzrq;
    }

    /**
     * @return MOBILE
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return PHONE
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return CERT_NO
     */
    public String getCertNo() {
        return certNo;
    }

    /**
     * @param certNo
     */
    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    /**
     * @return PHOTO
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * @return GENDER
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * @return YYB_NAME
     */
    public String getYybName() {
        return yybName;
    }

    /**
     * @param yybName
     */
    public void setYybName(String yybName) {
        this.yybName = yybName == null ? null : yybName.trim();
    }

    /**
     * @return YYB_PHONE
     */
    public String getYybPhone() {
        return yybPhone;
    }

    /**
     * @param yybPhone
     */
    public void setYybPhone(String yybPhone) {
        this.yybPhone = yybPhone == null ? null : yybPhone.trim();
    }

    /**
     * @return YYB_ADDRESS
     */
    public String getYybAddress() {
        return yybAddress;
    }

    /**
     * @param yybAddress
     */
    public void setYybAddress(String yybAddress) {
        this.yybAddress = yybAddress == null ? null : yybAddress.trim();
    }
}