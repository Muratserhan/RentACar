package com.etiya.rentACar.api.controllers;


import com.etiya.rentACar.business.abstracts.InvoiceService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.DeleteInvoiceRequest;
import com.etiya.rentACar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.etiya.rentACar.business.responses.invoiceResponses.ListInvoiceDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {

        this.invoiceService = invoiceService;
    }
    @PostMapping("/add")
    public Result add(@RequestBody CreateInvoiceRequest createInvoiceRequest){
        return  this.invoiceService.add(createInvoiceRequest);
    }
    @GetMapping("/getAll")
    public DataResult<List<ListInvoiceDto>> getAll(){

        return  this.invoiceService.getAll();
    }
    @DeleteMapping("/delete")
    public  Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest){
        return  this.invoiceService.delete(deleteInvoiceRequest);
    }
    @PostMapping("/update")
   public Result update(@RequestBody UpdateInvoiceRequest updateInvoiceRequest){
        return  this.invoiceService.update(updateInvoiceRequest);
   }

   @GetMapping("/findCreateDate")

    public  DataResult<List<ListInvoiceDto>> findCreateDate(@RequestParam @DateTimeFormat (iso=DateTimeFormat.ISO.DATE)LocalDate startDate
   ,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate){
        return  this.invoiceService.findByRentDateBetween(startDate,endDate);
    }
@GetMapping("/getAllCustomerId")
    public DataResult<List<ListInvoiceDto>> getByCustomerId(@RequestParam int customerId){
        return  this.invoiceService.getByCustomerId(customerId);
    }
}
