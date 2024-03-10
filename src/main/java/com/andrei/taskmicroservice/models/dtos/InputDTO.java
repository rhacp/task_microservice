package com.andrei.taskmicroservice.models.dtos;

import com.andrei.taskmicroservice.utils.validations.OperationPattern;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputDTO {

    @NotNull
    private Long operation_number;

    @NotNull
    private List<@OperationPattern(anyOf = {"append", "multiply", "reduce", "divide", "power"})OperationDTO> input_array = new ArrayList<>();
}
