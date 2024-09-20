package com.lycodeing.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.authorization")
@Data
public class AuthorizationProperties {

    /**
     * 授权密钥
     */
    private String secretKey = "secretKey";

    /**
     * Token 过期时间（以秒为单位）
     */
    private Long tokenExpiration = 3600L;

    /**
     * Token 前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Token 请求头
     */
    private String tokenHeader = "Authorization";

    /**
     * 登录地址
     */
    private String loginUrl = "/login";

    /**
     * 跳过认证的地址路径
     */
    private String[] permitUrls = new String[]{loginUrl};


    /**
     * 是否采用session
     */
    private boolean session = true;
}
