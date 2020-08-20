/*
package com.example.zuul.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CustomFilter implements Filter {
	private final Logger LOGGER = LoggerFactory.getLogger(CustomFilter.class);
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	EurekaClient eurekaClient;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			return;
		}

		// Obtain Access Token from incoming-request
		String accessToken = this.getRequestAccessToken(servletRequest);

		LOGGER.info("Token: {}", accessToken);

		Token token = this.decodeToken(accessToken);

		LOGGER.info("userId: {}", token.get_id());

		Response response = null;

		if (httpRequest.getMethod().toLowerCase().equals("get")) {
			response = this.fetchUserOrders(token);
		} else if (httpRequest.getMethod().toLowerCase().equals("post")) {
			response = this.postUserOrders(token, null);
		}

		httpResponse = this.setResponse(servletResponse, response);
		return;
	}

	@Override
	public void destroy() {
	}

	private HttpServletResponse setResponse(ServletResponse servletResponse, Response response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		byte[] bytes = response.body().bytes();

		httpResponse.setHeader("content-type", response.header("content-type"));
		httpResponse.getOutputStream().write(bytes);

		return httpResponse;
	}

	private String getRequestAccessToken(ServletRequest servletRequest) {
		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		return httpRequest.getHeader("Authorization");
	}

	private Token decodeToken(String accessToken) throws java.io.IOException {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("user-management-service", false);

		final String homePageUrl = "http://" + instance.getHostName() + ":" + instance.getPort() + "/";

		Request request = new Request.Builder()
				.url(homePageUrl + "decode-token")
				.addHeader("Authorization", accessToken)
				.get()
				.build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful()) {
			Token token = this.mapper.readValue(response.body().string(), Token.class);
			return token;
		}

		return null;
	}

	private Response fetchUserOrders(Token token) throws java.io.IOException {
		Request request = new Request.Builder()
				.url("http://localhost:9001/user-orders")
				.addHeader("userId", token.get_id().toString())
				.get()
				.build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		return response;
	}

	private Response postUserOrders(Token token, RequestBody body) throws java.io.IOException {
		Request request = new Request.Builder()
				.url("http://localhost:9001/user-orders")
				.addHeader("userId", token.get_id().toString())
				.post(body)
				.build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		return response;
	}
}*/
