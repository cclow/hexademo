package com.example.hexademo.api;

import com.example.hexademo.domain.service.GetStockMarketValueService;
import com.example.hexademo.domain.service.GetStockPositionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class StockPositionsController {

	private final GetStockPositionService getStockPositionService;

	private final GetStockMarketValueService getStockMarketValueService;

	public StockPositionsController(
			GetStockPositionService getStockPositionService,
			GetStockMarketValueService getStockMarketValueService) {
		this.getStockPositionService = getStockPositionService;
		this.getStockMarketValueService = getStockMarketValueService;
	}

	@GetMapping("/stock-position-market-value/{symbol}")
	Mono<GetStockPositionAndMarketValueApiResponseDto> getPositionAndMarketValue(
			@AuthenticationPrincipal Mono<Principal> principalMono,
			@PathVariable String symbol) {
		return principalMono.flatMap(principal -> getStockPositionService
				.get(principal.getName(), symbol))
		                    .zipWhen(stockPosition -> getStockMarketValueService
						                    .get(symbol, stockPosition.getQuantity()),
				                    (stockPosition, marketValue) ->
						                    new GetStockPositionAndMarketValueApiResponseDto(
								                    symbol,
								                    stockPosition.getQuantity(),
								                    stockPosition.getCurrencyCode(),
								                    stockPosition.getCost(),
								                    marketValue));
	}

}
