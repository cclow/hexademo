package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.DomainModelFaker;
import com.example.hexademo.domain.model.StockPosition;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataMongoTest
class StockPositionsRepositoryIntegrationTest {
	@Autowired
	private StockPositionsRepository repository;

	@Test
	void findByUserAndSymbol() {
		String user = DomainModelFaker.fakeUser();
		String symbol = DomainModelFaker.fakeStockSymbol();
		StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(user, symbol);
		// seed database
		repository.deleteAll()
				.then(repository.insert(fakeStockPosition))
				.block(); // make sure it's completed first

		// act
		Mono<StockPosition> result = repository.findOneByUserAndSymbol(user, symbol);

		// assert
		StepVerifier.create(result)
				.assertNext(stockPosition ->
						assertAll(
								() -> assertThat(stockPosition).isEqualToComparingFieldByField(fakeStockPosition)
						)
				)
				.verifyComplete();
	}

	@Test
	void findByUserAndSymbolEmpty() {
		String user = DomainModelFaker.fakeUser();
		String symbol = DomainModelFaker.fakeStockSymbol();
		// seed database
		repository.deleteAll()
				.block();

		Mono<StockPosition> result = repository.findOneByUserAndSymbol(user, symbol);

		StepVerifier.create(result)
				.verifyComplete();
	}
}
