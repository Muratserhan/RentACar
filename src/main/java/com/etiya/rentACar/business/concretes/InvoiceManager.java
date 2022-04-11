package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.InvoiceService;
import com.etiya.rentACar.business.abstracts.RentalService;
import com.etiya.rentACar.business.constants.messages.BusinessMessages;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.DeleteInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.etiya.rentACar.business.responses.invoiceResponses.ListInvoiceDto;
import com.etiya.rentACar.business.responses.orderedAdditionalServiceResponses.OrderedAdditionalServiceDto;
import com.etiya.rentACar.business.responses.rentalResponses.RentalDto;
import com.etiya.rentACar.dataAccess.abstracts.InvoiceDao;
import com.etiya.rentACar.entities.Invoice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ModelMapperService modelMapperService;
    private RentalService rentalService;

    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentalService rentalService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;

    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) {
        //fatura ekleme
        int rentalId=createInvoiceRequest.getRentalId();
        RentalDto rentalDto=this.rentalService.getById(rentalId);
        //iki tarih arasında fatura--period bir tipleme
        Period day=Period.between(rentalDto.getRentDate(),rentalDto.getReturnDate());
        int daysCount=day.getDays();
        Invoice invoice=this.modelMapperService.forRequest().map(createInvoiceRequest,Invoice.class);
        invoice.setRentDate(rentalDto.getRentDate());
        invoice.setReturnDate(rentalDto.getReturnDate());
        invoice.setTotalPrice(calculateTotalPrice(rentalDto));
        this.invoiceDao.save(invoice);
        return  new SuccessResult(BusinessMessages.InvoiceMessages.INVOICE_ADDED);
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
        int invoceId=deleteInvoiceRequest.getId();
        this.invoiceDao.deleteById(invoceId);
        return  new SuccessResult(BusinessMessages.InvoiceMessages.INVOICE_UPDATED);
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
        Invoice result=this.modelMapperService.forRequest().map(updateInvoiceRequest,Invoice.class);
        this.invoiceDao.save(result);
        return  new SuccessResult(BusinessMessages.InvoiceMessages.INVOICE_UPDATED);
    }

    @Override
    public DataResult<List<ListInvoiceDto>> getAll() {
        List<Invoice> invoices=this.invoiceDao.findAll();
        List<ListInvoiceDto> responce=invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ListInvoiceDto>>(responce);
    }

    @Override
    public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId) {
        List<Invoice> invoices=this.invoiceDao.getByCustomerId(customerId);
        List<ListInvoiceDto> responce=invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ListInvoiceDto>>(responce);
    }

    @Override
    public DataResult<List<ListInvoiceDto>> findByRentDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices=this.invoiceDao.findByCreateDateBetween(startDate, endDate);
        List<ListInvoiceDto> responce=invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ListInvoiceDto>>(responce);
    }
    private double calculateTotalPrice(RentalDto rentalDto){
        double totalPrice=0;
        Period day=Period.between(rentalDto.getRentDate(),rentalDto.getReturnDate());
        int daysCount=day.getDays();
        if (!Objects.equals(rentalDto.getRentCityName(),rentalDto.getReturnCityName())){
            totalPrice +=750;
        }
        totalPrice +=rentalDto.getDailyPrice()*daysCount;
        for (OrderedAdditionalServiceDto orderedadditionalservice:rentalDto.getOrderedAdditionalServices()){
            totalPrice +=orderedadditionalservice.getAdditionalServicePrice()*daysCount;

        }
        return totalPrice;
    }
}
