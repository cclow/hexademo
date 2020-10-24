package com.example.hexademo.api;

import com.example.hexademo.domain.model.DomainModelFaker;
import com.example.hexademo.domain.model.StockPosition;
import com.example.hexademo.domain.service.StockPositionsRepository;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class GetStockPositionAndMarketValueApiE2ETest {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private StockPositionsRepository repository;

	private final String username = "peterpan";

	@Test
	@WithMockUser(username)
	void getStockPositionAndMarketValue() {
		final WebTestClient client = WebTestClient.bindToApplicationContext(context).build();
		// arrange
		String symbol = DomainModelFaker.fakeStockSymbol();
		StockPosition fakeStockPosition = DomainModelFaker.fakeStockPosition(username, symbol);
		// seed database
		repository.deleteAll().then(repository.insert(fakeStockPosition)).block();

		// act
		client.get().uri("/stock-position-market-value/" + symbol).accept(MediaType.APPLICATION_JSON).exchange()

				// assert
				.expectStatus().isOk().expectBody(GetStockPositionAndMarketValueApiResponseDto.class)
				.value(dto -> {
					assertThat(dto.getSymbol()).isEqualTo(symbol);
					assertThat(dto.getQuantity().doubleValue())
							.isCloseTo(fakeStockPosition.getQuantity().doubleValue(), Offset.offset(0.01));
					assertThat(dto.getCurrencyCode()).isEqualTo(fakeStockPosition.getCurrencyCode());
					assertThat(dto.getCost().doubleValue())
							.isCloseTo(fakeStockPosition.getCost().doubleValue(), Offset.offset(0.0001));
					assertThat(dto.getMarketValue().doubleValue()).isGreaterThan(0.0);
				});
	}

}
