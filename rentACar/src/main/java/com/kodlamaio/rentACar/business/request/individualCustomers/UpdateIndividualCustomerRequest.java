package com.kodlamaio.rentACar.business.request.individualCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {

	private int individualCustomerId;
//	private String firstName;
//	private String lastName;
//	private String nationality;
//	private int birthDate;
	private String email;
	private String password;
}
