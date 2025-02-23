package ru.fil.weatherSensor.dto;

import java.util.List;

public class MeasurementDTOResponse {

    private List<MeasurementDTO> measurements;

    public MeasurementDTOResponse(){
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
