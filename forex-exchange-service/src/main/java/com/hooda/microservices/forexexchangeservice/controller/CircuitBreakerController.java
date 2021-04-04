package com.hooda.microservices.forexexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	//@Retry(name="sample-api",fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name="default",fallbackMethod = "hardCodedResponse")
	@RateLimiter(name="default")
	@Bulkhead(name="default")
	public String sampleApi() {
		logger.info("Sample apicall received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http:localhost:8080", String.class);
		return forEntity.getBody();
	}
	public String hardCodedResponse(Exception e) {
		return "fallback response";
	}
}
