package com.example.starterdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hello") //获取属性值
public class HelloProperties {

    private static final String MSG = "world";

    private String msg = MSG ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}


