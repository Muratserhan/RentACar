package com.etiya.rentACar.api.controllers;

import com.etiya.rentACar.business.abstracts.RentalService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.requests.RentalRequests.CreateRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.DeleteRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.UpdateRentalRequest;
import com.etiya.rentACar.business.responses.rentalResponses.ListRentalDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    private RentalService rentalService;

    public RentalsController(RentalService rentalService) {

        this.rentalService = rentalService;
    }


    @GetMapping("/getall")
    public DataResult<List<ListRentalDto>> getAll(){

        return rentalService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateRentalRequest createRentalRequest){
        return this.rentalService.add(createRentalRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateRentalRequest updateRentalRequest){
        return this.rentalService.update(updateRentalRequest);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest){
        return this.rentalService.delete(deleteRentalRequest);
    }

}
