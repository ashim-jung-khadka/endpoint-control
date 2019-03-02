package com.ashim.config.driven.endpoint.handler;

import com.ashim.config.driven.endpoint.model.MiscRights;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author ashimjk on 3/2/2019
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("scenario")
public class Scenario {

	private List<Parameter> parameters;

}

@Getter
@Setter
@ToString
class Parameter {

	private String urlPattern;
	private MiscRights miscRights = new MiscRights();
	private boolean accessible;

	public void setRights(List<String> rights) {

		this.miscRights = MiscRights.builder().build();

		for (String right : rights) {
			try {
				PropertyUtils.setProperty(miscRights, right, Boolean.TRUE);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

	}
}

