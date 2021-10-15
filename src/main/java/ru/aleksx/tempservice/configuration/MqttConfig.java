package ru.aleksx.tempservice.configuration;

import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aleksx.tempservice.settings.MqttSettings;


@Configuration
public class MqttConfig {


    @Bean
    public Mqtt3Client mqtt3AsyncClient(MqttSettings mqttSettings) {
        return Mqtt3Client.builder().serverHost(mqttSettings.getBroker().getHost())
                .serverPort(mqttSettings.getBroker().getPort())
                .automaticReconnectWithDefaultConfig().build();

    }


}
