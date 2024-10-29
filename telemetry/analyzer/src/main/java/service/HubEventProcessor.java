package service;

import configuration.KafkaAnalyzerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class HubEventProcessor implements Runnable {

    private final KafkaConsumer<String, HubEventAvro> hubConsumer;
    private final KafkaAnalyzerConfig kafkaConfig;

    public HubEventProcessor(KafkaAnalyzerConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
        hubConsumer = new KafkaConsumer<>(kafkaConfig.getHubConsumerProperties());
    }


    @Override
    public void run() {
        log.info("HubEventProcessor started");

        try(hubConsumer) {
            Runtime.getRuntime().addShutdownHook(new Thread(hubConsumer::wakeup));
            hubConsumer.subscribe(List.of(kafkaConfig.getTopics().get("hubs-events")));

            while (true) {
                ConsumerRecords<String, HubEventAvro> records = hubConsumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    HubEventAvro hubEventAvro = record.value();
                    switch (hubEventAvro.getPayload()) {
                        case DeviceActionAvro actionAvro:
                            //TODO
                    }

                    //TODO Обработка события хаба
                    // 1.Отправка в свой сервис в зависимости от типа события?
                    //

                }

                hubConsumer.commitAsync((offsets, exception) -> {
                    if (exception != null) {
                        log.warn("Commit processing error. Offsets: {}", offsets, exception);
                    }
                });
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
                log.error("Analyzer. Error by handling HubEvents from kafka", e);
        } finally {
                log.info("Analyzer. Closing consumer.");
                hubConsumer.close();

        }
    }

}
