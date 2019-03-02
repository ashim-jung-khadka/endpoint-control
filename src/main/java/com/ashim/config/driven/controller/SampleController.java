package com.ashim.config.driven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ashimjk on 3/1/2019
 */
@RestController()
public class SampleController {

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String getSample() {
		return "Sample";
	}

	@GetMapping("/{sampleName}")
	public String getSampleName(@PathVariable String sampleName) {
		return sampleName;
	}
}
