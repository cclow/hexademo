package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.StockPosition;
import reactor.core.publisher.Mono;

public interface StockPositionsRepository {

	Mono<StockPosition> findOneByUsernameAndSymbol(String username, String symbol);

	Mono<Void> deleteAll();

	Mono<StockPosition> insert(StockPosition stockPosition);

}
