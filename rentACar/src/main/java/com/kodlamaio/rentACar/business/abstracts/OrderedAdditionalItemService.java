package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalItems.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.response.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.response.orderedAdditionalItems.ReadOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItem;

public interface OrderedAdditionalItemService {

	Result add(CreateOrderedAdditionalItemRequest createAdditionalRequest);

	Result update(UpdateOrderedAdditionalItemRequest updateAdditionalRequest);

	Result delete(DeleteOrderedAdditionalItemRequest deleteAdditionalRequest);

	DataResult<ReadOrderedAdditionalItemResponse> getById(int id);

	DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll();
	
	public OrderedAdditionalItem getOrderedAdditionalItemById(int id);
	
	List<OrderedAdditionalItem> getOrderedAdditionalItems(int id);

}
