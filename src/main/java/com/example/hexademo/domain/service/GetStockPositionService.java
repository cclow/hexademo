package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.StockPosition;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetStockPositionService {

	private final StockPositionsRepository repository;

	public GetStockPositionService(StockPositionsRepository repository) {
		this.repository = repository;
	}

	public Mono<StockPosition> get(String username, String symbol) {
		return repository.findOneByUsernameAndSymbol(username, symbol);
	}

}
