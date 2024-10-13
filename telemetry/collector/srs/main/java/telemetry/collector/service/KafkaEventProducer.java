package telemetry.collector.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import telemetry.collector.configuration.KafkaConfig;

@SpringBootApplication
@EnableConfigurationProperties(KafkaConfig.class)
public class KafkaEventProducer {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConfig.class);
    }
}
