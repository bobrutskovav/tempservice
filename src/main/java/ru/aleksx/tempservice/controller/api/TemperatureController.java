package ru.aleksx.tempservice.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aleksx.tempservice.controller.dto.TempDataDto;
import ru.aleksx.tempservice.service.TemperatureService;

import java.util.List;

@RestController
public class TemperatureController {

    private TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }


    @GetMapping("/temperature/{sensorId}")
    public TempDataDto getTemp(@PathVariable String sensorId) {

        return temperatureService.getLastTemperature(sensorId).orElse(null);
    }


    @GetMapping("/temperature")
    public List<TempDataDto> getTempForSensor(@RequestParam String sensorId, @RequestParam int quantity) {
        if (sensorId.isEmpty()) {
            return temperatureService.getLastTemperatureForAllSensors(quantity);
        } else {
            return temperatureService.getLastTemperature(sensorId, quantity);
        }

    }




}
