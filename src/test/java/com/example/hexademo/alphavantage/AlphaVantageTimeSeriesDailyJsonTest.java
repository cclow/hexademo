package com.example.hexademo.alphavantage;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

class AlphaVantageTimeSeriesDailyJsonTest {

	@SneakyThrows
	@Test
	void matchesJson() {
		ObjectMapper mapper = new ObjectMapper();

		File file = new ClassPathResource("alphavantage-samples/time-series-daily.json").getFile();
		AlphaVantageTimeSeriesDailyJson json = mapper.readValue(file, AlphaVantageTimeSeriesDailyJson.class);

		assertThat(json).isNotNull();
	}

}
