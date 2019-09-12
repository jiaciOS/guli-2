package com.atguigu.guli.ucenter.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.open")
public class OAuth2Properties {

    private String appId;
    private String appSecret;
    private String redirectUrl;
}
