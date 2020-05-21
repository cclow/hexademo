package com.example.hexademo.alphavantage;

import com.example.hexademo.domain.service.GetStockMarketPricePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
public class AlphaVantageGetStockMarketPrice implements GetStockMarketPricePort {

	@Value("${alphavantage.api.key}")
	private String apiKey;

	@Override
	public Mono<BigDecimal> get(String symbol) {
		return WebClient.create()
		                .get()
		                .uri("https://www.alphavantage.co/query",
				                uriBuilder -> uriBuilder
						                .queryParam("function", "TIME_SERIES_DAILY")
						                .queryParam("symbol", symbol).queryParam("apikey", apiKey)
						                .build())
		                .retrieve()
		                .bodyToMono(AlphaVantageTimeSeriesDailyJson.class)
		                .map(this::getLatestClosingPrice);
	}

	private BigDecimal getLatestClosingPrice(AlphaVantageTimeSeriesDailyJson json) {
		String lastRefreshed = json.getMetaData()
		                           .getLastRefreshed();
		return BigDecimal.valueOf(Double.parseDouble(
				json.getDaily()
				    .get(lastRefreshed)
				    .getClosingPrice()
		));
	}

}
