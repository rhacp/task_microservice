package com.andrei.taskmicroservice.utils.enums_converters;

import com.andrei.taskmicroservice.utils.enums.OperationEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OperationEnumConverter implements AttributeConverter<OperationEnum, String> {

    @Override
    public String convertToDatabaseColumn(OperationEnum operationEnum) {
        if (operationEnum == null) {
            return null;
        }

        return operationEnum.getOperationEnumLabel();
    }

    @Override
    public OperationEnum convertToEntityAttribute(String operationEnumLabel) {
        if (operationEnumLabel == null) {
            return null;
        }

        return Stream.of(OperationEnum.values())
                .filter(availability -> availability.getOperationEnumLabel().equals((operationEnumLabel)))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}