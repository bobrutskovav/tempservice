package ru.aleksx.tempservice.controller.dto;


import lombok.Data;

@Data
public class TemperatureDataDto {

    private String sensorId;
    private String temperature;
    private String time;
    private String groupId;
}
