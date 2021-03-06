package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.CarService;
import com.etiya.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.etiya.rentACar.business.abstracts.RentalService;
import com.etiya.rentACar.business.constants.messages.BusinessMessages;
import com.etiya.rentACar.business.constants.messages.core.crossCuttingConcerns.exceptionHandling.BusinessException;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.business.requests.RentalRequests.CreateRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.DeleteRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.ReturnRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.UpdateRentalRequest;
import com.etiya.rentACar.business.requests.carRequests.UpdateCarCityRequest;
import com.etiya.rentACar.business.requests.carRequests.UpdateCarStateRequest;
import com.etiya.rentACar.business.requests.carRequests.UpdateKilometerRequest;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.responses.carResponses.CarDto;
import com.etiya.rentACar.business.responses.rentalResponses.ListRentalDto;
import com.etiya.rentACar.business.responses.rentalResponses.RentalDto;
import com.etiya.rentACar.dataAccess.abstracts.RentalDao;
import com.etiya.rentACar.entities.CarState;
import com.etiya.rentACar.entities.Rental;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentalService {

    private ModelMapperService modelMapperService;
    private RentalDao rentalDao;
    private CarService carService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;

    public RentalManager(ModelMapperService modelMapperService, RentalDao rentalDao, CarService carService, OrderedAdditionalServiceService orderedAdditionalServiceService) {
        this.modelMapperService = modelMapperService;
        this.rentalDao = rentalDao;
        this.carService = carService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }
    @Override
    public Result add(CreateRentalRequest createRentalRequest) {
        int carId = createRentalRequest.getCarId();
        checkIfCarState(carId);


        Rental result = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
        this.rentalDao.save(result);

        CarState status = CarState.Rented;
        updateCarState(carId, status);

        int rentalId = result.getId();
        List<Integer> additionalServicesId = createRentalRequest.getAdditionalServiceId();
        createOrderedAdditionalService(rentalId, additionalServicesId);

        return new SuccessResult(BusinessMessages.RentalMessages.RENTAL_ADDED);
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) {
        Rental result = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
        this.rentalDao.save(result);
        return new SuccessResult(BusinessMessages.RentalMessages.RENTAL_UPDATED);
    }

    @Override
    public Result delete(DeleteRentalRequest deleteRentalRequest) {
        int rentalId = deleteRentalRequest.getId();
        this.rentalDao.deleteById(rentalId);
        return new SuccessResult(BusinessMessages.RentalMessages.RENTAL_DELETED);
    }


    public Result returnRental(ReturnRentalRequest returnRentalRequest) {
        checkIfRentalIdExists(returnRentalRequest.getId());

        Rental result = this.rentalDao.getById(returnRentalRequest.getId());
        result.setReturnDate(returnRentalRequest.getReturnDate());
        result.setEndKilometre(returnRentalRequest.getEndKilometer());
        this.rentalDao.save(result);


        CarState states = CarState.Available;
        int carId = returnRentalRequest.getCarId();
        int returnC??tyId = returnRentalRequest.getReturnCityId();
        updateCarKilometer(returnRentalRequest);
        updateCarState(carId, states);
        updateCarCity(carId, returnC??tyId);

        return new SuccessResult(BusinessMessages.RentalMessages.RENTAL_RETURNED);
    }

    @Override
    public RentalDto getById(int rentalId) {
        Rental rental = this.rentalDao.getById(rentalId);
        RentalDto rentalDto = this.modelMapperService.forDto().map(rental, RentalDto.class);
        return rentalDto;
    }

    private void updateCarKilometer(ReturnRentalRequest returnRentalRequest) {
        double startCarKilometer = returnRentalRequest.getEndKilometer();
        int carId = returnRentalRequest.getCarId();
        UpdateKilometerRequest updateKilometerRequest = new UpdateKilometerRequest();
        updateKilometerRequest.setId(carId);
        updateKilometerRequest.setKilometreInfo(startCarKilometer);
        this.carService.updateCarKilometer(updateKilometerRequest);
    }

    @Override
    public DataResult<List<ListRentalDto>> getAll() {
        List<Rental> results = this.rentalDao.findAll();
        List<ListRentalDto> response = results.stream().map(rental -> modelMapperService.forDto()
                .map(rental, ListRentalDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ListRentalDto>>(response);
    }


    private void checkIfCarState(int carId) {
        CarDto result = this.carService.getById(carId);
        if (result.getCarStateName() != CarState.Available) {
            throw new BusinessException(BusinessMessages.RentalMessages.CAR_NOT_AVAILABLE);
        }

    }

    private void checkIfRentalIdExists(int rentalId) {
        if (!this.rentalDao.existsById(rentalId)) {
            throw new BusinessException(BusinessMessages.RentalMessages.RENTAL_NOT_EXIST);
        }
    }

    private void updateCarState(int carId, CarState status) {
        UpdateCarStateRequest updateCarStateRequest = new UpdateCarStateRequest();
        updateCarStateRequest.setCarId(carId);
        updateCarStateRequest.setCarStateName(String.valueOf(status));
        this.carService.updateCarState(updateCarStateRequest);

    }

    private void updateCarCity(int carId, int cityId) {
        UpdateCarCityRequest updateCarCityRequest = new UpdateCarCityRequest();
        updateCarCityRequest.setId(carId);
        updateCarCityRequest.setCityId(cityId);
        this.carService.updateCarCity(updateCarCityRequest);
    }

    private void createOrderedAdditionalService(int rentalId, List<Integer> additionalServicesId) {
        CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest();
        for (int additionalServiceId : additionalServicesId) {
            createOrderedAdditionalServiceRequest.setRentalId(rentalId);
            createOrderedAdditionalServiceRequest.setAdditionalServiceId(additionalServiceId);
            this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
        }
    }








}