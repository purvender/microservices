package com.hooda.microservices.forexconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hooda.microservices.forexconversionservice.model.ForexConversion;


//@FeignClient(name="forex-exchange")
//@FeignClient(name = "forex-exchange", url = "${FOREX_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
@FeignClient(name = "forex-exchange", url = "${FOREX_EXCHANGE_URI:http://localhost}:8000")
public interface ForexExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ForexConversion retrieveValues2 (@PathVariable String from, @PathVariable String to);

}
