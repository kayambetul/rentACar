package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.request.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.response.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.business.response.addresses.ReadAddressResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AddressRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;

@Service
public class AddressManager implements AddressService {

	
	UserService userService;         //her kullanıcın bir adresi olur.
	ModelMapperService modelMapperService;
	AddressRepository addressRepository;

	@Autowired
	public AddressManager(UserService userService, ModelMapperService modelMapperService,
			AddressRepository addressRepository) {
	
		this.userService = userService;
		this.modelMapperService = modelMapperService;
		this.addressRepository = addressRepository;
	}

	@Override
	public Result addSameAddress(CreateAddressRequest createAddressRequest) {    //EĞER FATURA ADRESİYE AYNIYSA SET ET
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		address.setBillAddress(createAddressRequest.getDeliveryAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDED.ADDRESS");

	}

	@Override
	public Result addDifferentAddress(CreateAddressRequest createAddressRequest) {
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDED.ADDRESS");
	}

	@Override
	public Result updateSameAddress(UpdateAddressRequest updateAddressRequest) {
		checkIfAddressExistsById(updateAddressRequest.getId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("UPDATE.ADDRESS");
	}

	@Override
	public Result updateDifferentAddress(UpdateAddressRequest updateAddressRequest) {
		checkIfAddressExistsById(updateAddressRequest.getId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("UPDATE.ADDRESS");
		
	}
	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		checkIfAddressExistsById(deleteAddressRequest.getId());
		Address address = this.modelMapperService.forRequest().map(deleteAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("DELETE.ADDRESS");
	}
	@Override
	public DataResult<ReadAddressResponse> getById(int id) {
		Address address=checkIfAddressExistsById(id);
		ReadAddressResponse readAddressResponse=this.modelMapperService.forResponse().map(address, ReadAddressResponse.class);
		return new SuccessDataResult<ReadAddressResponse>(readAddressResponse);
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAll() {
		List<Address> addresses = this.addressRepository.findAll();
		List<GetAllAddressesResponse> responses = addresses.stream().map(address -> this.modelMapperService.forResponse().
				map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(responses);
	}
	
	
	private Address checkIfAddressExistsById(int id) {
		Address currentAddress;
		try {
			currentAddress = this.addressRepository.findById(id).get();
		} catch (Exception e) {
			throw new BusinessException("ADDRESS.NOT.EXISTS");
		}
		return currentAddress;
	}

	@Override
	public Address getAddressById(int id) {
		// TODO Auto-generated method stub
		return checkIfAddressExistsById(id);
	}

	

	



}
