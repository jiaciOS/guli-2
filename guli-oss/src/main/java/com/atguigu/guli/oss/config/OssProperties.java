package com.atguigu.guli.oss.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class OssProperties {

    private String endPoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
    public static String fileHost;
}
