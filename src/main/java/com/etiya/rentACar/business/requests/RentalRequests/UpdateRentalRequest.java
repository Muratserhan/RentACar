package com.etiya.rentACar.business.requests.RentalRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

    private int id;

    private LocalDate rentDate;

    private LocalDate returnDate;

    private int rentCity;

    private  int returnCity;



    private int customerId;

    private int carId;
}
