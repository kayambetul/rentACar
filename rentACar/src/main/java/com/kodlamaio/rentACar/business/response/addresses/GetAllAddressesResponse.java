package com.kodlamaio.rentACar.business.response.addresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAddressesResponse {
	private int id;
	private String deliveryAddress;
	private String billAddress;
	

}
