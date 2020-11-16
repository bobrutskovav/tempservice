package ru.aleksx.tempservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.aleksx.tempservice.controller.dto.TempDataDto;
import ru.aleksx.tempservice.model.TempData;
import ru.aleksx.tempservice.repository.TemperatureRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;

    private final ModelMapper modelMapper;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, ModelMapper modelMapper) {
        this.temperatureRepository = temperatureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<TempDataDto> getLastTemperature(String sensorId) {
        return temperatureRepository.getLastForSensorId(UUID.fromString(sensorId))
                .map(this::mapToDTO);
    }

    @Override
    public List<TempDataDto> getLastTemperature(String sensorId, int count) {
        return temperatureRepository.getLastNForSensorId(UUID.fromString(sensorId), count)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TempDataDto> getLastTemperatureForAllSensors(int count) {
        return temperatureRepository.getLastNForAll(count).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private TempDataDto mapToDTO(TempData tempData) {
        return modelMapper.map(tempData, TempDataDto.class);
    }
}
