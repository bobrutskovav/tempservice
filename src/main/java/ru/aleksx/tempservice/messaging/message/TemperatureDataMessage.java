package ru.aleksx.tempservice.messaging.message;


import lombok.Data;

@Data
public class TemperatureDataMessage {

    private long time;
    private String sensorId;
    private double temperature;
}
