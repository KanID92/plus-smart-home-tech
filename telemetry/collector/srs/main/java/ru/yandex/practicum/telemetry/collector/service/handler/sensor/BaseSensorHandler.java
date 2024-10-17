package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import java.util.Properties;


public abstract class BaseSensorHandler implements SensorEventHandler {

    Properties properties;
    String topic = "telemetry.sensors.v1";

    public BaseSensorHandler() {
        this.properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("client.id", "telemetry.collector");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "kafka.serializer.GeneralAvroSerializer");
    }

}

