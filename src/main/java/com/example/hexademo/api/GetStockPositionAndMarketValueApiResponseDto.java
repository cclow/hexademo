package com.example.hexademo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStockPositionAndMarketValueApiResponseDto {

	String symbol;

	Number quantity;

	String currencyCode;

	Number cost;

	Number marketValue;

}
