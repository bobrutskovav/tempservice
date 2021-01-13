package ru.aleksx.tempservice.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.aleksx.tempservice.controller.dto.SensorInfoDto;
import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;
import ru.aleksx.tempservice.messaging.message.TemperatureDataMessage;
import ru.aleksx.tempservice.model.TemperatureData;
import ru.aleksx.tempservice.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemperatureServiceImpl implements TemperatureService {


    private static final ZoneOffset MOSCOW_ZONE_OFFSET = ZoneOffset.of("+03:00:00");

    private final TemperatureRepository temperatureRepository;

    private final SensorService sensorService;

    private final ModelMapper modelMapper;


    public TemperatureServiceImpl(TemperatureRepository temperatureRepository,
                                  SensorService sensorService, ModelMapper modelMapper) {
        this.temperatureRepository = temperatureRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "temperatureData")
    public Optional<TemperatureDataDto> getLastTemperature(String sensorId) {
        return temperatureRepository.findFirstBySensorIdOrderByTimeDesc(sensorId)
                .stream().findFirst()
                .map(this::mapToDTO);
    }

    @Override
    @Cacheable(value = "temperatureData")
    public List<TemperatureDataDto> getLastTemperature(String sensorId, int count) {
        log.info("Get last {} temperature for sensorId {}", count, sensorId);
        PageRequest pageRequest = PageRequest.of(0, count);
        return temperatureRepository.findAllBySensorIdOrderByTimeDesc(sensorId, pageRequest)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "temperatureData")
    public List<TemperatureDataDto> getLastTemperatureForAllSensors(int quantity) {
        PageRequest pageable = PageRequest.of(0, quantity);
        return temperatureRepository.findAllByOrderByTimeDesc(pageable).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "temperatureData")
    public List<TemperatureDataDto> getLastTemperatureForGroup(String groupId) {

        List<SensorInfoDto> sensors = sensorService.getSensorsByGroupName(groupId);

        return sensors.stream()
                .map(SensorInfoDto::getId)
                .map(temperatureRepository::findFirstBySensorIdOrderByTimeDesc)
                .filter(Optional::isPresent)
                .map(temperatureData -> mapToDTO(temperatureData.get()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveTemperatureDataMessage(TemperatureDataMessage temperatureDataMessage) {

        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setSensorId(temperatureDataMessage.getSensorId());
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(temperatureDataMessage.getTime(), 0, MOSCOW_ZONE_OFFSET);
        temperatureData.setTime(localDateTime);
        temperatureData.setTemperature(temperatureDataMessage.getTemperature());
        temperatureData.setCreatedTime(LocalDateTime.now());
        TemperatureData savedEntity = temperatureRepository.save(temperatureData);
        log.debug("Saved! " + savedEntity.getId());
    }


    private TemperatureDataDto mapToDTO(TemperatureData temperatureData) {
        return modelMapper.map(temperatureData, TemperatureDataDto.class);
    }
}
