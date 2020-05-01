package com.example.hexademo.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPosition {

	private String user;

	private String symbol;

	private BigDecimal quantity;

	private String currencyCode;

	private BigDecimal cost;

}
