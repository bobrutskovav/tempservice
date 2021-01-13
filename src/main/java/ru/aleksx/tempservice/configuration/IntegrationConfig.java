package ru.aleksx.tempservice.configuration;

import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aleksx.tempservice.settings.MqttSettings;

//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.endpoint.MessageProducerSupport;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;
//import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;

@Configuration
//@EnableIntegration
//@IntegrationComponentScan
public class IntegrationConfig {


    @Bean
    public Mqtt3Client mqtt3AsyncClient(MqttSettings mqttSettings) {
        return Mqtt3Client.builder().serverHost(mqttSettings.getBroker().getHost())
                .serverPort(mqttSettings.getBroker().getPort())
                .automaticReconnectWithDefaultConfig().build();

    }

//
//    //OutBound - отправка сообщений
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory(MqttSettings mqttSettings) {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//        String connectionString = String.format("tcp://%s:%d",
//                mqttSettings.getBroker().getHost(), mqttSettings.getBroker().getPort());
//        options.setServerURIs(new String[]{connectionString});
//
//        String username = mqttSettings.getUsername();
//        if (username != null) {
//            options.setUserName(username);
//        }
//        String pass = mqttSettings.getPassword();
//        if (pass != null) {
//            options.setPassword(pass.toCharArray());
//        }
//        factory.setConnectionOptions(options);
//        return factory;
//    }
//
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttOutboundChannel")
//    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttPahoClientFactory, @Value("${mqtt.clientname}") String clientName) {
//        MqttPahoMessageHandler messageHandler =
//                new MqttPahoMessageHandler(clientName, mqttPahoClientFactory);
//        //set handler details
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("/#");
//
//
//        return messageHandler;
//    }
//
//
//    @Bean
//    public MessageChannel mqttOutboundChannel() {
//        return new DirectChannel();
//    }
//
//
//    //Inbound - прием сообщений
//
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageProducerSupport inbound(MqttPahoClientFactory mqttPahoClientFactory) {
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter("server-publisher " + UUID.randomUUID().toString(), mqttPahoClientFactory, "/#");
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public AbstractMqttMessageHandler handler(MqttSettings mqttSettings, MqttPahoClientFactory clientFactory) {
//
//        String connectionString = String.format("tcp://%s:%d", mqttSettings.getBroker().getHost()
//                , mqttSettings.getBroker().getPort());
//        MqttPahoMessageHandler mqttPahoMessageHandler = new MqttPahoMessageHandler(connectionString,
//                mqttSettings.getClientName() + UUID.randomUUID().toString(),
//                clientFactory);
//        mqttPahoMessageHandler.setDefaultTopic("/#");
//        return mqttPahoMessageHandler;
//    }


}
