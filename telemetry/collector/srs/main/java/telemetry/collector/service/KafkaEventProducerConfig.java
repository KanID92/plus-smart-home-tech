package telemetry.collector.service;

import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Getter
public class KafkaEventProducerConfig {

    private final Properties props;

    @Value("${collector.kafka.producer.properties.bootstrap.servers}")
    private String servers;

    @Value("${collector.kafka.producer.properties.client.id}")
    private String client;

    @Value("${collector.kafka.producer.properties.key.serializer}")
    private String keySerializer;

    @Value("${collector.kafka.producer.properties.value.serializer}")
    private String valueSerializer;

    public KafkaEventProducerConfig() {
        props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, client);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    }

}
