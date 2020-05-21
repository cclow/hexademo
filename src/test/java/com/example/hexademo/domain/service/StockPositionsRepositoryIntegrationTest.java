package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.DomainModelFaker;
import com.example.hexademo.domain.model.StockPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class StockPositionsRepositoryIntegrationTest {

	@Autowired
	private StockPositionsRepository repository;

	@Test
	void findByUsernameAndSymbol() {
		String username = DomainModelFaker.fakeUsername();
		String symbol = DomainModelFaker.fakeStockSymbol();
		StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(username, symbol);
		// seed database
		repository.deleteAll()
		          .then(repository.insert(fakeStockPosition))
		          .block(); // make sure it's completed first

		// act
		Mono<StockPosition> result = repository.findOneByUsernameAndSymbol(username, symbol);

		// assert
		StepVerifier.create(result)
		            .assertNext(stockPosition -> assertThat(stockPosition)
				            .isEqualToComparingFieldByField(fakeStockPosition))
		            .verifyComplete();
	}

	@Test
	void findByUserAndSymbolEmpty() {
		String username = DomainModelFaker.fakeUsername();
		String symbol = DomainModelFaker.fakeStockSymbol();
		// seed database
		repository.deleteAll().block();

		Mono<StockPosition> result = repository.findOneByUsernameAndSymbol(username, symbol);

		StepVerifier.create(result).verifyComplete();
	}

}
