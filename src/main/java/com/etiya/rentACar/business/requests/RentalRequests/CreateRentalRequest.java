package com.etiya.rentACar.business.requests.RentalRequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

    @JsonIgnore
    private int id;

    private LocalDate rentDate;

    private LocalDate returnDate;

    private int rentCityId;

    private  int returnCityId;

    private int customerId;

    private int carId;

    private double dailyPrice;

    private List<Integer> additionalServiceId;

}
