package com.example.hexademo.api;

import com.example.hexademo.domain.model.StockPosition;
import com.example.hexademo.domain.service.GetStockMarketValueService;
import com.example.hexademo.domain.service.GetStockPositionService;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.example.hexademo.domain.model.DomainModelFaker.fakeAmount;
import static com.example.hexademo.domain.model.DomainModelFaker.fakeStockPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest
public class GetStockPositionAndMarketValueApiTest {

	private final String username = "peterpan";

	@Autowired
	private WebTestClient client;

	// Domain Service
	@MockBean
	private GetStockPositionService getStockPositionService;

	@MockBean
	private GetStockMarketValueService getStockMarketValueService;

	@Test
	@WithMockUser(username)
	void get() {
		// arrange
		String symbol = "aapl";
		StockPosition fakeStockPosition = fakeStockPosition(username, symbol);
		when(getStockPositionService.get(username, symbol)).thenReturn(Mono.just(fakeStockPosition));
		BigDecimal fakeMarketPrice = fakeAmount();
		when(getStockMarketValueService.get(symbol, fakeStockPosition.getQuantity()))
				.thenReturn(Mono.just(fakeMarketPrice));
		// act
		makeGetRequest(symbol)
				// assert
				.expectStatus().isOk().expectBody(GetStockPositionAndMarketValueApiResponseDto.class)
				.value(dto -> {
					assertThat(dto.getSymbol()).isEqualTo(symbol);
					assertThat(dto.getQuantity().doubleValue())
							.isCloseTo(fakeStockPosition.getQuantity().doubleValue(), Offset.offset(0.01));
					assertThat(dto.getCurrencyCode()).isEqualTo(fakeStockPosition.getCurrencyCode());
					assertThat(dto.getCost().doubleValue())
							.isCloseTo(fakeStockPosition.getCost().doubleValue(), Offset.offset(0.0001));
					assertThat(dto.getMarketValue().doubleValue()).isCloseTo(fakeMarketPrice.doubleValue(),
							Offset.offset(0.0001));
				});
	}

	@Test
	@WithMockUser(username)
	void emptyPosition() {
		String symbol = "appl";
		when(getStockPositionService.get(username, symbol)).thenReturn(Mono.empty());
		when(getStockMarketValueService.get(eq(symbol), any())).thenReturn(Mono.just(fakeAmount()));
		makeGetRequest(symbol).expectStatus().isOk().expectBody(Void.class);
	}

	@Test
	@WithAnonymousUser
	void anonymousGet() {
		makeGetRequest("aapl").expectStatus().isForbidden();
	}

	@Test
	void unauthenticatedGet() {
		makeGetRequest("aapl").expectStatus().isUnauthorized();
	}

	private WebTestClient.ResponseSpec makeGetRequest(String symbol) {
		return client.get().uri("/stock-position-market-value/" + symbol).accept(MediaType.APPLICATION_JSON).exchange();
	}

}
