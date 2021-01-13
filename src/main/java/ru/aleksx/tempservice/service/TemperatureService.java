package ru.aleksx.tempservice.service;

import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;
import ru.aleksx.tempservice.messaging.message.TemperatureDataMessage;

import java.util.List;
import java.util.Optional;

public interface TemperatureService {


    Optional<TemperatureDataDto> getLastTemperature(String sensorId);

    List<TemperatureDataDto> getLastTemperature(String sensorId, int quantity);

    List<TemperatureDataDto> getLastTemperatureForAllSensors(int quantity);

    List<TemperatureDataDto> getLastTemperatureForGroup(String groupId);

    void saveTemperatureDataMessage(TemperatureDataMessage temperatureDataMessage);

}
