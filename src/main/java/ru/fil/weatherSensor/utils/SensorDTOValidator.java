package ru.fil.weatherSensor.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.fil.weatherSensor.dto.SensorDTO;
import ru.fil.weatherSensor.models.Sensor;
import ru.fil.weatherSensor.services.SensorService;

import java.util.Optional;

@Component
public class SensorDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        Optional<Sensor> optional=sensorService.getByName(sensorDTO.getName());
        if(optional.isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name already exists");
        }
    }
}
