package com.example.hexademo.mongo;

import com.example.hexademo.domain.model.StockPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StockPositionDocument extends StockPosition {

	@Id
	private ObjectId id;

}
