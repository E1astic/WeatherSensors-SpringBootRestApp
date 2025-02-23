package ru.fil.weatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fil.weatherSensor.models.Measurement;

import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    Long countByRaining(boolean raining);
}
