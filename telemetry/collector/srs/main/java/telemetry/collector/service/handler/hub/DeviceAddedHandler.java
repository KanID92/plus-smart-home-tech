package telemetry.collector.service.handler.hub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import telemetry.collector.model.hub.DeviceAddedEvent;
import telemetry.collector.model.hub.HubEvent;
import telemetry.collector.model.hub.HubEventType;

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
        addedDeviceEvent.getDeviceType()

        return DeviceAddedEventAvro.newBuilder()
                .setId(addedDeviceEvent.getId())
                .setType(toTypeAvro(addedDeviceEvent.getDeviceType()))
                .build();
    }

}
