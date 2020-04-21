package com.example.hexademo.domain.service;

import java.math.BigDecimal;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class GetStockMarketValueService {
	public Mono<BigDecimal> get(
			String symbol,
			BigDecimal quantity) {
		return Mono.empty();
	}
}
