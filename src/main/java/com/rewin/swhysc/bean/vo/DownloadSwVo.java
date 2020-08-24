package com.rewin.swhysc.bean.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 返回前端软件下载统计数据
 */
@Data
public class DownloadSwVo implements Comparable<DownloadSwVo> {
    //软件id
    private Integer softwareId;
    //软件类型
    private Integer softwareType;
    //软件类型名称
    private String softwareTypeName;
    //软件名称
    private String softwareName;
    //下载次数
    private Integer downloadCount;
    //下载时间
    private Date downloadTime;
    //下载机器ip
    private String ip;


    @Override
    public int compareTo(DownloadSwVo o) {
        return o.downloadCount - this.downloadCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("softwareId", getSoftwareId())
                .append("softwareType", getSoftwareType())
                .append("softwareTypeName", getSoftwareTypeName())
                .append("softwareName", getSoftwareName())
                .append("downloadCount", getDownloadCount())
                .append("downloadTime", getDownloadTime())
                .append("ip", getIp())
                .toString();
    }
}
