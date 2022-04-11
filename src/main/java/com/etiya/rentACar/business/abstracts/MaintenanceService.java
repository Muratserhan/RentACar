package com.etiya.rentACar.business.abstracts;

import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.requests.maintenanceRequests.CreateMaintenanceRequest;
import com.etiya.rentACar.business.requests.maintenanceRequests.DeleteMaintenanceRequest;
import com.etiya.rentACar.business.requests.maintenanceRequests.UpdateMaintenanceRequest;
import com.etiya.rentACar.business.responses.maintenanceResponses.ListMaintenanceDto;

import java.util.List;

public interface MaintenanceService {

    Result add(CreateMaintenanceRequest createMaintenanceRequest);
    Result update(UpdateMaintenanceRequest updateMaintenanceRequest);
    Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest);


    DataResult<List<ListMaintenanceDto>> getAll();
    DataResult<List<ListMaintenanceDto>> getAllMaintenanceByCarId(int carId);
}
