package com.andrei.taskmicroservice.services.input;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.dtos.ResponseDTO;
import com.andrei.taskmicroservice.models.dtos.ResponsePOSTDTO;
import com.andrei.taskmicroservice.models.entities.Input;
import com.andrei.taskmicroservice.models.entities.Response;
import com.andrei.taskmicroservice.repositories.InputRepository;
import com.andrei.taskmicroservice.repositories.ResponseRepository;
import com.andrei.taskmicroservice.services.response.ResponseService;
import com.andrei.taskmicroservice.utils.properties.TaskAPIProperties;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InputServiceImpl  implements InputService{

    private final ModelMapper modelMapper;

    private final InputRepository inputRepository;

    private final TaskAPIProperties taskAPIProperties;

    private final ResponseService responseService;

    private final TypeMap<InputDTO, Input> propertyMapper;

    public InputServiceImpl(ModelMapper modelMapper, InputRepository inputRepository, TaskAPIProperties taskAPIProperties, ResponseService responseService, @Qualifier("propertyMapper") TypeMap<InputDTO, Input> propertyMapper) {
        this.modelMapper = modelMapper;
        this.inputRepository = inputRepository;
        this.taskAPIProperties = taskAPIProperties;
        this.responseService = responseService;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public ResponseDTO calculationInput(InputDTO inputDTO) {
        Input input = modelMapper.map(inputDTO, Input.class);
        propertyMapper.map(inputDTO, input);
        Input savedInput = inputRepository.save(input);
        log.info("Input {} saved in db. Method: {}", savedInput.getId(), "calculation");

        Double result = calculation(input);
        Response savedResponse = responseService.createResponseForCalculationInput(result, savedInput);

        String serverResponse = makeCallToRestEndpoint(savedInput.getOperationNumber(), result);

        return new ResponseDTO(savedResponse.getResponseId(), savedResponse.getResult(), savedInput, serverResponse);
    }

    public Double calculation(Input input) {
        Double result = 0D;

        for (int index = 0; index < input.getInputArray().size(); index++) {
            switch (input.getInputArray().get(index).getCommand().toLowerCase()) {
                case "append" -> result += input.getInputArray().get(index).getNumber();
                case "reduce" -> result -= input.getInputArray().get(index).getNumber();
                case "multiply" -> result *= input.getInputArray().get(index).getNumber();
                case "divide" -> result /= input.getInputArray().get(index).getNumber();
                case "power" -> result = Math.pow(result, input.getInputArray().get(index).getNumber());
            }
        }

        log.info("Calculations done. Result ready. Method: {}", "calculation");

        return result;
    }

    public String makeCallToRestEndpoint(Long operationNumber, Double result) {
        Long test = 80L;

        String callUrl = taskAPIProperties.getTestBaseUrlFirst() + test + taskAPIProperties.getTestBaseUrlSecond();

        ResponsePOSTDTO response = new ResponsePOSTDTO(result);

        WebClient client = WebClient.create();

        String callResponse = client.post()
                .uri(callUrl)
                .header("Accept-Language", taskAPIProperties.getHeader())
                .header("Operation-Number", Long.toString(operationNumber))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), ResponsePOSTDTO.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Call to the REST endpoint successfully made. Method: {}", "makeCallToRestEndpoint");

        return callResponse;
    }
}
