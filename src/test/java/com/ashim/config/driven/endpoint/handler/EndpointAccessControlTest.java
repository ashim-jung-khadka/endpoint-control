package com.ashim.config.driven.endpoint.handler;

import com.ashim.config.driven.endpoint.model.MiscRights;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;

/**
 * @author ashimjk on 2/25/2019
 */
@SpringBootTest
public class EndpointAccessControlTest extends AbstractTestNGSpringContextTests {

	private static final String SAMPLE_ENDPOINT = "/sample";
	private static final String SAMPLE_NAME_ENDPOINT = "/{sampleName}";

	@Autowired
	private EndpointProperties endpointProperties;

	@Autowired
	private Scenario scenario;

	private EndpointAccessControl accessControl;

	// when valid endpoints null or empty
	@Test(expectedExceptions = IllegalStateException.class)
	public void shouldThrowException_ForEmptyValidEndpoints() {

		EndpointProperties endpointProperties = new EndpointProperties();
		this.accessControl = new EndpointAccessControl(endpointProperties, null);
	}

	// when endpoint properties null
	@Test(expectedExceptions = NullPointerException.class)
	public void shouldThrowException_ForNullProperties() {

		EndpointProperties endpointProperties = null;
		new EndpointAccessControl(endpointProperties, Sets.newLinkedHashSet(SAMPLE_ENDPOINT));

	}

	// when endpoint properties empty
	@Test
	public void shouldGiveTrue_ForEmptyProperties() {

		EndpointProperties endpointProperties = new EndpointProperties();
		EndpointAccessControl endpointAccessControl = new EndpointAccessControl(
				endpointProperties, Sets.newLinkedHashSet(SAMPLE_ENDPOINT));

		MiscRights miscRights = new MiscRights();
		Assert.assertTrue(endpointAccessControl.hasAccessToEndpoint(SAMPLE_ENDPOINT, miscRights));
	}

	@Test(dataProvider = "scenario")
	public void shouldPassAllScenario(Parameter parameter) {

		this.accessControl = new EndpointAccessControl(endpointProperties, this.getValidEndpoints());

		boolean status = this.accessControl.hasAccessToEndpoint(parameter.getUrlPattern(), parameter.getMiscRights());

		Assert.assertEquals(status, parameter.isAccessible());
	}

	@DataProvider(name = "scenario")
	public Object[] getScenario() {
		return scenario.getParameters().toArray();
	}

	private Set<String> getValidEndpoints() {
		return Sets.newHashSet(Arrays.asList(SAMPLE_ENDPOINT, SAMPLE_NAME_ENDPOINT));
	}

}