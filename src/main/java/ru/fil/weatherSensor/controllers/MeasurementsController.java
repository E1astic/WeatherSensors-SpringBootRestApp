package ru.fil.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fil.weatherSensor.dto.MeasurementDTO;
import ru.fil.weatherSensor.dto.MeasurementDTOResponse;
import ru.fil.weatherSensor.models.Measurement;
import ru.fil.weatherSensor.models.Sensor;
import ru.fil.weatherSensor.services.MeasurementService;
import ru.fil.weatherSensor.services.SensorService;
import ru.fil.weatherSensor.utils.ErrorBody;
import ru.fil.weatherSensor.utils.ErrorUtils;
import ru.fil.weatherSensor.utils.MeasurementDTOValidator;
import ru.fil.weatherSensor.utils.MeasurementException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final MeasurementDTOValidator measurementDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, SensorService sensorService, MeasurementDTOValidator measurementDTOValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.measurementDTOValidator = measurementDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public MeasurementDTOResponse getAll() {
        MeasurementDTOResponse measurementsResponse=new MeasurementDTOResponse();
        measurementsResponse.setMeasurements(measurementService.getAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));
        return measurementsResponse;
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        measurementDTOValidator.validate(measurementDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            ErrorUtils.throwingMeasurementException(bindingResult);
        }

        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorBody> handleException(MeasurementException e) {
        ErrorBody errorBody=new ErrorBody(e.getMessage());
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // получаем сенсор текущего измерения
        String sensorName=measurementDTO.getSensor().getName();
        Sensor sensor=sensorService.getByName(sensorName).get();

        // здесь у нас смапятся все поля кроме айди сенсора (название сенсора смапится),
        // следовательно, измерение будет ссылаться на несуществующий сенсор
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);

        // делаем двустороннее связывание
        measurement.setSensor(sensor);
        sensor.addMeasurement(measurement);

        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
