package com.green.greengramver2.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    @Autowired
    private ClientIpLoggingInterceptor clientIpLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientIpLoggingInterceptor);
    }

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController의 모든 URL에 "/api" prefix를 설정
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }



}
