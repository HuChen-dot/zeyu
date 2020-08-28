package com.rewin.swhysc.bean.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "DOWNLOAD_COUNT")
public class DownloadCount {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "SOFTWAREID")
    private String softwareid;

    @Column(name = "SOFTWARETYPE")
    private String softwaretype;

    @Column(name = "IP")
    private String ip;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return SOFTWAREID
     */
    public String getSoftwareid() {
        return softwareid;
    }

    /**
     * @param softwareid
     */
    public void setSoftwareid(String softwareid) {
        this.softwareid = softwareid == null ? null : softwareid.trim();
    }

    /**
     * @return SOFTWARETYPE
     */
    public String getSoftwaretype() {
        return softwaretype;
    }

    /**
     * @param softwaretype
     */
    public void setSoftwaretype(String softwaretype) {
        this.softwaretype = softwaretype == null ? null : softwaretype.trim();
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}