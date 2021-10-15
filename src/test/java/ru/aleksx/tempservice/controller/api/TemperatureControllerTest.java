package ru.aleksx.tempservice.controller.api;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;
import ru.aleksx.tempservice.model.TemperatureData;
import ru.aleksx.tempservice.repository.SensorRepository;
import ru.aleksx.tempservice.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = MongoDataAutoConfiguration.class)
@AutoConfigureMockMvc
public class TemperatureControllerTest {


    ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    MockMvc mockMvc;


    @MockBean
    TemperatureRepository temperatureRepository;

    @MockBean
    SensorRepository sensorRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void getTemperature() throws Exception {

        String randomSensorUuid = UUID.randomUUID().toString();

        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setSensorId(randomSensorUuid);
        temperatureData.setTemperature(28.2);
        temperatureData.setTime(LocalDateTime.now());
        temperatureData.setId(randomSensorUuid);


        when(temperatureRepository.findFirstBySensorIdOrderByTimeDesc(randomSensorUuid))
                .thenReturn(Optional.of(temperatureData));
        MvcResult result = mockMvc.perform(get(String.format("/temperature/sensor/%s", randomSensorUuid)))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        TemperatureDataDto temperatureDataDto = objectMapper.readValue(content, TemperatureDataDto.class);
        Assert.assertEquals(temperatureDataDto.getTemperature(), "28.2");
    }


    @Test
    void testGetTemperatureNForSensor() throws Exception {
        String randomSensorUuid = UUID.randomUUID().toString();

        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setSensorId(randomSensorUuid);
        temperatureData.setTemperature(27.3);
        temperatureData.setTime(LocalDateTime.now());
        temperatureData.setId(randomSensorUuid);

        TemperatureData temperatureData2 = new TemperatureData();
        temperatureData2.setSensorId(randomSensorUuid);
        temperatureData2.setTemperature(26.0);
        temperatureData2.setTime(LocalDateTime.now());
        temperatureData2.setId(randomSensorUuid);

        TemperatureData temperatureData3 = new TemperatureData();
        temperatureData3.setSensorId(randomSensorUuid);
        temperatureData3.setTemperature(29.2);
        temperatureData3.setTime(LocalDateTime.now());
        temperatureData3.setId(randomSensorUuid);


        PageRequest pageRequest = PageRequest.of(0, 3);
        when(temperatureRepository.findAllBySensorIdOrderByTimeDesc(randomSensorUuid, pageRequest))
                .thenReturn(List.of(temperatureData, temperatureData2, temperatureData3));
        MvcResult result = mockMvc.perform(get(String.format("/temperature/sensor/%s/quantity/3", randomSensorUuid)))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArrayList<TemperatureDataDto> tempDatas = objectMapper.readValue(content, ArrayList.class);
        Assert.assertEquals(tempDatas.size(), 3);

    }


    @Test
    void testGetTemperatureNForSensorCached() throws Exception {
        String randomSensorUuid = UUID.randomUUID().toString();

        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setSensorId(randomSensorUuid);
        temperatureData.setTemperature(27.3);
        temperatureData.setTime(LocalDateTime.now());
        temperatureData.setId(randomSensorUuid);

        TemperatureData temperatureData2 = new TemperatureData();
        temperatureData2.setSensorId(randomSensorUuid);
        temperatureData2.setTemperature(26.0);
        temperatureData2.setTime(LocalDateTime.now());
        temperatureData2.setId(randomSensorUuid);

        TemperatureData temperatureData3 = new TemperatureData();
        temperatureData3.setSensorId(randomSensorUuid);
        temperatureData3.setTemperature(29.2);
        temperatureData3.setTime(LocalDateTime.now());
        temperatureData3.setId(randomSensorUuid);


        PageRequest pageRequest = PageRequest.of(0, 3);


        String endpointString = String.format("/temperature/sensor/%s/quantity/3", randomSensorUuid);

        when(temperatureRepository.findAllBySensorIdOrderByTimeDesc(randomSensorUuid, pageRequest))
                .thenReturn(List.of(temperatureData, temperatureData2, temperatureData3));
        mockMvc.perform(get(endpointString))
                .andExpect(status().is(200))
                .andReturn();
        MvcResult result2 = mockMvc.perform(get(endpointString))
                .andExpect(status().is(200))
                .andReturn();
        verify(temperatureRepository, times(1)).findAllBySensorIdOrderByTimeDesc(randomSensorUuid, pageRequest);
        String content = result2.getResponse().getContentAsString();
        ArrayList<TemperatureDataDto> tempDatas = objectMapper.readValue(content, ArrayList.class);
        Assert.assertEquals(tempDatas.size(), 3);

    }

}