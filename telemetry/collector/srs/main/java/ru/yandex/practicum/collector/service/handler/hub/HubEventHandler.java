package ru.yandex.practicum.collector.service.handler.hub;

import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;


public interface HubEventHandler {
    HubEventType getMessageType();

    void handle(HubEvent hubEvent);
}
