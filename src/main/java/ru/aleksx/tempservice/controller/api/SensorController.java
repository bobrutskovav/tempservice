package ru.aleksx.tempservice.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aleksx.tempservice.controller.dto.SensorInfoDto;
import ru.aleksx.tempservice.model.Sensor;
import ru.aleksx.tempservice.service.SensorService;

import java.util.List;
import java.util.Set;

@RestController
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @GetMapping("/sensor")
    public List<SensorInfoDto> getAllSensors() {
        return sensorService.getAllSensors();
    }

}
