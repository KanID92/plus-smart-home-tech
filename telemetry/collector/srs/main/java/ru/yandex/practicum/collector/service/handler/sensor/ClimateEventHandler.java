package ru.yandex.practicum.collector.service.handler.sensor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@Component
public class ClimateEventHandler extends BaseSensorHandler {

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent sensorEvent) {
        ProducerRecord<String, ClimateSensorAvro> record = new ProducerRecord<>(topic, toAvro(sensorEvent));
        try(KafkaProducer<String, ClimateSensorAvro> producer = new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private ClimateSensorAvro toAvro(SensorEvent sensorEvent) {
        ClimateSensorEvent climateEvent = (ClimateSensorEvent) sensorEvent;

        return ClimateSensorAvro.newBuilder()
                .setCo2Level(climateEvent.getCo2Level())
                .setTemperatureC(climateEvent.getTemperatureC())
                .setHumidity(climateEvent.getHumidity())
                .build();
    }

}
