package ru.aleksx.tempservice.controller.api;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.aleksx.tempservice.controller.dto.TempDataDto;
import ru.aleksx.tempservice.model.TempData;
import ru.aleksx.tempservice.repository.SensorRepository;
import ru.aleksx.tempservice.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = CassandraDataAutoConfiguration.class)
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
    void getTemp() throws Exception {

        UUID randomSensorUuid = UUID.randomUUID();

        TempData tempData = new TempData();
        tempData.setSensorId(randomSensorUuid);
        tempData.setTemperature("28");
        tempData.setTime(LocalDateTime.now());
        tempData.setRecordId(randomSensorUuid);


        when(temperatureRepository.getLastForSensorId(randomSensorUuid))
                .thenReturn(Optional.of(tempData));
        MvcResult result = mockMvc.perform(get(String.format("/temperature/%s", randomSensorUuid.toString())))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        TempDataDto tempDataDto = objectMapper.readValue(content, TempDataDto.class);
        Assert.assertEquals(tempDataDto.getTemperature(), "28");
    }


    @Test
    void testGetTempNForSensor() throws Exception {
        UUID randomSensorUuid = UUID.randomUUID();

        TempData tempData = new TempData();
        tempData.setSensorId(randomSensorUuid);
        tempData.setTemperature("27");
        tempData.setTime(LocalDateTime.now());
        tempData.setRecordId(randomSensorUuid);

        TempData tempData2 = new TempData();
        tempData2.setSensorId(randomSensorUuid);
        tempData2.setTemperature("26");
        tempData2.setTime(LocalDateTime.now());
        tempData2.setRecordId(randomSensorUuid);

        TempData tempData3 = new TempData();
        tempData3.setSensorId(randomSensorUuid);
        tempData3.setTemperature("27");
        tempData3.setTime(LocalDateTime.now());
        tempData3.setRecordId(randomSensorUuid);

        when(temperatureRepository.getLastNForSensorId(randomSensorUuid, 3))
                .thenReturn(List.of(tempData, tempData2, tempData3));
        MvcResult result = mockMvc.perform(get(String.format("/temperature?sensorId=%s&quantity=3", randomSensorUuid.toString())))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArrayList<TempDataDto> tempDatas = objectMapper.readValue(content, ArrayList.class);
        Assert.assertEquals(tempDatas.size(), 3);

    }

    @Test
    void testGetTempNForAll() throws Exception {
        UUID randomSensorUuid = UUID.randomUUID();

        TempData tempData = new TempData();
        tempData.setSensorId(randomSensorUuid);
        tempData.setTemperature("27");
        tempData.setTime(LocalDateTime.now());
        tempData.setRecordId(randomSensorUuid);

        TempData tempData2 = new TempData();
        tempData2.setSensorId(randomSensorUuid);
        tempData2.setTemperature("26");
        tempData2.setTime(LocalDateTime.now());
        tempData2.setRecordId(randomSensorUuid);

        TempData tempData3 = new TempData();
        tempData3.setSensorId(randomSensorUuid);
        tempData3.setTemperature("27");
        tempData3.setTime(LocalDateTime.now());
        tempData3.setRecordId(randomSensorUuid);


        when(temperatureRepository.getLastNForAll( 3))
                .thenReturn(List.of(tempData, tempData2, tempData3));

        MvcResult result = mockMvc.perform(get("/temperature?sensorId=&quantity=3"))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArrayList<TempDataDto> tempDatas = objectMapper.readValue(content, ArrayList.class);
        Assert.assertEquals(tempDatas.size(), 3);

    }
}