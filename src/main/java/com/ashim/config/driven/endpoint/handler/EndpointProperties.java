package com.ashim.config.driven.endpoint.handler;

import com.ashim.config.driven.endpoint.model.Endpoint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ashimjk on 2/23/2019
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("access-control")
public class EndpointProperties {

	private List<Endpoint> endPoints;

}
