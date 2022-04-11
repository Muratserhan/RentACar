package com.etiya.rentACar.business.abstracts;

import com.etiya.rentACar.business.requests.RentalRequests.CreateRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.DeleteRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.ReturnRentalRequest;
import com.etiya.rentACar.business.requests.RentalRequests.UpdateRentalRequest;
import com.etiya.rentACar.business.responses.rentalResponses.ListRentalDto;
import com.etiya.rentACar.business.responses.rentalResponses.RentalDto;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;

import java.util.List;

public interface RentalService {

    Result add(CreateRentalRequest createRentalRequest);

    Result delete(DeleteRentalRequest deleteRentalRequest);

    Result update(UpdateRentalRequest updateRentalRequest);

    DataResult<List<ListRentalDto>> getAll();

    RentalDto getById(int id);

    Result returnRental(ReturnRentalRequest returnRentalRequest);
}
