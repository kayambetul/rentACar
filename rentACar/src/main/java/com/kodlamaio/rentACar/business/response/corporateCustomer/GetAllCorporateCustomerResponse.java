package com.kodlamaio.rentACar.business.response.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCorporateCustomerResponse {

	private int corporateCustomerId;
	private String corporetName;
	private String taxNumber;
}
