package com.kodlamaio.rentACar.business.request.orderedAdditionalItems;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalItemRequest {
	private int id;
	private int additionalItemId;
	private int rentalId;
	private int totalDays;
	private LocalDate pickupDate;
}
