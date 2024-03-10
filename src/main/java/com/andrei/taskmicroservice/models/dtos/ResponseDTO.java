package com.andrei.taskmicroservice.models.dtos;

import com.andrei.taskmicroservice.models.entities.Input;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO {

    private Long responseId;

    private Double result;

    private Input input;

    @JsonProperty("")
    private String serverResponse;
}
