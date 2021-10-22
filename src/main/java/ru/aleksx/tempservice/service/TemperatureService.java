package ru.aleksx.tempservice.service;

import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;
import ru.aleksx.tempservice.messaging.message.TemperatureDataMessage;

import java.util.List;
import java.util.Optional;

public interface TemperatureService {


    TemperatureDataDto getLastTemperatures(String sensorId);

    List<TemperatureDataDto> getLastTemperatures(String sensorId, int quantity);

    List<TemperatureDataDto> getLastTemperatureForAllSensors(int quantity);

    List<TemperatureDataDto> getLastTemperatureForGroup(String groupId);

    void saveTemperatureDataMessage(TemperatureDataMessage temperatureDataMessage);

}
