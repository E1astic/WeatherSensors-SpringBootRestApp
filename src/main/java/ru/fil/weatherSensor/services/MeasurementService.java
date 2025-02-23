package ru.fil.weatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fil.weatherSensor.models.Measurement;
import ru.fil.weatherSensor.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public int getRainyDaysCount(){
        return measurementRepository.countByRaining(true);
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setMeasuredAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }
}
