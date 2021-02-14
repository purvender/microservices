package com.hooda.microservices.forexexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hooda.microservices.forexexchangeservice.model.CurrencyExchange;
import com.hooda.microservices.forexexchangeservice.service.CurrencyExchangeService;

@RestController
public class ForexExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(ForexExchangeController.class);


	@Autowired
	private Environment environment;

	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveValues(@PathVariable String from, @PathVariable String to) {
		// CurrencyExchange currencyExchange = new
		// CurrencyExchange(1000L,"INR","USD",BigDecimal.valueOf(50));
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		CurrencyExchange currencyExchange = currencyExchangeService.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data from " + from + " to " + to);
		}
		currencyExchange.setEnvironment(
				environment.getProperty("local.server.port") + " " + environment.getProperty("HOSTNAME"));
		return currencyExchange;
	}

}
