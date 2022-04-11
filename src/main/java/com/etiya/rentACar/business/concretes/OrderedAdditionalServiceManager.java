package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.etiya.rentACar.business.responses.orderedAdditionalServiceResponses.ListOrderedAdditionalServiceDto;
import com.etiya.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.etiya.rentACar.entities.OrderedAdditionalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

    private ModelMapperService modelmapperService;
    private OrderedAdditionalServiceDao orderedAdditionalServiceDao;

    public OrderedAdditionalServiceManager(ModelMapperService modelmapperService, OrderedAdditionalServiceDao orderedAdditionalServiceDao) {
        this.modelmapperService = modelmapperService;
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
    }

    @Override
    public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
        OrderedAdditionalService result=this.modelmapperService.forRequest().map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
        this.orderedAdditionalServiceDao.save(result);
        return new SuccessResult();
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
        OrderedAdditionalService result = this.modelmapperService.forRequest()
                .map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
        this.orderedAdditionalServiceDao.save(result);
        return new SuccessResult();
    }

    @Override
    public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
        int orderedAdditionalServiceId = deleteOrderedAdditionalServiceRequest.getId();
        this.orderedAdditionalServiceDao.deleteById(orderedAdditionalServiceId);
        return new SuccessResult("ORDERED_ADDITIONAL_DELETED");
    }

    @Override
    public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
        List<OrderedAdditionalService> result=this.orderedAdditionalServiceDao.findAll();
        List<ListOrderedAdditionalServiceDto> response=result.stream().map(orderedAdditionalService -> modelmapperService.forDto()
                .map(orderedAdditionalService,ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
    }
}
