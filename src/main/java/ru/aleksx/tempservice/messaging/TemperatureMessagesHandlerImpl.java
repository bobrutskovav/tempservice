package ru.aleksx.tempservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aleksx.tempservice.messaging.message.TemperatureDataMessage;
import ru.aleksx.tempservice.service.SensorService;
import ru.aleksx.tempservice.service.TemperatureService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Slf4j
public class TemperatureMessagesHandlerImpl implements TemperatureMessagesHandler {


    private final String temperatureTopic;

    private final Mqtt3Client mqtt3Client;

    private final TemperatureService temperatureService;

    private final SensorService sensorService;

    private final ObjectMapper objectMapper;

    public TemperatureMessagesHandlerImpl(Mqtt3Client mqtt3Client,
                                          TemperatureService temperatureService,
                                          SensorService sensorService,
                                          ObjectMapper objectMapper,
                                          @Value("mqtt.topic.temperature") String temperatureTopic) {
        this.mqtt3Client = mqtt3Client;
        this.temperatureService = temperatureService;
        this.sensorService = sensorService;
        this.objectMapper = objectMapper;
        this.temperatureTopic = temperatureTopic;
    }

    @Override
    public void handleMessages() {
        mqtt3Client.toAsync().subscribeWith()
                .topicFilter(temperatureTopic)
                .callback(message -> {
                    try {
                        TemperatureDataMessage temperatureDataMessage = objectMapper.readValue(message.getPayloadAsBytes(), TemperatureDataMessage.class);
                        String sensorId = temperatureDataMessage.getSensorId();
                        if (sensorService.getSensor(sensorId).isEmpty()) {
                            log.warn("Message from Unregistered sensor! {}", temperatureDataMessage);
                        } else {
                            log.info("Got a message form sensor {}", temperatureDataMessage.getSensorId());
                            log.debug("Message :  {}", temperatureDataMessage);
                            temperatureService.saveTemperatureDataMessage(temperatureDataMessage);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    log.debug("PAYLOAD " + message.getPayload());
                    log.debug("TOPIC " + message.getTopic().getLevels().get(0));

                })
                .send();
    }


    @PostConstruct
    public void init() {
        mqtt3Client.toAsync().connect().whenComplete((mqtt3ConnAck, throwable) -> {
            if (throwable == null) {
                log.info("MQTT ->>> CONNECTED! info: {} ", mqtt3ConnAck);
            }
        });
        handleMessages();
    }
}
