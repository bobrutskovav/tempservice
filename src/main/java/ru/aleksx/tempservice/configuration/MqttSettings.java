package ru.aleksx.tempservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mqtt")
public class MqttSettings {


    public static class Broker {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    private Broker broker;

    private String clientName;

    private String password;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Broker getBroker() {
        return broker;
    }

    public String getPassword() {
        return password;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
