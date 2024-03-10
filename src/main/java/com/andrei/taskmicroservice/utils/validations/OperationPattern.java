package com.andrei.taskmicroservice.utils.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OperationPattersValidator.class)
public @interface OperationPattern {

    String[] anyOf();

    String message() default "Input array command&number must not be null and command must be one of the following: append, reduce, multiply, divide, power.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
