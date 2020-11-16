package ru.aleksx.tempservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.aleksx.tempservice.controller.dto.SensorInfoDto;
import ru.aleksx.tempservice.model.Sensor;
import ru.aleksx.tempservice.repository.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorServiceImpl implements SensorService {

    private SensorRepository sensorRepository;

    private ModelMapper modelMapper;


    public SensorServiceImpl(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<SensorInfoDto> getSensor(String id) {
        return sensorRepository.findById(UUID.fromString(id))
                .map(this::mapToDto);
    }

    @Override
    public List<SensorInfoDto> getAllSensors() {
        return sensorRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<SensorInfoDto> getSensorByIp(String ip) {
        return sensorRepository.findById(UUID.fromString(ip)).
                map(this::mapToDto);
    }


    private SensorInfoDto mapToDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorInfoDto.class);
    }

}
