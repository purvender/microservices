package com.hooda.microservices.forexexchangeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hooda.microservices.forexexchangeservice.model.CurrencyExchange;
import com.hooda.microservices.forexexchangeservice.repository.CurrencyExchangeRepository;

@Service
public class CurrencyExchangeService {
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	public CurrencyExchange findByFromAndTo(String from,String to) {
		return repository.findByFromAndTo(from, to);
	}

}
