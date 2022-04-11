package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.CityService;
import com.etiya.rentACar.business.constants.messages.BusinessMessages;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.business.requests.cityRequest.CreateCityRequest;
import com.etiya.rentACar.business.requests.cityRequest.DeleteCityRequest;
import com.etiya.rentACar.business.requests.cityRequest.UpdateCityRequest;
import com.etiya.rentACar.business.responses.cityResponses.ListCityDto;
import com.etiya.rentACar.dataAccess.abstracts.CityDao;
import com.etiya.rentACar.entities.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {
    private CityDao cityDao;
    private ModelMapperService modelMapperService;


    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;

    }


    @Override
    public Result add(CreateCityRequest createCityRequest) {

        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("CITY_ADDED");
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {
        this.cityDao.deleteById(deleteCityRequest.getId());
        return new SuccessResult(BusinessMessages.CityMessages.CITY_DELETED);
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {
        City city=this.modelMapperService.forRequest().map(updateCityRequest, City.class);
        this.cityDao.save(city);
        return  new SuccessResult(BusinessMessages.CityMessages.CITY_UPDATED);
    }

    @Override
    public DataResult<List<ListCityDto>> getAll() {
        List<City> cities=this.cityDao.findAll();

        List<ListCityDto> response=cities.stream()
                .map(city -> this.modelMapperService.forDto().map(city,ListCityDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }
}
