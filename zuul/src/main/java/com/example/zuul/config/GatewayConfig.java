package com.example.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.zuul.filters.ErrorFilter;
import com.example.zuul.filters.PostFilter;
import com.example.zuul.filters.PreFilter;
import com.example.zuul.filters.RouteFilter;

@Configuration
public class GatewayConfig {
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
}
