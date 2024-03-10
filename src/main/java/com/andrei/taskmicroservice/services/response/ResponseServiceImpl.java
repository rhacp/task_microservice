package com.andrei.taskmicroservice.services.response;

import com.andrei.taskmicroservice.models.entities.Input;
import com.andrei.taskmicroservice.models.entities.Response;
import com.andrei.taskmicroservice.repositories.ResponseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService{

    private final ResponseRepository responseRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public Response createResponseForCalculationInput(Double result, Input input) {
        Response response = new Response(result, input);
        Response savedResponse = responseRepository.save(response);
        log.info("Response {} saved in db. Method: {}", savedResponse.getResponseId(), "createResponseForCalculationInput");

        return savedResponse;
    }
}
