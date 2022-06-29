package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.rental.CreateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.DeleteIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.request.rental.UpdateIndividualCustomerRentalRequest;
import com.kodlamaio.rentACar.business.response.rentals.GetAllRentalResponse;
import com.kodlamaio.rentACar.business.response.rentals.ReadRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {
	@Autowired
	RentalService rentalService;

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRentalRequest createRentalRequest) {
		return this.rentalService.addIndividual(createRentalRequest);

	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRentalRequest updateRentalRequest) {
		return this.rentalService.updateIndividual(updateRentalRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRentalRequest deleteRentalRequest) {
		return this.rentalService.deleteIndividual(deleteRentalRequest);

	}

	@GetMapping("/getbyid")
	public DataResult<ReadRentalResponse> getById(@RequestParam int id) {
		return this.rentalService.getById(id);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllRentalResponse>> getAll() {
		return this.rentalService.getAll();
	}
}
