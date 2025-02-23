package ru.fil.weatherSensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    @Min(value = -100, message = "Value should been greater than -100")
    @Max(value = 100, message = "Value should been less than 100")
    @NotNull(message = "Value should not be empty")
    private Float value;

    @NotNull(message = "Raining should not be empty")
    private Boolean raining;

    @NotNull(message = "Sensor should not be empty")
    private SensorDTO sensor;

    public MeasurementDTO() {}

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
