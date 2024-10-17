package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SwitchSensorEvent;

@Component
public class SwitchEventHandler extends BaseSensorHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent sensorEvent) {
        ProducerRecord<String, SwitchSensorAvro> record = new ProducerRecord<>(
                topic, toAvro(sensorEvent));
        try(KafkaProducer<String, SwitchSensorAvro> producer =
                    new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private SwitchSensorAvro toAvro(SensorEvent sensorEvent) {
        SwitchSensorEvent switchEvent = (SwitchSensorEvent) sensorEvent;

        return SwitchSensorAvro.newBuilder()
                .setState(switchEvent.isState())
                .build();
    }
}
