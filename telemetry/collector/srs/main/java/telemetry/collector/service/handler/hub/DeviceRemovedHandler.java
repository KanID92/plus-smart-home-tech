package telemetry.collector.service.handler.hub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import telemetry.collector.model.hub.DeviceAddedEvent;
import telemetry.collector.model.hub.HubEvent;
import telemetry.collector.model.hub.HubEventType;

public class DeviceRemovedHandler extends BaseHubHandler {

    @Override
    public HubEventType getMessageType() {
        return HubEventType.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEvent hubEvent) {
        ProducerRecord<String, DeviceRemovedEventAvro> record = new ProducerRecord<>(topic, toAvro(hubEvent));
        try(KafkaProducer<String, DeviceRemovedEventAvro> producer = new KafkaProducer<>(properties)) {
            producer.send(record);
        }
    }

    private DeviceAddedEventAvro toAvro(HubEvent hubEvent) {
        DeviceAddedEvent addedDeviceEvent = (DeviceAddedEvent) hubEvent;
        addedDeviceEvent.getDeviceType()

        return DeviceAddedEventAvro.newBuilder()
                .setId(addedDeviceEvent.getId())
                .setType(toTypeAvro(addedDeviceEvent.getDeviceType()))
                .build();
    }

}
