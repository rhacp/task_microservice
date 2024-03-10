package com.andrei.taskmicroservice.utils.validations;

import com.andrei.taskmicroservice.models.dtos.OperationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class OperationPattersValidator implements ConstraintValidator<OperationPattern, OperationDTO> {

    private String[] subset;

    @Override
    public boolean isValid(OperationDTO value, ConstraintValidatorContext context) {
        if (value.getCommand() == null) return false;
        if (value.getNumber() == null) return false;
        return Arrays.asList(subset).contains(value.getCommand().toLowerCase());
    }

    @Override
    public void initialize(OperationPattern constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }
}
