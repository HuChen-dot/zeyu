package com.ruoyi.swhysc.bean.extension;

import com.ruoyi.swhysc.bean.dto.laterdto.later_IndexDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class IndexDtoExtension extends later_IndexDto implements Serializable {
    private List<later_IndexDto> children;

}
