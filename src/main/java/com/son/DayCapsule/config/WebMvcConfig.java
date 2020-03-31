package com.son.DayCapsule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** Bean에 등록된 interceptor를 주입 받는다 **/
    @Autowired
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         addPathPatterns : interceptor 적용 시킬 경로
         excludePathPatterns : interceptor 적용 제회할 경로
         */
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user/main");

    }

}
