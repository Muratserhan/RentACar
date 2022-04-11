package com.etiya.rentACar.business.responses.carResponses;

import com.etiya.rentACar.entities.CarState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private int id;

    private double dailyPrice;

    private String description;

    private double modelYear;

    private String colorName;

    private double kilometerInfo;

    private String brandName;

    private CarState carStateName;


}
