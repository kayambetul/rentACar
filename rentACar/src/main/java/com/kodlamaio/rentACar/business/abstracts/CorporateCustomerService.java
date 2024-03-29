package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.corporateCustomer.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomer.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomer.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.response.corporateCustomer.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.business.response.corporateCustomer.ReadCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

	DataResult<ReadCorporateCustomerResponse> getById(int id);

	DataResult<List<GetAllCorporateCustomerResponse>> getAll();
	
	public CorporateCustomer getCorporateCustomerById(int id);
	
	

}


