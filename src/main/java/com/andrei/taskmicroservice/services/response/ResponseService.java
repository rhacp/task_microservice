package com.andrei.taskmicroservice.services.response;

import com.andrei.taskmicroservice.models.entities.Input;
import com.andrei.taskmicroservice.models.entities.Response;

public interface ResponseService {

    /**
     * Creates a response object for the calculationInput method based on the given result and input.
     *
     * @param result Double calculation response
     * @param input Input object
     */
    Response createResponseForCalculationInput(Double result, Input input);
}
