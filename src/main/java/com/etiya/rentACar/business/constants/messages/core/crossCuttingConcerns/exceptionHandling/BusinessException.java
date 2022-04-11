package com.etiya.rentACar.business.constants.messages.core.crossCuttingConcerns.exceptionHandling;

public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }

}
