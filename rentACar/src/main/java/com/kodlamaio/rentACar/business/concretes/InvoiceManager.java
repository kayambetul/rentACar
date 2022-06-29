package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.response.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.response.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	private RentalService rentalservice;
	private OrderedAdditionalItemService orderedAdditionalItemService;
	private ModelMapperService modelMapperService;

	@Autowired
	public InvoiceManager(InvoiceRepository invoiceRepository, RentalService rentalservice,
			OrderedAdditionalItemService orderedAdditionalItemService, ModelMapperService modelMapperService) {
		
		this.invoiceRepository = invoiceRepository;
		this.rentalservice = rentalservice;
		this.orderedAdditionalItemService = orderedAdditionalItemService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {

		Rental rental= this.rentalservice.getRentalById(createInvoiceRequest.getRentalId());
		checkIfInvoiceExists(createInvoiceRequest.getRentalId());
		checkIfInvoiceNumberExists(createInvoiceRequest.getInvoiceNumber());  
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		
		invoice.setState(1);
		invoice.setTotalPrice(calculateInvoiceTotalPrice(createInvoiceRequest.getRentalId())); ;       
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.CREATED");
		
	}
	@Override
	public DataResult<ReadInvoiceResponse> getById(int id) {
		
		Invoice invoice= checkIfInvoiceExistsById(id);
		ReadInvoiceResponse response = this.modelMapperService.forResponse().map(invoice, ReadInvoiceResponse.class);
		return new SuccessDataResult<ReadInvoiceResponse>(response);
	}

	@Override
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = invoices.stream()
				.map(invoice -> this.modelMapperService.forResponse().map(invoice, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllInvoicesResponse>>(response);
	}

	
	private double calculateTotalRentalPrice(int rentalId) {        //rental hesaplama
		Rental rental = this.rentalservice.getRentalById(rentalId);
		double totalPrice = rental.getTotalPrice();
		return totalPrice;
	}

	
	private double calculateOrderedAdditionalItemTotalPrice(int id) { //ordered hesaplama /repositoryden ulaşabiliyoduk ama şimdi servis üzerinden 
		                                                             //kullandığımız için list oluşturduk.
		double result = 0;
		for (OrderedAdditionalItem additional : this.orderedAdditionalItemService.getOrderedAdditionalItems(id)){

			result += additional.getTotalPrice();
		}
		return result;
	}
	
	
	private double calculateInvoiceTotalPrice(int rentalId) {    //invoice hesap
		double invoicetotal=calculateTotalRentalPrice(rentalId)+calculateOrderedAdditionalItemTotalPrice(rentalId);

		return invoicetotal;
	}
	

	private void checkIfInvoiceExists(int rentalId) {
		Invoice currentIfInvoice=this.invoiceRepository.findByRentalId(rentalId);
		if (currentIfInvoice != null) {
			throw new BusinessException("INVOICE.EXİSTS");
		}
		
	}
	
	private Invoice checkIfInvoiceExistsById(int id) {  
		Invoice currentInvoice;
		try {
			currentInvoice = this.invoiceRepository.findById(id).get();
		} catch (Exception e) {
			throw new BusinessException("RENTAL.NOT.EXISTS");
		}
		return currentInvoice;
	}

	
	private void checkIfInvoiceNumberExists(String invoiceNumber) {   //benzersiz number üretme
		Invoice invoice = this.invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice != null) {
			throw new BusinessException("INVOICE.NOT.EXISTS");
		}
	}

	@Override
	public Invoice getInvoiceById(int id) {
		// TODO Auto-generated method stub
		return checkIfInvoiceExistsById(id);
	}
	
}
