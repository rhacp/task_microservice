package com.andrei.taskmicroservice.services.input;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.dtos.ResponseDTO;
import com.andrei.taskmicroservice.models.dtos.ResponsePOSTDTO;
import com.andrei.taskmicroservice.models.entities.Input;
import com.andrei.taskmicroservice.models.entities.Response;
import com.andrei.taskmicroservice.repositories.InputRepository;
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

    private final WebClient webClient;

    public InputServiceImpl(ModelMapper modelMapper, InputRepository inputRepository, TaskAPIProperties taskAPIProperties, ResponseService responseService, @Qualifier("propertyMapper") TypeMap<InputDTO, Input> propertyMapper, WebClient webClient) {
        this.modelMapper = modelMapper;
        this.inputRepository = inputRepository;
        this.taskAPIProperties = taskAPIProperties;
        this.responseService = responseService;
        this.propertyMapper = propertyMapper;
        this.webClient = webClient;
    }

    @Override
    public ResponseDTO fullFunction(InputDTO inputDTO) {
        Input savedInput = createInput(inputDTO);

        Double result = calculation(savedInput);
        Response savedResponse = responseService.createResponseForCalculationInput(result, savedInput);

        String serverResponse = makeCallToRestEndpoint(savedInput.getOperationNumber(), result);

        return new ResponseDTO(savedResponse.getResponseId(), savedResponse.getResult(), savedInput, serverResponse);
    }

    public Input createInput(InputDTO inputDTO) {
        Input input = modelMapper.map(inputDTO, Input.class);
        propertyMapper.map(inputDTO, input);
        Input savedInput = inputRepository.save(input);
        log.info("Input {} saved in db. Method: {}", savedInput.getId(), "calculation");

        return savedInput;
    }

    @Override
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

    @Override
    public String makeCallToRestEndpoint(Long operationNumber, Double result) {
        String callUrl = taskAPIProperties.getBaseUrlFirst() + operationNumber + taskAPIProperties.getBaseUrlSecond();

        ResponsePOSTDTO response = new ResponsePOSTDTO(result);

        String callResponse = webClient.post()
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
