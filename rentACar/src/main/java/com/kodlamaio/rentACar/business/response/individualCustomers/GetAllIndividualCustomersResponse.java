package com.kodlamaio.rentACar.business.response.individualCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllIndividualCustomersResponse {
	private int individualCustomerId;
	private String firstName;
	private String lastName;
	private String nationality;
	private int birthDate;
}
