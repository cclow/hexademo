package com.example.hexademo.alphavantage;

import java.math.BigDecimal;
import java.util.Map;

import com.example.hexademo.domain.service.GetStockMarketPricePort;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class AlphaVantageGetStockMarketPrice implements GetStockMarketPricePort {
	@Value("${alphavantage.apiKey}")
	private String apiKey;

	@Override
	public Mono<BigDecimal> get(String symbol) {
		return WebClient.create().get().uri("https://www.alphavantage.co/query",
				uriBuilder -> uriBuilder.queryParam("function", "TIME_SERIES_DAILY")
						.queryParam("symbol", symbol)
						.queryParam("apikey", apiKey)
						.build()
		)
				.retrieve()
				.bodyToMono(AlphaVantageTimeSeriesDailyJson.class)
				.map(json -> getLatestClosingPrice(json.getDaily()));
	}

	private BigDecimal getLatestClosingPrice(Map<String, AlphaVantageTimeSeriesDailyJsonDaily> daily) {
		String latest = "";
		for (String key: daily.keySet()) {
			if (key.compareToIgnoreCase(latest) > 0) {
				latest = key;
			}
		}
		return BigDecimal.valueOf(Double.parseDouble(daily.get(latest).getClosingPrice()));
	}
}
