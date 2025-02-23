package ru.fil.weatherSensor.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorUtils {

    public static void throwingMeasurementException(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for(FieldError error: bindingResult.getFieldErrors()){
            errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
        }
        throw new MeasurementException(errorMessage.toString());
    }
}
