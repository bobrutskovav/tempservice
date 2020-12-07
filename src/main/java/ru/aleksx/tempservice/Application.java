package ru.aleksx.tempservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.config.EnableIntegration;

@EnableConfigurationProperties
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}



