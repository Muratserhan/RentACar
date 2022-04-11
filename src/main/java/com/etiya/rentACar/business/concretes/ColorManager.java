package com.etiya.rentACar.business.concretes;

import com.etiya.rentACar.business.abstracts.ColorService;
import com.etiya.rentACar.business.constants.messages.BusinessMessages;
import com.etiya.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.etiya.rentACar.business.requests.colorRequests.DeleteColorRequest;
import com.etiya.rentACar.business.requests.colorRequests.UpdateColorRequest;
import com.etiya.rentACar.business.responses.colorResponses.ColorDto;
import com.etiya.rentACar.business.responses.colorResponses.ListColorDto;
import com.etiya.rentACar.business.constants.messages.core.mapping.ModelMapperService;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.DataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.Result;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessDataResult;
import com.etiya.rentACar.business.constants.messages.core.utilities.results.SuccessResult;
import com.etiya.rentACar.dataAccess.abstracts.ColorDao;
import com.etiya.rentACar.entities.Color;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;


    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService=modelMapperService;
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) {

        checkIfColorExist(createColorRequest.getName());

        Color color=this.modelMapperService.forRequest().map(createColorRequest,Color.class);
        colorDao.save(color);
        return new SuccessResult(BusinessMessages.ColorMessages.COLOR_ADDED);
    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) {
        checkIfColorIdExist(deleteColorRequest.getId());
        this.colorDao.deleteById(deleteColorRequest.getId());
        return new SuccessResult(BusinessMessages.ColorMessages.COLOR_DELETED);
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) {
        checkIfColorIdExist(updateColorRequest.getId());

        Color color=this.modelMapperService.forRequest().map(updateColorRequest,Color.class);
        this.colorDao.save(color);
        return new SuccessResult(BusinessMessages.ColorMessages.COLOR_UPDATED);
    }

    @Override
    public DataResult<List<ListColorDto>> getAll() {
        List<Color> colors=this.colorDao.findAll();

        List<ListColorDto> response=colors.stream()
                .map(color -> this.modelMapperService.forDto().map(color,ListColorDto.class))
                .collect(Collectors.toList());

        return  new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<ColorDto> getById(int id) {

        Color color=this.colorDao.getById(id);

        ColorDto colorDto=this.modelMapperService.forDto().map(color,ColorDto.class);

        return new SuccessDataResult<>(colorDto);
    }

    private void checkIfColorExist(String colorName){
        if(this.colorDao.getByNameIgnoreCase(colorName).size()!=0){
            throw new RuntimeException("bu renk daha önce kaydedilmiştir");
        }
    }

    private void checkIfColorIdExist(int colorId){
        if(this.colorDao.getById(colorId)==null){
            throw new RuntimeException("bu id li renk mevcut değil");
        }
    }


}
