package ru.yandex.practicum.collector.service.handler.hub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, ScenarioRemovedEventAvro> record = new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, ScenarioRemovedEventAvro> producer = new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private ScenarioRemovedEventAvro toAvro(HubEvent hubEvent) {
        ScenarioRemovedEvent event = (ScenarioRemovedEvent) hubEvent;


        return ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();
    }
}
