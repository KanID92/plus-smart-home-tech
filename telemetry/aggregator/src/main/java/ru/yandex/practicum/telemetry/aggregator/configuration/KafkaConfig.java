package ru.yandex.practicum.telemetry.aggregator.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Properties;

@Getter @Setter @ToString
@ConfigurationProperties("aggregator.kafka")
public class KafkaConfig {

    private Map<String, String> topics;
    private Properties producerProperties;
    private Properties consumerProperties;
}
