package com.hooda.microservices.forexconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hooda.microservices.forexconversionservice.configuration.Configuration;
import com.hooda.microservices.forexconversionservice.model.ForexConversion;
import com.hooda.microservices.forexconversionservice.proxy.ForexExchangeProxy;

@RestController
public class ForexConversionController {
	
	private Logger logger = LoggerFactory.getLogger(ForexConversionController.class);

	@Autowired
	private ForexExchangeProxy proxy;
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ForexConversion retrieveConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		logger.info("calculateCurrencyConversion called with {} to {} with {}", from, to, quantity);

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<ForexConversion> responseEntity = new RestTemplate().getForEntity(
				configuration.getRestUrl(), ForexConversion.class, uriVariables);
		ForexConversion forexConversion = responseEntity.getBody();
		return new ForexConversion(forexConversion.getId(), from, to, forexConversion.getConversionMultiple(),
				forexConversion.getEnvironment(), quantity, quantity.multiply(forexConversion.getConversionMultiple()));

	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public ForexConversion retrieveConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		ForexConversion forexConversion = proxy.retrieveValues2(from, to);
		return new ForexConversion(forexConversion.getId(), from, to, forexConversion.getConversionMultiple(),
				forexConversion.getEnvironment(), quantity, quantity.multiply(forexConversion.getConversionMultiple()));

	}

}
