package com.kodlamaio.rentACar.business.request.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalItemRequest {
	
	private String name;
	
	private double dailyPrice;
	

}
