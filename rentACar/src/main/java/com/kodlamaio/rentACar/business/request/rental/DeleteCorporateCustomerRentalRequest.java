package com.kodlamaio.rentACar.business.request.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class DeleteCorporateCustomerRentalRequest {
	private int corporateCustomerId;

}
