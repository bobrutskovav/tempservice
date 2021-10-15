package ru.aleksx.tempservice.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;
import ru.aleksx.tempservice.service.SensorService;
import ru.aleksx.tempservice.service.TemperatureService;

import java.util.List;

@RestController
public class TemperatureController {

    private final TemperatureService temperatureService;

    private final SensorService sensorService;

    public TemperatureController(TemperatureService temperatureService, SensorService sensorService) {
        this.temperatureService = temperatureService;
        this.sensorService = sensorService;
    }


    @GetMapping("/temperature/sensor/{sensorId}")
    public TemperatureDataDto getTempForSensor(@PathVariable String sensorId) {

        return temperatureService.getLastTemperatures(sensorId);
    }


    @GetMapping("/temperature/sensor/{sensorId}/quantity/{quantity}")
    public List<TemperatureDataDto> getTempForSensorQuantity(@PathVariable String sensorId,
                                                             @PathVariable int quantity) {
        return temperatureService.getLastTemperatures(sensorId, quantity);
    }

    @GetMapping("/temperature/group/{groupId}")
    public List<TemperatureDataDto> getTemperatureForGroupQuantity(@PathVariable String groupId) {
        return temperatureService.getLastTemperatureForGroup(groupId);
    }


}
