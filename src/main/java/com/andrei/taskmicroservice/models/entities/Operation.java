package com.andrei.taskmicroservice.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    private Long operationId;

    private String command;

    private Double number;
}
