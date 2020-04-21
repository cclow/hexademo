package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.StockPosition;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class GetStockPositionService {
	public Mono<StockPosition> get(
			String user,
			String symbol
	) {
		return Mono.empty();
	}
}
