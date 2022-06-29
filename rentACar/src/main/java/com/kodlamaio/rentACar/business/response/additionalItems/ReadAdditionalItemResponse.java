package com.kodlamaio.rentACar.business.response.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadAdditionalItemResponse {
	private int id;
	private String name;
	private double dailyPrice;

}
