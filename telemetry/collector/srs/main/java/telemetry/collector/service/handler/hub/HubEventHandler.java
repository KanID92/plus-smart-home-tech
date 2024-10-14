package telemetry.collector.service.handler.hub;

import telemetry.collector.model.hub.HubEvent;
import telemetry.collector.model.hub.HubEventType;


public interface HubEventHandler {
    HubEventType getMessageType();

    void handle(HubEvent hubEvent);
}
