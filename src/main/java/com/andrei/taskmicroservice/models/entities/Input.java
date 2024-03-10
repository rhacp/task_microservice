package com.andrei.taskmicroservice.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inputs")
public class Input {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_number")
    private Long operationNumber;

    @ElementCollection
    @CollectionTable(
            name = "input_operation",
            joinColumns = @JoinColumn(name = "id")
    )
    private List<Operation> inputArray = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "operation")
    private Response response;
}
