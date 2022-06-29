package com.kodlamaio.rentACar.business.request.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

	private int corporateCustomerId;
	private String corporetName;
	private String taxNumber;
}
