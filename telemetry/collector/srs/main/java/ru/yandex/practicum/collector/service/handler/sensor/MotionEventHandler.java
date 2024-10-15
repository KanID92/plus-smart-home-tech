package ru.yandex.practicum.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Component
public class MotionEventHandler extends BaseSensorHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent sensorEvent) {
        ProducerRecord<String, MotionSensorAvro> record = new ProducerRecord<>(topic, toAvro(sensorEvent));
        try(KafkaProducer<String, MotionSensorAvro> producer = new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private MotionSensorAvro toAvro(SensorEvent sensorEvent) {
        MotionSensorEvent motionEvent = (MotionSensorEvent) sensorEvent;

        return MotionSensorAvro.newBuilder()
                .setMotion(motionEvent.isMotion())
                .setLinkQuality(motionEvent.getLinkQuality())
                .setVoltage(motionEvent.getVoltage())
                .build();
    }

}
