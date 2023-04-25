package com.example.bookshop.Config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    /*配置全局跨域请求*/
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")  //允许跨域访问的路径
                .allowedOrigins("*")    //允许跨域请求的源
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")  //允许的请求方法
                .maxAge(168000)     //最大响应时间
                .allowedHeaders("*")    //允许头部设置
                .allowCredentials(true);    //是否发送cookie
    }

}
