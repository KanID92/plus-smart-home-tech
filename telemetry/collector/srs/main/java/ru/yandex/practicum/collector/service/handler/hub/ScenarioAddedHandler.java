package ru.yandex.practicum.collector.service.handler.hub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.List;

@Component
public class ScenarioAddedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, ScenarioAddedEventAvro> record = new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, ScenarioAddedEventAvro> producer = new KafkaProducer<>(properties)) {
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
