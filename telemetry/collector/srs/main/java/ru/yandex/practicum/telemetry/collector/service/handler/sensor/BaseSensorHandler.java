package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaEventProducer;

public abstract class BaseSensorHandler implements SensorEventHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseSensorHandler.class);
    KafkaEventProducer producer;
    String topic;

    public BaseSensorHandler(KafkaEventProducer kafkaProducer) {
        this.producer = kafkaProducer;
        topic = kafkaProducer.getConfig().getTopics().get("sensors-events");
    }

    @Override
    public void handle(SensorEventProto sensorEvent) {
        log.info("Received sensor event {}", sensorEvent);
        ProducerRecord<String, SpecificRecordBase> record =
                new ProducerRecord<>(
                        topic, null, System.currentTimeMillis(), sensorEvent.getHubId(), toAvro(sensorEvent));
        log.info("Sending sensor event {}", record);
        producer.sendRecord(record);
    }

    abstract SpecificRecordBase toAvro(SensorEventProto sensorEvent);

}

