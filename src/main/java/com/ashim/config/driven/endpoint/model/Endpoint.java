package com.ashim.config.driven.endpoint.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author ashimjk on 2/23/2019
 */
@Getter
@Setter
@ToString
public class Endpoint {

	@NotBlank(message = "url pattern should not be null or blank")
	private String urlPattern;

	private String pathVariable;

	@NotEmpty(message = "rights should not be null or empty")
	private List<String> rights;

}
