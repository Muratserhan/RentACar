package com.etiya.rentACar.business.constants.messages.core.utilities.results;

public class ErrorResult extends Result {

    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String messages) {
        super(false, messages);
    }

}
