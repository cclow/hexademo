package com.example.hexademo.domain.service;

import java.math.BigDecimal;

import reactor.core.publisher.Mono;

public interface GetStockMarketPricePort {

	Mono<BigDecimal> get(String symbol);

}
