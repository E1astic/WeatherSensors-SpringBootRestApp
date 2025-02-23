package ru.fil.weatherSensor.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.fil.weatherSensor.dto.MeasurementDTO;
import ru.fil.weatherSensor.models.Sensor;
import ru.fil.weatherSensor.services.SensorService;

import java.util.Optional;

@Component
public class MeasurementDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        Optional<Sensor> optional=sensorService.getByName(measurementDTO.getSensor().getName());
        if(optional.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name not exists");
        }

    }
}
