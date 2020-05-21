package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.DomainModelFaker;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetStockMarketValueServiceTest {

	private GetStockMarketPricePort getStockMarketPricePort = mock(GetStockMarketPricePort.class);

	private GetStockMarketValueService subject = new GetStockMarketValueService(getStockMarketPricePort);

	@Test
	void get() {
		// arrange
		String symbol = DomainModelFaker.fakeStockSymbol();
		BigDecimal fakeQuantity = DomainModelFaker.fakeQuantity();
		BigDecimal fakePrice = DomainModelFaker.fakeAmount();
		
		when(getStockMarketPricePort.get(symbol))
				.thenReturn(Mono.just(fakePrice));

		// act
		Mono<BigDecimal> result = subject.get(symbol, fakeQuantity);

		// assert
		StepVerifier.create(result)
		            .expectNextMatches(amount -> amount.equals(fakeQuantity.multiply(fakePrice)))
		            .verifyComplete();
	}

}
