package ru.aleksx.tempservice.controller.dto;

import lombok.Data;

@Data
public class SensorInfoDto {

    private String id;
    private String ip;
    private String groupName;
}
