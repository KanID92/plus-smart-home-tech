package ru.yandex.practicum.collector.service.handler.hub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Component
public class DeviceAddedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, DeviceAddedEventAvro> record = new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, DeviceAddedEventAvro> producer = new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private DeviceAddedEventAvro toAvro(HubEvent hubEvent) {
        DeviceAddedEvent addedDeviceEvent = (DeviceAddedEvent) hubEvent;

        return DeviceAddedEventAvro.newBuilder()
                .setId(addedDeviceEvent.getId())
                .setType(toDeviceTypeAvro(addedDeviceEvent.getDeviceType()))
                .build();
    }

}
