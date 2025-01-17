package com.woowa.woowakit.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.woowa.woowakit.global.argument.AuthPrincipalResolver;
import com.woowa.woowakit.global.interceptor.AuthenticationInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;
	private final AuthPrincipalResolver authPrincipalResolver;

	@Value("${cors.allowed-origin}")
	private String allowedOrigin;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry
			.addInterceptor(authenticationInterceptor)
			.addPathPatterns("/**")
			.order(0);
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authPrincipalResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins(allowedOrigin)
			.allowedMethods("*");
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
