package ru.fil.weatherSensor.utils;

public class SensorNotAddedException extends RuntimeException {
    public SensorNotAddedException(String message) {
        super(message);
    }
}
