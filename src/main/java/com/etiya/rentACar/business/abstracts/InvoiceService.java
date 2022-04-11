package com.etiya.rentACar.business.abstracts;

import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.DeleteInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.etiya.rentACar.business.responses.invoiceResponses.ListInvoiceDto;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    
    Result add(CreateInvoiceRequest createInvoiceRequest);
    Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
    Result update(UpdateInvoiceRequest updateInvoiceRequest);

    DataResult<List<ListInvoiceDto>> getAll();

    DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId);
    DataResult<List<ListInvoiceDto>> findByRentDateBetween(LocalDate startDate,LocalDate endDate);
}
