package telemetry.collector.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

@Getter @Setter @ToString
@ConfigurationProperties("collector.kafka")
public class KafkaConfig {
    private ProducerConfig producerConfig;

    @Getter
    public static class ProducerConfig {
        private final Properties properties;
        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);

        public ProducerConfig(Properties properties, Map<String, String> topics) {
            this.properties = properties;
            for(Map.Entry<String, String> entry : topics.entrySet()) {
                this.topics.put(TopicType.fromString(entry.getKey()),entry.getValue());
            }
        }

        public enum TopicType {
            SENSORS_EVENTS, HUBS_EVENTS;

            public static TopicType fromString(String topic) {
                for(TopicType t : TopicType.values()) {
                    if(t.name().equalsIgnoreCase(topic)) {
                        return t;
                    }
                }
                return null;
            }
        }
    }
}
