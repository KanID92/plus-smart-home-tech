package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

@Component
@RequiredArgsConstructor
public class DeviceRemovedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, DeviceRemovedEventAvro> record =
                new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, DeviceRemovedEventAvro> producer =
                    new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private DeviceRemovedEventAvro toAvro(HubEvent hubEvent) {
        DeviceAddedEvent addedDeviceEvent = (DeviceAddedEvent) hubEvent;

        return DeviceRemovedEventAvro.newBuilder()
                .setId(addedDeviceEvent.getId())
                .build();
    }

}
