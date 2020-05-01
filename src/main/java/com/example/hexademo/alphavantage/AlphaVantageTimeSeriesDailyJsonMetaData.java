package com.example.hexademo.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageTimeSeriesDailyJsonMetaData {

	@JsonProperty("1. Information")
	private String information;

	@JsonProperty("2. Symbol")
	private String symbol;

	@JsonProperty("3. Last Refreshed")
	private String lastRefreshed;

	@JsonProperty("4. Output Size")
	private String outputSize;

	@JsonProperty("5. Time Zone")
	private String timeZone;

}
