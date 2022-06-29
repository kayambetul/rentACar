package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.rental.CreateCorporateCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.CreateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.DeleteIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.UpdateCorporateCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.UpdateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.response.rentals.GetAllRentalResponse;
import com.kodlamaio.rentACar.business.response.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Rental;

public interface RentalService {
	Result addIndividual(CreateIndividualCustomerRentalRequest createIndividualCustomerRentalRequest);
	
	Result addCorporete(CreateCorporateCustomerRentalRequest createCorporateCustomerRentalRequest);

	Result updateIndividual(UpdateIndividualCustomerRentalRequest updateIndividualCustomerRentalRequest);
	
	Result updateCorporate(UpdateCorporateCustomerRentalRequest updateCorporateCustomerRentalRequest);

	Result deleteIndividual(DeleteIndividualCustomerRentalRequest deleteIndividualCustomerRentalRequest);
	
	
	DataResult<ReadRentalResponse> getById(int id);

	DataResult<List<GetAllRentalResponse>> getAll();

	public Rental getRentalById(int id);
}
