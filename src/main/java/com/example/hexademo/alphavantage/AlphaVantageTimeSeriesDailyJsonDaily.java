package com.example.hexademo.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageTimeSeriesDailyJsonDaily {

	@JsonProperty("1. open")
	private String openingPrice;

	@JsonProperty("2. high")
	private String highPrice;

	@JsonProperty("3. low")
	private String lowPrice;

	@JsonProperty("4. close")
	private String closingPrice;

	@JsonProperty("5. volume")
	private String volume;

}
