package ru.aleksx.tempservice.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aleksx.tempservice.controller.dto.RegisterTemperatureSensorRequest;
import ru.aleksx.tempservice.controller.dto.SensorInfoDto;
import ru.aleksx.tempservice.service.SensorService;

import java.util.List;

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


    @PostMapping(value = "/sensor", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SensorInfoDto> registerSensor(@RequestBody RegisterTemperatureSensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sensorService.addNewSensor(request.getIp(), request.getGroupName()));
    }


    @GetMapping("/sensor/group/{groupName}")
    public List<SensorInfoDto> getAllSensorForGroup(@PathVariable String groupName) {
        return sensorService.getSensorsByGroupName(groupName);
    }

}
