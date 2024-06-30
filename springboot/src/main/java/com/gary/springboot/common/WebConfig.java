package com.gary.springboot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements  WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller統一的接口前綴(為接口添加統一的前綴( EX: /admin))
        //configurer.addPathPrefix會找到 RestContoller 的註解，然後添加 /api前綴
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }


    /**
     * 攔截器使用
     */
    @Autowired
    JwtInterceptor jwtInterceptor;

    // 加自定義攔截器JwtInterceptor，設置攔截規則
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //攔截所有請求 以 /api開頭
        //excludePathPatterns("/api/login");只有 /login頁面不攔截，登入時還沒有token
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/admin/login");
    }


}
