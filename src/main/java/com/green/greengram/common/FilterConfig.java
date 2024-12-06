package com.green.greengram.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<IpLoggingFilter> loggingFilter() {
        FilterRegistrationBean<IpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new IpLoggingFilter());
        registrationBean.addUrlPatterns("/api/*");  // IP 로그를 남길 URL 패턴
        return registrationBean;
    }
}
