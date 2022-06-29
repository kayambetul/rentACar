package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.response.individualCustomers.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.response.individualCustomers.ReadIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;



@RestController
@RequestMapping("/api/individualcustomers")
public class IndividualCustomersController {
	@Autowired
	IndividualCustomerService individualCustomerService;
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return this.individualCustomerService.add(createIndividualCustomerRequest);

	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@DeleteMapping("/delete") 
	public Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}

	@GetMapping("/getbyid")
	public DataResult<ReadIndividualCustomerResponse> getById(@RequestBody int id) {
		return this.individualCustomerService.getById(id);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll() {
		return this.individualCustomerService.getAll();
	}
}
