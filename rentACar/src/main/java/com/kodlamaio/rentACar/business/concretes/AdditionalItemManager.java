package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.request.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.response.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.response.additionalItems.ReadAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
@Service
public class AdditionalItemManager implements AdditionalItemService{

	AdditionalItemRepository additionalItemRepository;
	ModelMapperService modelMapperService;
	
	@Autowired
	public AdditionalItemManager(AdditionalItemRepository additionalItemRepository,
			ModelMapperService modelMapperService) {
		super();
		this.additionalItemRepository = additionalItemRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		AdditionalItem additionalItem=this.modelMapperService.forRequest().map(createAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("ADDED.ADDİTİONALITEM");
	}

	@Override
	public Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		checkIfAdditionalItemExitsById(updateAdditionalItemRequest.getId());
		AdditionalItem additionalItem=this.modelMapperService.forRequest().map(updateAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("UPDATE.ADDİTİONALITEM");
	}

	@Override
	public Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		checkIfAdditionalItemExitsById(deleteAdditionalItemRequest.getId());
		AdditionalItem additionalItem=this.modelMapperService.forRequest().map(deleteAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("DELETE.ADDİTİONALITEM");
	}

	@Override
	public DataResult<ReadAdditionalItemResponse> getById(int id) {
		AdditionalItem additionalItem=checkIfAdditionalItemExitsById(id);
		ReadAdditionalItemResponse readAdditionalItemResponse=this.modelMapperService.forRequest().map(additionalItem, ReadAdditionalItemResponse.class);
		 return new SuccessDataResult<ReadAdditionalItemResponse>(readAdditionalItemResponse);
	}

	@Override
	public DataResult<List<GetAllAdditionalItemsResponse>> getAll() {
		List<AdditionalItem> additionalItems = this.additionalItemRepository.findAll();
		List<GetAllAdditionalItemsResponse> response = additionalItems.stream()
				.map(additionalItem -> this.modelMapperService.forResponse().map(additionalItem, GetAllAdditionalItemsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalItemsResponse>>(response);
	}

	private AdditionalItem checkIfAdditionalItemExitsById(int id) {
		AdditionalItem currentAdditionalItem; 
		try {
			currentAdditionalItem=this.additionalItemRepository.findById(id).get();	
			
		} catch (Exception e) {
			throw new BusinessException("COLOR.NOT.EXITS");   
		}
		return currentAdditionalItem;
	}

	@Override
	public AdditionalItem getAdditionalItemById(int id) {
		// TODO Auto-generated method stub
		return checkIfAdditionalItemExitsById(id);
	}
	}

