package telemetry.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

public abstract class BaseSensorHandler implements SensorEventHandler {

    final Properties properties;

    @Value("${collector.kafka.producer.properties.bootstrap.servers}")
    private String servers;

    @Value("${collector.kafka.producer.properties.client.id}")
    private String client;

    @Value("${collector.kafka.producer.properties.key.serializer}")
    private String keySerializer;

    @Value("${collector.kafka.producer.properties.value.serializer}")
    private String valueSerializer;

    @Value("${collector.kafka.producer.topics.sensors-events}")
    String topic;

    public BaseSensorHandler() {
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, client);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    }

}

