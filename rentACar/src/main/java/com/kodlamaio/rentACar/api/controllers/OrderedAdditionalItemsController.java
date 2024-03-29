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

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.response.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.response.orderedAdditionalItems.ReadOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionals")
public class OrderedAdditionalItemsController {
	@Autowired
	private OrderedAdditionalItemService additionalService;

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalItemRequest createAdditionalRequest) {
		return this.additionalService.add(createAdditionalRequest);
	}

	@PostMapping("/update")
	public Result add(@RequestBody @Valid UpdateOrderedAdditionalItemRequest updateAdditionalRequest) {
		return this.additionalService.update(updateAdditionalRequest);
	}

	@DeleteMapping("/delete")
	public Result add(@RequestBody DeleteOrderedAdditionalItemRequest deleteAdditionalRequest) {
		return this.additionalService.delete(deleteAdditionalRequest);
	}

	@GetMapping("/getbyid")
	public DataResult<ReadOrderedAdditionalItemResponse> getById(@RequestParam int id) {
		return this.additionalService.getById(id);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll() {
		return this.additionalService.getAll();
	}
}
