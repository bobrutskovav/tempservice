package ru.aleksx.tempservice.controller.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data

public class TempDataDto {

    private UUID sensorId;
    private String temperature;
    private String localDateTime;
}
