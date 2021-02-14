package com.hooda.microservices.forexexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hooda.microservices.forexexchangeservice.model.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

	CurrencyExchange findByFromAndTo(String from, String to);
}
