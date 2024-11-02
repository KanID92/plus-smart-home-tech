package ru.yandex.practicum.telemetry.analyzer.processor;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.analyzer.configuration.KafkaAnalyzerConfig;
import ru.yandex.practicum.telemetry.analyzer.entity.Action;
import ru.yandex.practicum.telemetry.analyzer.entity.Scenario;
import ru.yandex.practicum.telemetry.analyzer.entity.ScenarioCondition;
import ru.yandex.practicum.telemetry.analyzer.entity.Sensor;
import ru.yandex.practicum.telemetry.analyzer.mapper.ScenarioMapper;
import ru.yandex.practicum.telemetry.analyzer.mapper.SensorMapper;
import ru.yandex.practicum.telemetry.analyzer.repository.ActionRepository;
import ru.yandex.practicum.telemetry.analyzer.repository.ConditionRepository;
import ru.yandex.practicum.telemetry.analyzer.service.ScenarioService;
import ru.yandex.practicum.telemetry.analyzer.service.SensorService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HubEventProcessor implements Runnable {

    private final KafkaConsumer<String, HubEventAvro> hubConsumer;
    private final KafkaAnalyzerConfig kafkaConfig;

    private final SensorService sensorService;
    private final ScenarioService scenarioService;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;

    public HubEventProcessor(KafkaAnalyzerConfig kafkaConfig,
                             SensorService sensorService,
                             ScenarioService scenarioService,
                             ConditionRepository conditionRepository,
                             ActionRepository actionRepository) {
        this.kafkaConfig = kafkaConfig;
        hubConsumer = new KafkaConsumer<>(kafkaConfig.getHubConsumerProperties());
        this.sensorService = sensorService;
        this.scenarioService = scenarioService;
        this.conditionRepository = conditionRepository;
        this.actionRepository = actionRepository;

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

                        case DeviceAddedEventAvro deviceAddedEventAvro -> {
                            Sensor addedSensor = SensorMapper.avroToSensor(hubEventAvro.getHubId(), deviceAddedEventAvro);
                            sensorService.add(addedSensor);
                        }

                        case DeviceRemovedEventAvro deviceRemovedEventAvro ->
                            sensorService.delete(hubEventAvro.getHubId(), deviceRemovedEventAvro.getId());


                        case ScenarioAddedEventAvro scenarioAddedEventAvro -> {
                            Optional<Scenario> oldScenario = scenarioService.findByNameAndHubId(
                                    scenarioAddedEventAvro.getName(), hubEventAvro.getHubId());

                            if(oldScenario.isEmpty()) {
                                Scenario addedScenario = ScenarioMapper.avroToScenario(hubEventAvro.getHubId(), scenarioAddedEventAvro);
                                scenarioService.add(addedScenario);
                            } else {
                                Set<Long> oldConditionsIds = oldScenario.get().getScenarioConditions().stream()
                                        .map(ScenarioCondition::getConditionId)
                                        .collect(Collectors.toSet());
                                conditionRepository.deleteAllByConditionIdIn(oldConditionsIds);
                                Set<Long> oldActions = oldScenario.get().getScenarioActions().stream()
                                        .map(Action::getActionId)
                                        .collect(Collectors.toSet());
                                actionRepository.deleteAllByActionIdIn(oldActions);

                                Scenario addedScenario = ScenarioMapper.avroToScenario(
                                        hubEventAvro.getHubId(), scenarioAddedEventAvro);

                                conditionRepository.saveAll(addedScenario.getScenarioConditions());
                                actionRepository.saveAll(addedScenario.getScenarioActions());

                            }

                        }

                        case ScenarioRemovedEventAvro scenarioRemovedEventAvro ->
                            scenarioService.delete(scenarioRemovedEventAvro.getName(), hubEventAvro.getHubId());

                        default -> throw new IllegalStateException("Unexpected value: " + hubEventAvro.getPayload());
                    }
                }

                hubConsumer.commitAsync((offsets, exception) -> {
                    if (exception != null) {
                        log.warn("Commit hubEvent processing error. Offsets: {}", offsets, exception);
                    }
                });
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
                log.error("Analyzer. Error by handling HubEvents from kafka", e);
        } finally {
                log.info("Analyzer. Closing consumer.");
        }
    }

}
