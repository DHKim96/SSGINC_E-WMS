package com.ssginc.ewms;

import com.ssginc.ewms.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터 적용을 위한 웹 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 첫 번째로 적용될 인터셉터
                .addPathPatterns("/**") // 하위에 전부 적용
                .excludePathPatterns("/", "/login", "/logout", "/registration", "/img/**", "/css/**", "/*.ico", "/error"); // 예외
    }
}
