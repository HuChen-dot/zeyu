package com.ruoyi.project.interestrate.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/***
 *  利率表数据层
 */
public class Interestrate implements Serializable {
    //主键
    private Integer sysId;
    //融资利率
    private Double sysRate;
    //融卷利率
    private Double sysRating;
    //融资逾期罚息率
    private Double sysRateOverdue;
    //融卷逾期罚息率
    private Double sysRatingOverdue;
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-hh HH-mm-ss")
    private Date modificationTime;

    //get set 方法
    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getSysId() {
        return this.sysId;
    }

    public void setSysRate(Double sysRate) {
        this.sysRate = sysRate;
    }

    public Double getSysRate() {
        return this.sysRate;
    }

    public void setSysRating(Double sysRating) {
        this.sysRating = sysRating;
    }

    public Double getSysRating() {
        return this.sysRating;
    }

    public void setSysRateOverdue(Double sysRateOverdue) {
        this.sysRateOverdue = sysRateOverdue;
    }

    public Double getSysRateOverdue() {
        return this.sysRateOverdue;
    }

    public void setSysRatingOverdue(Double sysRatingOverdue) {
        this.sysRatingOverdue = sysRatingOverdue;
    }

    public Double getSysRatingOverdue() {
        return this.sysRatingOverdue;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Date getModificationTime() {
        return this.modificationTime;
    }
}
