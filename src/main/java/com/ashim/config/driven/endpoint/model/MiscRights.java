package com.ashim.config.driven.endpoint.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ashimjk on 2/23/2019
 */
@Getter
@Setter
@Builder
@ToString
public class MiscRights {

	private boolean apiAccessible;
	private boolean showSampleName;

}
