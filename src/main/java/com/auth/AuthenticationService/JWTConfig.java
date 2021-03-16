package com.auth.AuthenticationService;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.jwt.secret")
public class JWTConfig {

    String key;
    Integer keyexpirydays;
    String prefix;
    String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public JWTConfig() {

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getKeyexpirydays() {
        return keyexpirydays;
    }

    public void setKeyexpirydays(Integer keyexpirydays) {
        this.keyexpirydays = keyexpirydays;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
