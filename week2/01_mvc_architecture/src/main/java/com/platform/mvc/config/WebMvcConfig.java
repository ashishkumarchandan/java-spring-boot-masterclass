package com.platform.mvc.config;

import com.platform.mvc.interceptor.RequestLifecycleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestLifecycleInterceptor requestLifecycleInterceptor;

    public WebMvcConfig(RequestLifecycleInterceptor requestLifecycleInterceptor) {
        this.requestLifecycleInterceptor = requestLifecycleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLifecycleInterceptor).addPathPatterns("/api/**");
    }
}
