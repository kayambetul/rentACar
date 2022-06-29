package com.kodlamaio.rentACar.business.request.rental;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class UpdateCorporateCustomerRentalRequest {
	private int corporateCustomerId;
	private int totalDays;
	private int carId;
	private LocalDate pickupDate;
	private int pickUpCityId;
	private int returnCityId;
	private int userId;
}
