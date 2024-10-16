package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScenarioAddedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, ScenarioAddedEventAvro> record =
                new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, ScenarioAddedEventAvro> producer =
                    new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private ScenarioAddedEventAvro toAvro(HubEvent hubEvent) {

       ScenarioAddedEvent addedScenarioEvent = (ScenarioAddedEvent) hubEvent;
        List<DeviceActionAvro> actionAvroList = addedScenarioEvent.getActions().stream()
                .map(this::toDeviceActionAvro)
                .toList();
        List<ScenarioConditionAvro> scenarioConditionAvroList = addedScenarioEvent.getConditions().stream()
                .map(this::toScenarioConditionAvro)
                .toList();

        return ScenarioAddedEventAvro.newBuilder()
                .setName(addedScenarioEvent.getName())
                .setAction(actionAvroList)
                .setConditions(scenarioConditionAvroList)
                .build();
    }
}
