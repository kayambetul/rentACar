package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.response.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.response.invoices.ReadInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Invoice;

public interface InvoiceService {
	Result add(CreateInvoiceRequest createInvoicesRequest);
	DataResult<ReadInvoiceResponse> getById(int id);
	DataResult<List<GetAllInvoicesResponse>> getAll();
	public Invoice getInvoiceById(int id);
	

}
