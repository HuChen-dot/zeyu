package com.rewin.swhysc.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/24 16:17
 **/

@Configuration
@PropertySource(
        value = {"classpath:message.properties"},
        encoding = "UTF-8",
        ignoreResourceNotFound = true
)
@ConfigurationProperties(
        prefix = "business"
)
public class ExceptionMsgUtils {
    Map<String, String> message = new HashMap();

    public ExceptionMsgUtils() {
    }

    public String getExecptionMsg(Integer code) {
        String msg = (String)this.message.get(code);
        return !StringUtils.isBlank(msg) ? msg : null;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public Map<String, String> getMessage() {
        return this.message;
    }
}
