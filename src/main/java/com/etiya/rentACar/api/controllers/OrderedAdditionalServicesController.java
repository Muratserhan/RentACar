package com.etiya.rentACar.api.controllers;

import com.etiya.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.responses.orderedAdditionalServiceResponses.ListOrderedAdditionalServiceDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderedAdditionalServices")
public class OrderedAdditionalServicesController {
    private OrderedAdditionalServiceService orderedAdditionalServiceService;

    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
        return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
        return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
        return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
    }

    @GetMapping("/getall")
    public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
        return this.orderedAdditionalServiceService.getAll();
    }

}
