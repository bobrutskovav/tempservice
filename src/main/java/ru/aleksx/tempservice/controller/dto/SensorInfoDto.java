package ru.aleksx.tempservice.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SensorInfoDto {

    private UUID uuid;
    private String ip;
}
