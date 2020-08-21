package com.example.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class PreFilter extends ZuulFilter {

	private final Logger LOGGER = LoggerFactory.getLogger(PreFilter.class);
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	EurekaClient eurekaClient;

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest httpRequest = ctx.getRequest();

		// ignore routes that are used to obtain access-token
		if (httpRequest.getRequestURI().toLowerCase().equals("/user/authentication"))
			return ctx;

		if (httpRequest.getRequestURI().toLowerCase().equals("/user/users")
				&& httpRequest.getMethod().toLowerCase().equals("post")) {
			return ctx;
		}

		String accessToken = this.getRequestAccessToken(httpRequest);

		if (accessToken == null || accessToken.equals("")) {
			ctx.unset();
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}

		try {
			Token token = this.decodeToken(ctx, accessToken);

			if (token == null) {
				ctx.unset();
				ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			}

			ctx.addZuulRequestHeader("userid", token.get_id());

			return ctx;
		} catch (Exception e) {
			return null;
		}
	}

	private String getRequestAccessToken(HttpServletRequest httpRequest) {
		return httpRequest.getHeader("Authorization");
	}

	private Token decodeToken(RequestContext ctx, String accessToken) throws java.io.IOException {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("user-management-service", false);

		if (instance == null)
			return null;

		final String homePageUrl = ctx.getRequest().getScheme().concat("://") + instance.getHostName() + ":"
				+ instance.getPort() + "/";

		LOGGER.info("HomePageURL Builder: {}", this.buildHomePageUrl(ctx, instance));

		Request request = new Request.Builder().url(homePageUrl + "decode-token").addHeader("Authorization", accessToken)
				.get().build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful()) {
			Token token = this.mapper.readValue(response.body().string(), Token.class);
			return token;
		}

		return null;
	}

	private String buildHomePageUrl(RequestContext ctx, InstanceInfo instanceInfo) {
		StringBuilder homePageUrl = new StringBuilder("");

		homePageUrl.append(ctx.getRequest().getScheme());
		homePageUrl.append("://");
		homePageUrl.append(instanceInfo.getHostName());
		homePageUrl.append(":");
		homePageUrl.append(instanceInfo.getPort());

		if (homePageUrl == null || homePageUrl.toString().equals("")) {
			return "";
		}

		return homePageUrl.toString();
	}
}