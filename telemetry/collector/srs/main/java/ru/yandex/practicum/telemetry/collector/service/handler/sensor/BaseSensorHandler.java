package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig;

@RequiredArgsConstructor
public abstract class BaseSensorHandler implements SensorEventHandler {

    KafkaConfig kafkaConfig;

    public BaseSensorHandler(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

}

