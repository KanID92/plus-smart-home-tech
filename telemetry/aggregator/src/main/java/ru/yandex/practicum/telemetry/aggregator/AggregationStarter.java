package ru.yandex.practicum.telemetry.aggregator;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.configuration.KafkaConfig;
import ru.yandex.practicum.telemetry.aggregator.repository.SnapshotsRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final KafkaConfig kafkaConfig;
    private final SnapshotsRepository snapshotsRepository;

    public AggregationStarter(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
        producer = new KafkaProducer<>(kafkaConfig.getProducerProperties());
        consumer = new KafkaConsumer<>(kafkaConfig.getConsumerProperties());
        snapshotsRepository = new SnapshotsRepository();
    }

    public void start() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            consumer.subscribe(List.of(kafkaConfig.getTopics().get("sensors-events")));

            //poll loop - цикл опроса
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records =
                        consumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    SensorEventAvro event = record.value();


                }

            }

        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Aggregator. Error by handling events from sensors", e);
        } finally {
            log.info("Aggregator. Closing consumer.");
            consumer.close();
            log.info("Aggregator. Closing producer.");
            producer.close();
        }

    }

    private void updateState(SensorEventAvro event) {
        Optional<SensorsSnapshotAvro> oldSnapshot = snapshotsRepository.get(event.getHubId());

        if (oldSnapshot.isPresent()) {
            Optional<SensorStateAvro> oldEvent =
                    Optional.ofNullable(oldSnapshot.get().getSensorsState().get(event.getId()));
            if (oldEvent.isPresent() && oldEvent.get().getTimestamp().isBefore(event.getTimestamp())) {
                SensorsSnapshotAvro newSnapshot = oldSnapshot.get();
                newSnapshot.setTimestamp(event.getTimestamp());
                newSnapshot.getSensorsState().put(event.getId(), ); //TODO
            }

        } else {
            Map<String, SensorStateAvro> state = new HashMap<>();
            SensorStateAvro sensorStateAvro = new SensorStateAvro();
            sensorStateAvro.setTimestamp(event.getTimestamp());
            sensorStateAvro.setData(event);
            state.put(event.getId(), sensorStateAvro);
            snapshotsRepository.update(event.getHubId(),
                    SensorsSnapshotAvro.newBuilder()
                            .setHubId(event.getHubId())
                            .setTimestamp(Instant.now())
                            .setSensorsState(state)
                            .build());
        }
    }

}
