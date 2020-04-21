package com.example.hexademo.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockPosition {
	private String symbol;
	private BigDecimal quantity;
	private String currencyCode;
	private BigDecimal cost;
}
