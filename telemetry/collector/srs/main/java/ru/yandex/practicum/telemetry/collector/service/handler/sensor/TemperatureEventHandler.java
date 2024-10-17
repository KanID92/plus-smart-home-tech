package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.TemparatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.TemperatureSensorEvent;

@Component
public class TemperatureEventHandler extends BaseSensorHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent sensorEvent) {
        ProducerRecord<String, TemparatureSensorAvro> record = new ProducerRecord<>(
                topic, toAvro(sensorEvent));
        try(KafkaProducer<String, TemparatureSensorAvro> producer = new KafkaProducer<>(
                properties))  {
            producer.send(record);
        }
    }

    private TemparatureSensorAvro toAvro(SensorEvent sensorEvent) {
        TemperatureSensorEvent temperatureEvent = (TemperatureSensorEvent) sensorEvent;

        return TemparatureSensorAvro.newBuilder()
                .setTemperatureF(temperatureEvent.getTemperatureF())
                .setTemparatureC(temperatureEvent.getTemperatureC())
                .build();
    }

}
