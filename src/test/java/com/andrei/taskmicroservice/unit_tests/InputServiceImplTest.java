package com.andrei.taskmicroservice.unit_tests;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.dtos.OperationDTO;
import com.andrei.taskmicroservice.models.entities.Input;
import com.andrei.taskmicroservice.models.entities.Operation;
import com.andrei.taskmicroservice.repositories.InputRepository;
import com.andrei.taskmicroservice.services.input.InputServiceImpl;
import com.andrei.taskmicroservice.services.response.ResponseService;
import com.andrei.taskmicroservice.utils.properties.TaskAPIProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InputServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private InputRepository inputRepository;

    @Mock
    private TaskAPIProperties taskAPIProperties;

    @Mock
    private ResponseService responseService;

    @Mock
    private TypeMap<InputDTO, Input> propertyMapper;

    @Mock
    private WebClient webClient;

    private InputServiceImpl inputService;

    @BeforeEach
    void setUp() {
        inputService = new InputServiceImpl(modelMapper, inputRepository, taskAPIProperties, responseService, propertyMapper, webClient);
    }

    @Test
    void calculation_validInput() {
        //GIVEN
        List<Operation> inputArray = Arrays.asList(
                new Operation("append", 22D),
                new Operation("multiply", 2D),
                new Operation("power", 2D),
                new Operation("reduce", 2937D),
                new Operation("multiply", 4D),
                new Operation("divide", 9D)
        );
        Input input = Input.builder()
                .id(1L)
                .operationNumber(1L)
                .inputArray(inputArray)
                .build();

        Double expectedResult = -444.8888888888889D;

        //WHEN
        Double result = inputService.calculation(input);

        //THEN
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void calculationInput_emptyInput() {
        //GIVEN
        List<Operation> inputArray = new ArrayList<>();
        Input input = Input.builder()
                .id(1L)
                .operationNumber(1L)
                .inputArray(inputArray)
                .build();

        Double expectedResult = 0D;

        //WHEN
        Double result = inputService.calculation(input);

        //THEN
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void calculationInput_invalidInput() {
        //GIVEN
        Input input = Input.builder()
                .id(1L)
                .operationNumber(1L)
                .build();

        Double expectedResult = 0D;

        //WHEN

        //THEN
        assertThatExceptionOfType(java.lang.NullPointerException.class).isThrownBy(() -> inputService.calculation(input));
    }

    @Test
    void createInput_saveDB() {
        //GIVEN
        List<OperationDTO> input_array = Arrays.asList(
                new OperationDTO("append", 22D),
                new OperationDTO("multiply", 2D),
                new OperationDTO("power", 2D),
                new OperationDTO("reduce", 2937D),
                new OperationDTO("multiply", 4D),
                new OperationDTO("divide", 9D)
        );
        InputDTO inputDTO = new InputDTO(1L, input_array);

        List<Operation> expectedArray = Arrays.asList(
                new Operation("append", 22D),
                new Operation("multiply", 2D),
                new Operation("power", 2D),
                new Operation("reduce", 2937D),
                new Operation("multiply", 4D),
                new Operation("divide", 9D)
        );
        Input expectedInput = Input.builder()
                .operationNumber(1L)
                .inputArray(expectedArray)
                .build();

        List<Operation> savedArray = Arrays.asList(
                new Operation("append", 22D),
                new Operation("multiply", 2D),
                new Operation("power", 2D),
                new Operation("reduce", 2937D),
                new Operation("multiply", 4D),
                new Operation("divide", 9D)
        );
        Input savedInput = Input.builder()
                .id(1L)
                .operationNumber(1L)
                .inputArray(expectedArray)
                .build();

        when(modelMapper.map(inputDTO, Input.class)).thenReturn(expectedInput);
        when(inputRepository.save(expectedInput)).thenReturn(savedInput);

        //WHEN
        Input returnInput = inputService.createInput(inputDTO);

        //THEN
        verify(inputRepository, times(1)).save(expectedInput);
        assertThat(returnInput).isEqualTo(savedInput);
    }
}