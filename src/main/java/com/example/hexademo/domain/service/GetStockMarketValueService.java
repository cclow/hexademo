package com.example.hexademo.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class GetStockMarketValueService {

	private final GetStockMarketPricePort getStockMarketPricePort;

	public GetStockMarketValueService(GetStockMarketPricePort getStockMarketPricePort) {
		this.getStockMarketPricePort = getStockMarketPricePort;
	}

	public Mono<BigDecimal> get(String symbol, BigDecimal quantity) {
		return getStockMarketPricePort
				.get(symbol)
				.map(price -> price.multiply(quantity));
	}

}
