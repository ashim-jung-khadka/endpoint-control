package com.ashim.config.driven.endpoint.handler;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ashimjk on 2/25/2019
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EndpointAccessControlTest {

	// should handle with properties file is not present
	// check empty url pattern
	// check empty rights
	// check path variable
	// check all rights are true
	// check both url pattern and rights exist
	// should throw exception when valid path is null or empty
	// should throw exception when url pattern is not valid path
	// should throw exception when rights is not present
	// check for requested endpoint match
	// check for requested parent endpoint match
	// check for requested no endpoint match
	// check for exception that can be raised
	// check for path variable
}