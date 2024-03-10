package com.andrei.taskmicroservice.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responses")
public class Response {

    public Response(Double result, Input operation) {

        this.result = result;
        this.operation = operation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @Column(name = "result")
    private Double result;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "input_id", referencedColumnName = "id")
    private Input operation;
}
