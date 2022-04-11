package com.etiya.rentACar.business.constants.messages.core.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
    ModelMapper forRequest();
    ModelMapper forDto();
}
