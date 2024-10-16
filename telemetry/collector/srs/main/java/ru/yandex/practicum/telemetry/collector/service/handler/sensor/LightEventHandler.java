package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.LigntSensorAvro;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig;
import ru.yandex.practicum.telemetry.collector.model.sensor.LightSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;

@Component
public class LightEventHandler extends BaseSensorHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent sensorEvent) {
        ProducerRecord<String, LigntSensorAvro> record = new ProducerRecord<>(
                kafkaConfig.getProducer().getTopics().get(
                        KafkaConfig.ProducerConfig.TopicType.SENSORS_EVENT), toAvro(sensorEvent));
        try(KafkaProducer<String, LigntSensorAvro> producer =
                    new KafkaProducer<>(kafkaConfig.getProducer().getProperties())) {
            producer.send(record);
        }
    }

    private LigntSensorAvro toAvro(SensorEvent sensorEvent) {
        LightSensorEvent lightEvent = (LightSensorEvent) sensorEvent;

        return LigntSensorAvro.newBuilder()
                .setLinkQuality(lightEvent.getLinkQuality())
                .setLuminosity(lightEvent.getLuminosity())
                .build();
    }
}
