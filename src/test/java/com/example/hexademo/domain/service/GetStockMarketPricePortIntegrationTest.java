package com.example.hexademo.domain.service;

import com.example.hexademo.domain.model.DomainModelFaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GetStockMarketPricePortIntegrationTest {

	@Autowired
	private GetStockMarketPricePort subject;

	@Test
	void get() {
		String symbol = DomainModelFaker.fakeStockSymbol();

		Mono<BigDecimal> result = subject.get(symbol)
		                                 .log();

		StepVerifier.create(result)
		            .assertNext(item -> assertThat(item).isGreaterThanOrEqualTo(BigDecimal.ZERO))
		            .verifyComplete();
	}

}
