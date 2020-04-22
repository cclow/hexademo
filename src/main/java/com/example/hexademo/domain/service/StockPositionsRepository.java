package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.StockPosition;
import reactor.core.publisher.Mono;

public interface StockPositionsRepository {
	Mono<StockPosition> findOneByUserAndSymbol(
			String user,
			String symbol);

	Mono<Void> deleteAll();

	Mono<StockPosition> insert(StockPosition stockPosition);
}
