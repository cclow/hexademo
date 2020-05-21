package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.DomainModelFaker;
import com.example.hexademo.domain.model.StockPosition;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetStockPositionServiceTest {

	private final StockPositionsRepository repository = mock(StockPositionsRepository.class);

	private final GetStockPositionService subject = new GetStockPositionService(repository);

	@Test
	void get() {
		// arrange
		String username = DomainModelFaker.fakeUsername();
		String symbol = DomainModelFaker.fakeStockSymbol();
		StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(username, symbol);
		when(repository.findOneByUsernameAndSymbol(username, symbol))
				.thenReturn(Mono.just(fakeStockPosition));

		// act
		Mono<StockPosition> result = subject.get(username, symbol);

		// assert
		StepVerifier.create(result)
		            .expectNext(fakeStockPosition)
		            .verifyComplete();
	}

}
