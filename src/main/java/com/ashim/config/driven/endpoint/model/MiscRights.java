package com.ashim.config.driven.endpoint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ashimjk on 2/23/2019
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MiscRights {

	private boolean apiAccessible;
	private boolean showSampleName;

}
