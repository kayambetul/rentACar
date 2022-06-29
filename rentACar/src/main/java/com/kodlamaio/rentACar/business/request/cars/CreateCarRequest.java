package com.kodlamaio.rentACar.business.request.cars;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	private String description;
	private double dailyPrice;
	@NotNull
	@Min(1)
	private int brandId;
	@NotNull
	@Min(1)
	private int colorId;
	private String plate;
	private int kilometer;
	@NotNull
	@Min(1)
	private int cityId;
	private int minFindex;

}
