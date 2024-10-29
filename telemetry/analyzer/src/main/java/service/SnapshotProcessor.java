package service;

import configuration.KafkaAnalyzerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class SnapshotProcessor implements Runnable {

    private final KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer;
    private final KafkaAnalyzerConfig kafkaConfig;

    public SnapshotProcessor(KafkaAnalyzerConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
        snapshotConsumer = new KafkaConsumer<>(kafkaConfig.getSnapshotConsumerProperties());
    }


    @Override
    public void run() {
        log.info("Starting snapshotProcessor");

        try (snapshotConsumer) {
            Runtime.getRuntime().addShutdownHook(new Thread(snapshotConsumer::wakeup));
            snapshotConsumer.subscribe(List.of(kafkaConfig.getTopics().get("sensors-snapshot:")));

            while (true) {
                ConsumerRecords<String, SensorsSnapshotAvro> records = snapshotConsumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    SensorsSnapshotAvro sensorsSnapshotAvro = record.value();
                    //TODO Обработка события стапшота

                }
                snapshotConsumer.commitAsync((offsets, exception) -> {
                    if (exception != null) {
                        log.warn("Commit processing error. Offsets: {}", offsets, exception);
                    }
                });
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Analyzer. Error by handling SnapshotEvents from kafka", e);
        } finally {
            log.info("Analyzer. Closing consumer.");
            snapshotConsumer.close();
        }
    }
}
