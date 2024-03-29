package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.request.corporateCustomer.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomer.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomer.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.response.corporateCustomer.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.business.response.corporateCustomer.ReadCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporatecustomers")
public class CorporateCustomersController {
	@Autowired
	private CorporateCustomerService corporateCustomerService;

	@PostMapping("/add")
	public Result add(@RequestBody  CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody  UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}

	@PostMapping("/getbyid")
	public DataResult<ReadCorporateCustomerResponse> getbyid(@RequestParam int id) {
		return this.corporateCustomerService.getById(id);
	}

	@PostMapping("/getall")
	public DataResult<List<GetAllCorporateCustomerResponse>> getAll() {
		return this.corporateCustomerService.getAll();
	}

}
