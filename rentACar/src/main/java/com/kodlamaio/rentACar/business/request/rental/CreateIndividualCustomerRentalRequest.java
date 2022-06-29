package com.kodlamaio.rentACar.business.request.rental;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor


public class CreateIndividualCustomerRentalRequest {
	@NotNull
	@FutureOrPresent
	private LocalDate pickupDate;
	@NotNull
	@Min(1)
	private int totalDays;
	@Min(1)
	private int carId;
	@Min(1)
	@Max(81)
	@NotNull
	private int pickUpCityId;
	@Min(1)
	@Max(81)
	@NotNull
	private int returnCityId;
	
	@Min(1)
	private int individualCustomerId;
	
	

}
