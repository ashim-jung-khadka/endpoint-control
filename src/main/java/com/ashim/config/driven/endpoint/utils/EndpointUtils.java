package com.ashim.config.driven.endpoint.utils;

import com.ashim.config.driven.endpoint.model.MiscRights;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 2/24/2019
 */
public class EndpointUtils {

	private static Logger logger = LoggerFactory.getLogger(EndpointUtils.class);

	private EndpointUtils() {
	}

	public static String normalizeUrlPath(String path) {
		path = path.trim();

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		return path;
	}

	public static List<String> getRightsProperties(Class clazz) {

		List<String> properties = new ArrayList<>();

		try {
			for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {

				Class<?> returnType = propertyDescriptor.getReadMethod().getReturnType();

				if (returnType == Boolean.class || returnType == boolean.class) {
					properties.add(propertyDescriptor.getName());
				}

			}
		} catch (IntrospectionException e) {
			logger.info("Error while reading property of {}", clazz.getName());
			throw new RuntimeException(e);
		}

		return properties;
	}

	public static boolean isRightsEnabled(String name, MiscRights userMiscRights) {
		try {
			return (boolean) PropertyUtils.getProperty(userMiscRights, name);
		} catch (Exception e) {
			return false;
		}
	}
}
