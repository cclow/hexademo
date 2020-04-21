package com.example.hexademo.domain.model;

import java.math.BigDecimal;

import com.github.javafaker.Faker;

public class DomainModelFaker {
	private static Faker faker = Faker.instance();
	
	public static StockPosition fakeStockPosition(String symbol) {
		return new StockPosition(
				symbol,
				fakeQuantity(),
				faker.currency().code(),
				fakeAmount()
		);
	}

	public static BigDecimal fakeQuantity() {
		return BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000));
	}

	public static BigDecimal fakeAmount() {
		return BigDecimal.valueOf(faker.number().randomDouble(4, 0, 10000000));
	}

	public static String fakeUser() {
		return faker.name().username();
	}

	public static String fakeStockSymbol() {
		return faker.stock().nsdqSymbol();
	}
}
