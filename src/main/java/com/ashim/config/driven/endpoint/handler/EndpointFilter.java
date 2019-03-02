package com.ashim.config.driven.endpoint.handler;

import com.ashim.config.driven.endpoint.model.MiscRights;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ashimjk on 2/24/2019
 */
@Component
public class EndpointFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(EndpointFilter.class);

	private MiscRights miscRights;
	private EndpointAccessControl endpointAccessControl;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	public EndpointFilter(EndpointProperties endpointProperties, RequestMappingHandlerMapping handlerMapping) {

		Set<String> validEndpoints = this.getAllValidEndpoints(handlerMapping);

		this.miscRights = MiscRights.builder().apiAccessible(true).showSampleName(false).build();
		this.endpointAccessControl = new EndpointAccessControl(endpointProperties, validEndpoints);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestedURI = request.getRequestURI().replaceFirst(contextPath, "");

		if (endpointAccessControl.hasAccessToEndpoint(requestedURI, miscRights)) {

			chain.doFilter(servletRequest, servletResponse);

		} else {
			sendUnauthorizedResponse(servletResponse);
		}
	}

	private Set<String> getAllValidEndpoints(RequestMappingHandlerMapping handlerMapping) {

		return handlerMapping.getHandlerMethods()
				.keySet()
				.stream()
				.map(x -> x.getPatternsCondition().getPatterns().iterator().next())
				.collect(Collectors.toSet());
	}

	private void sendUnauthorizedResponse(ServletResponse servletResponse) throws IOException {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print(this.getErrorResponse());
		response.getWriter().flush();
	}

	private String getErrorResponse() {

		Map<String, String> errorMessage = ImmutableMap.of(
				"error", "Access denied to requested endpoint",
				"status", HttpStatus.UNAUTHORIZED.getReasonPhrase()
		);

		String errorResponse;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			errorResponse = objectMapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			logger.info("Error while creating error message for access denied");
			errorResponse = "{}";
		}

		return errorResponse;
	}

}
