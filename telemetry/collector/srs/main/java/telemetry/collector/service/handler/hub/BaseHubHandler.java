package telemetry.collector.service.handler.hub;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import telemetry.collector.model.hub.DeviceType;

import java.util.Properties;

public abstract class BaseHubHandler implements HubEventHandler {

    final Properties properties;

    @Value("${collector.kafka.producer.properties.bootstrap.servers}")
    private String servers;

    @Value("${collector.kafka.producer.properties.client.id}")
    private String client;

    @Value("${collector.kafka.producer.properties.key.serializer}")
    private String keySerializer;

    @Value("${collector.kafka.producer.properties.value.serializer}")
    private String valueSerializer;

    @Value("${collector.kafka.producer.topics.hub-events}")
    String topic;

    public BaseHubHandler() {
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, client);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    }

    DeviceTypeAvro toTypeAvro(DeviceType deviceType) {
        return switch (deviceType) {
            case DeviceType.MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case DeviceType.CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case DeviceType.LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case DeviceType.SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
            case DeviceType.TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
        };
    }
}
