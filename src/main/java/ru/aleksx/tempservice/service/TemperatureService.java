package ru.aleksx.tempservice.service;

import ru.aleksx.tempservice.controller.dto.TempDataDto;

import java.util.List;
import java.util.Optional;

public interface TemperatureService {


    Optional<TempDataDto> getLastTemperature(String sensorId);

    List<TempDataDto> getLastTemperature(String sensorId, int quantity);

    List<TempDataDto> getLastTemperatureForAllSensors(int quantity);

}
