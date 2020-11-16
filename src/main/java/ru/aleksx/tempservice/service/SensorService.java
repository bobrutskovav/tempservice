package ru.aleksx.tempservice.service;

import ru.aleksx.tempservice.controller.dto.SensorInfoDto;

import java.util.List;
import java.util.Optional;


public interface SensorService {


    Optional<SensorInfoDto> getSensor(String id);

    List<SensorInfoDto> getAllSensors();

    Optional<SensorInfoDto> getSensorByIp(String ip);

}
