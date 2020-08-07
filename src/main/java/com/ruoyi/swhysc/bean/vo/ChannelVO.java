package com.ruoyi.swhysc.bean.vo;

import com.ruoyi.swhysc.bean.extension.ChannelDtoExtension;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回给前端的信息
 */
@Getter
@Setter
public class ChannelVO {
    //封装一条父类级其所有的子类标签
    private ChannelDtoExtension routes;
}
