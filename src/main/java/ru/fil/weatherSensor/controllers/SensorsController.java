package ru.fil.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.fil.weatherSensor.dto.SensorDTO;
import ru.fil.weatherSensor.models.Sensor;
import ru.fil.weatherSensor.services.SensorService;
import ru.fil.weatherSensor.utils.ErrorBody;
import ru.fil.weatherSensor.utils.SensorNotAddedException;
import ru.fil.weatherSensor.utils.SensorDTOValidator;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final SensorDTOValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, SensorDTOValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid SensorDTO sensorDTO,
                                 BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error: bindingResult.getFieldErrors()){
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }
            throw new SensorNotAddedException(errorMessage.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorBody> handleException(SensorNotAddedException e){
        ErrorBody errorBody=new ErrorBody(e.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
