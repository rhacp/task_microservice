package com.andrei.taskmicroservice.controllers;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.dtos.ResponseDTO;
import com.andrei.taskmicroservice.services.input.InputService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/input")
public class InputController {

    private final InputService inputService;

    public InputController(InputService inputService) {
        this.inputService = inputService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> calculation(@Valid @RequestBody InputDTO inputDTO) {
        return ResponseEntity.ok(inputService.calculationInput(inputDTO));
    }
}
