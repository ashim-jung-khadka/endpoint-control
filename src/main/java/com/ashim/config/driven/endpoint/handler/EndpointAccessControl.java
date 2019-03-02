package com.ashim.config.driven.endpoint.handler;

import com.ashim.config.driven.endpoint.model.Endpoint;
import com.ashim.config.driven.endpoint.model.MiscRights;
import com.ashim.config.driven.endpoint.utils.EndpointUtils;
import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author ashimjk on 2/24/2019
 */
class EndpointAccessControl {

	private static String ROOT_URL = "/";

	private Map<String, List<String>> endpointAndRights;

	EndpointAccessControl(EndpointProperties endpointProperties, Set<String> validEndpoints) {

		Preconditions.checkNotNull(validEndpoints, "at least one valid end points should be available");

		this.endpointAndRights = new HashMap<>();
		this.initializeEndpointAndRights(endpointProperties, validEndpoints);
	}

	private void initializeEndpointAndRights(EndpointProperties endpointProperties, Set<String> validEndpoints) {

		for (Endpoint endpoint : endpointProperties.getEndPoints()) {

			String urlPattern = EndpointUtils.normalizeUrlPath(endpoint.getUrlPattern());

			this.validateUrlPattern(urlPattern, validEndpoints);
			this.validateRights(endpoint.getRights());
			urlPattern = this.replaceByPathVariable(urlPattern, endpoint);

			endpointAndRights.put(urlPattern, endpoint.getRights());
		}

	}

	private void validateUrlPattern(String urlPattern, Set<String> validEndpoints) {
		Optional<String> mappedValidEndpoints = validEndpoints.stream()
				.map(EndpointUtils::normalizeUrlPath)
				.filter(endPoint -> endPoint.startsWith(urlPattern))
				.findFirst();

		mappedValidEndpoints.orElseThrow(
				() -> new NoSuchURLException("URL : " + urlPattern + " not valid." + " Valid URLs : " + validEndpoints)
		);
	}

	private void validateRights(List<String> rights) {
		List<String> definedRights = EndpointUtils.getRightsProperties(MiscRights.class);
		Optional<String> mappedRights = rights.stream().filter(definedRights::contains).findFirst();

		mappedRights.orElseThrow(
				() -> new NoSuchRightsException("Rights : " + rights + " not valid.")
		);
	}

	private String replaceByPathVariable(String urlPattern, Endpoint endpoint) {

		String endpointUrl = urlPattern;

		if (!StringUtils.isEmpty(endpoint.getPathVariable())) {
			endpointUrl = endpointUrl.substring(0, endpointUrl.indexOf('{')) + endpoint.getPathVariable();
		}

		return EndpointUtils.normalizeUrlPath(endpointUrl);
	}

	boolean hasAccessToEndpoint(String endpoint, MiscRights miscRights) {

		Preconditions.checkNotNull(endpoint);
		Preconditions.checkNotNull(miscRights);

		return ROOT_URL.equals(endpoint) || checkEndpointAccess(endpoint, miscRights);
	}

	private boolean checkEndpointAccess(String endpoint, MiscRights miscRights) {

		String normalizedEndpoint = EndpointUtils.normalizeUrlPath(endpoint);

		if (endpointAndRights.containsKey(normalizedEndpoint)) {

			return endpointAndRights.get(normalizedEndpoint).stream()
					.allMatch(rights -> EndpointUtils.isRightsEnabled(rights, miscRights));

		} else {
			String parentEndpoint = normalizedEndpoint.replaceFirst("\\w+/$", "");
			return ROOT_URL.equals(parentEndpoint) || checkEndpointAccess(parentEndpoint, miscRights);
		}
	}

	private class NoSuchURLException extends RuntimeException {
		NoSuchURLException(String message) {
			super(message);
		}
	}

	private class NoSuchRightsException extends RuntimeException {
		NoSuchRightsException(String message) {
			super(message);
		}
	}
}
