package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.response.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.response.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Address;

public interface AddressService {
	
	Result addSameAddress(CreateAddressRequest createAddressRequest);
	Result addDifferentAddress(CreateAddressRequest createAddressRequest);
	
	Result updateSameAddress(UpdateAddressRequest updateAddressRequest);
	Result updateDifferentAddress(UpdateAddressRequest updateAddressRequest);
	
	Result delete(DeleteAddressRequest deleteAddressRequest);
	
	DataResult<ReadAddressResponse> getById(int id);

	DataResult<List<GetAllAddressesResponse>> getAll();

	public Address getAddressById(int id);

}
