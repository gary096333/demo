package com.gary.springboot.configuration;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Cors配置文件，解決跨域問題
 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();


        corsConfiguration.addAllowedOrigin("*"); //設置訪問源地址
        corsConfiguration.addAllowedHeader("*"); //設置訪問源請求頭
        corsConfiguration.addAllowedMethod("*"); //設置訪問源請求方法

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); //對接口配置跨域設置
        return new CorsFilter(source);


    }

}
