package ru.aleksx.tempservice.controller.dto;

import lombok.Data;

@Data
public class RegisterTemperatureSensorRequest {

    private String ip;
    private String groupName;
}
