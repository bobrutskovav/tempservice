package ru.aleksx.tempservice.controller.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class TemperatureDataDto implements Serializable {

    private String sensorId;
    private String temperature;
    private String time;
    private String groupId;
}
