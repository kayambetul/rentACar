package com.kodlamaio.rentACar.business.request.rental;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIndividualCustomerRentalRequest {
	
	@Min(1)
	private int id;

}
