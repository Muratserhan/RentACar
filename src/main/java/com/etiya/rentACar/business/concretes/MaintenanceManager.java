package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.CarService;
import com.etiya.rentACar.business.abstracts.MaintenanceService;
import com.etiya.rentACar.business.constants.messages.BusinessMessages;
import com.etiya.rentACar.business.constants.messages.core.crossCuttingConcerns.exceptionHandling.BusinessException;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.business.requests.carRequests.UpdateCarStateRequest;
import com.etiya.rentACar.business.requests.maintenanceRequests.CreateMaintenanceRequest;
import com.etiya.rentACar.business.requests.maintenanceRequests.DeleteMaintenanceRequest;
import com.etiya.rentACar.business.requests.maintenanceRequests.UpdateMaintenanceRequest;
import com.etiya.rentACar.business.responses.carResponses.CarDto;
import com.etiya.rentACar.business.responses.maintenanceResponses.ListMaintenanceDto;
import com.etiya.rentACar.dataAccess.abstracts.MaintenanceDao;
import com.etiya.rentACar.entities.CarState;
import com.etiya.rentACar.entities.Maintenance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceManager implements MaintenanceService {

    private MaintenanceDao maintenanceDao;
    private ModelMapperService modelMapperService;
    private CarService carService;

    public MaintenanceManager(MaintenanceDao maintenanceDao, ModelMapperService modelMapperService,CarService carService) {
        this.maintenanceDao = maintenanceDao;
        this.modelMapperService = modelMapperService;
        this.carService=carService;
    }


    @Override
    public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
         checkIfMaintenanceExists(createMaintenanceRequest);
         Maintenance result=this.modelMapperService.forRequest().map(createMaintenanceRequest,Maintenance.class);
         this.maintenanceDao.save(result);

         updateCarState(createMaintenanceRequest);
         return new SuccessResult("MA??NTENANCE_ADD");
    }

    @Override
    public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
        Maintenance result=this.modelMapperService.forRequest().map(updateMaintenanceRequest,Maintenance.class);
        this.maintenanceDao.save(result);
        return new SuccessResult("MAINTENANCE_UPDATE");

    }

    @Override
    public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
        int maintenanceId=deleteMaintenanceRequest.getId();
        this.maintenanceDao.deleteById(maintenanceId);
        return new SuccessResult(BusinessMessages.MaintenanceMessages.MAINTENANCE_DELETED);
    }

    @Override
    public DataResult<List<ListMaintenanceDto>> getAll() {
       List<Maintenance> maintenances=this.maintenanceDao.findAll();
       List<ListMaintenanceDto> response=maintenances.stream().map(maintenance -> this.modelMapperService.forDto()
               .map(maintenance,ListMaintenanceDto.class))
                       .collect(Collectors.toList());
       return new SuccessDataResult<List<ListMaintenanceDto>>(response);
    }

    @Override
    public DataResult<List<ListMaintenanceDto>> getAllMaintenanceByCarId(int carId) {
        List<Maintenance> maintenances=this.maintenanceDao.getByCarId(carId);
        List<ListMaintenanceDto> response=maintenances.stream().map(maintenance -> this.modelMapperService.forDto()
                .map(maintenance,ListMaintenanceDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<ListMaintenanceDto>>(response);
    }
    private void checkIfMaintenanceExists(CreateMaintenanceRequest createMaintenanceRequest){
        CarDto result=this.carService.getById(createMaintenanceRequest.getCarId());
        if (result.getCarStateName()== CarState.UnderMaintenance){
            //MESAJLARI HALLET
            throw  new BusinessException(BusinessMessages.MaintenanceMessages.MAINTENANCE_ADDED);
        }
    }
    private void updateCarState(CreateMaintenanceRequest createMaintenanceRequest){
        CarDto carDto=this.carService.getById(createMaintenanceRequest.getCarId());
        UpdateCarStateRequest updateCarStateRequest=this.modelMapperService.forRequest().map(carDto,UpdateCarStateRequest.class);
        updateCarStateRequest.setCarStateName(String.valueOf(CarState.UnderMaintenance));
        //MESAJLARI HALLET
        updateCarStateRequest.setCarId(createMaintenanceRequest.getCarId());
        this.carService.updateCarState(updateCarStateRequest);

    }
}
