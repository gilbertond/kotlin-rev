package com.config

import com.security.CustomSecurityAuthenticationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//Used to register our custom interceptor in spring: `CustomSecurityAuthenticationInterceptor`
@Configuration
class WebMvcConfiguration: WebMvcConfigurer {

    @Autowired
    @Qualifier("customAuthenticationInterceptor")
    private val customSecurityAuthenticationInterceptor: CustomSecurityAuthenticationInterceptor? = null

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(customSecurityAuthenticationInterceptor).excludePathPatterns("/").addPathPatterns("/secured/api/**")
    }
}