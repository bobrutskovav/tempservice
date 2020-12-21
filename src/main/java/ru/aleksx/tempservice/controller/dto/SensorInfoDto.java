package ru.aleksx.tempservice.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SensorInfoDto implements Serializable {

    private String id;
    private String ip;
    private String groupName;
}
