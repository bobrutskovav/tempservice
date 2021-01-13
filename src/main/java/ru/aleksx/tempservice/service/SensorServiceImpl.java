package ru.aleksx.tempservice.service;

import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.aleksx.tempservice.controller.dto.SensorInfoDto;
import ru.aleksx.tempservice.model.Sensor;
import ru.aleksx.tempservice.repository.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    private final Mqtt3Client mqtt3Client;


    public SensorServiceImpl(SensorRepository sensorRepository, ModelMapper modelMapper, Mqtt3Client mqtt3Client) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
        this.mqtt3Client = mqtt3Client;
    }


    public Optional<SensorInfoDto> getSensor(String id) {
        return sensorRepository.findById(id)
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
        validateIp(ip);
        return sensorRepository.findById(ip).
                map(this::mapToDto);
    }

    @Override
    public List<SensorInfoDto> getSensorsByGroupName(String groupName) {
        return sensorRepository.findAllByGroupName(groupName)
                .stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public SensorInfoDto addNewSensor(String ip, String sensorGroupName) {

        validateIp(ip);
        checkIpIsAlreadyUsed(ip);
        Sensor sensor = new Sensor();
        sensor.setGroupName(sensorGroupName);
        sensor.setIp(ip);
        return mapToDto(sensorRepository.save(sensor));
    }


    private SensorInfoDto mapToDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorInfoDto.class);
    }


    private void validateIp(String ip) {
        log.debug("Validate ip {}", ip);
        if (!InetAddressValidator.getInstance().isValid(ip)) {
            throw new IllegalArgumentException(String.format("IP Address %s is not valid IP!", ip));
        }
    }

    private void checkIpIsAlreadyUsed(String ip) {
        log.debug("Check ip is already used {}", ip);
        Optional<Sensor> found = sensorRepository.findFirstByIp(ip);
        if (found.isPresent()) {
            throw new IllegalArgumentException(String.format("IP Address is used by Sensor %s", found.get()));
        }
    }

}
