package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.abstracts.UserCheckService;
import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.response.individualCustomers.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.response.individualCustomers.ReadIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	IndividualCustomerRepository individualCustomerRepository;

	ModelMapperService modelMapperService;

	UserCheckService userCheckService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerRepository individualCustomerRepository,
			ModelMapperService modelMapperService, UserCheckService userCheckService) {
		this.individualCustomerRepository = individualCustomerRepository;
		this.modelMapperService = modelMapperService;
		this.userCheckService = userCheckService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		checkIfIndividualCustomerExistsByNationalityId(createIndividualCustomerRequest.getNationality());
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);
		checkIfRealIndividualCustomer(individualCustomer);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("INDIVIDUAL.CUSTOMER.ADDED");
	}


	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(deleteIndividualCustomerRequest, IndividualCustomer.class);
		checkIfIndividualCustomerExistsById(deleteIndividualCustomerRequest.getIndividualCustomerId());
		this.individualCustomerRepository.delete(individualCustomer);
		return new SuccessResult("INDIVIDUAL.CUSTOMER.DELETED");
	}
		
	
	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		checkIfIndividualCustomerExistsById(updateIndividualCustomerRequest.getIndividualCustomerId());
		IndividualCustomer individualCustomer=this.modelMapperService.forRequest().map(updateIndividualCustomerRequest,IndividualCustomer.class );
		setIndividualCustomer(individualCustomer,updateIndividualCustomerRequest.getIndividualCustomerId());
		this.individualCustomerRepository.save(individualCustomer);
		
		return  new SuccessResult("INDIVIDUAL_CUSTOMER.UPDATED");
	}

	
	@Override
	public DataResult<ReadIndividualCustomerResponse> getById(int id) {
		
		IndividualCustomer individualCustomer = checkIfIndividualCustomerExistsById(id);
		ReadIndividualCustomerResponse readIndividualCustomerResponse = this.modelMapperService.forResponse()
				.map(individualCustomer, ReadIndividualCustomerResponse.class);
		return new SuccessDataResult<ReadIndividualCustomerResponse>(readIndividualCustomerResponse);
	}
	

	@Override
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll();
		List<GetAllIndividualCustomersResponse> response = individualCustomers.stream()
				.map(individualCustomer -> this.modelMapperService.forResponse().map(individualCustomer,
						GetAllIndividualCustomersResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllIndividualCustomersResponse>>(response);
	}
	

	private IndividualCustomer checkIfIndividualCustomerExistsById(int id) {
		IndividualCustomer currentIndividualCustomer;
		try {
			currentIndividualCustomer = this.individualCustomerRepository.findById(id).get();
		} catch (Exception e) {
			throw new BusinessException("USER.NOT.EXISTS");
		}
		return currentIndividualCustomer;

	}

	private void checkIfIndividualCustomerExistsByNationalityId(String nationality) {
		IndividualCustomer currentNationalityId = this.individualCustomerRepository.findByNationality(nationality);
		if (currentNationalityId != null) {
			throw new BusinessException("INDIVIDUAL_CUSTOMER.EXİST");
		}

	}

	private void checkIfRealIndividualCustomer(IndividualCustomer individualCustomer) {
		if (!this.userCheckService.checkIfRealPerson(individualCustomer)) {
			throw new BusinessException("COULD.NOT.INDIVIDUAL_CUSTOMER.ADDED");
		}
	}
	
	private void setIndividualCustomer(IndividualCustomer individualCustomer,int id) { //güncelleme sadece email/passwordda olabilir.
		IndividualCustomer tempCustomer=this.individualCustomerRepository.findById(id).get();
		individualCustomer.setFirstName(tempCustomer.getFirstName());
		individualCustomer.setLastName(tempCustomer.getLastName());
		individualCustomer.setNationality(tempCustomer.getNationality());
		individualCustomer.setBirthDate(tempCustomer.getBirthDate());
			
	}

	@Override
	public IndividualCustomer getIndividualCustomerById(int id) {
		// TODO Auto-generated method stub
		return checkIfIndividualCustomerExistsById(id);
	}
}
