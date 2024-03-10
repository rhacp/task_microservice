package com.andrei.taskmicroservice.services.input;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.dtos.ResponseDTO;
import com.andrei.taskmicroservice.models.entities.Input;

public interface InputService {

    /**
     * Saves the input object in db based on the provided DTO, then creates and saves a linked response object. Then, does the calculations and sends the request to the REST endpoint.
     *
     * @param inputDTO Input object.
     */
    ResponseDTO calculationInput(InputDTO inputDTO);

    /**
     * Helper that does the calculations only according to the input object.
     *
     * @param input Input object.
     */
    public Double calculation(Input input);

    /**
     * Helper method which makes the call to the REST endpoint from the "application.yaml" file along with the initial "operation_number".
     *
     * @param operationNumber operation number
     * @param result           result of the calculations
     */
    public String makeCallToRestEndpoint(Long operationNumber, Double result);
}
