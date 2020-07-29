package com.ruoyi.swhysc.bean.extension;

import com.ruoyi.swhysc.bean.dto.laterdto.later_ChannelDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ChannelDtoExtension extends later_ChannelDto implements Serializable {
    //存放子类频道标签
    List<later_ChannelDto> children;
}
