package ru.yandex.practicum.telemetry.collector.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
//@Configuration
//@ConfigurationProperties("collector.kafka")
public class KafkaConfig {
//
//    private ProducerConfig producer;
//
//    @Getter
//    public static class ProducerConfig {
//        private final Properties properties;
//        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);
//
//        public ProducerConfig(Properties properties, Map<String, String> topics) {
//            this.properties = properties;
//            for (Map.Entry<String, String> entry : topics.entrySet()) {
//                this.topics.put(TopicType.from(entry.getKey()), entry.getValue());
//            }
//        }
//
//        public enum TopicType {
//            SENSORS_EVENT, HUBS_EVENTS;
//
//            public static TopicType from(String type) {
//                for (TopicType value : values()) {
//                    if (value.name().equalsIgnoreCase(type.replace("-", "_"))) {
//                        return value;
//                    }
//                }
//                return null;
//            }
//        }
//    }
}
