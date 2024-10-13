package telemetry.collector;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import telemetry.collector.model.hub.HubEvent;
import telemetry.collector.model.hub.HubEventType;
import telemetry.collector.model.sensor.SensorEvent;
import telemetry.collector.model.sensor.SensorEventType;

import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final Map<HubEventType, HubEventHandler> hubEventHandlerMap;
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlerMap;


    public EventController(Set<HubEventHandler> hubEventHandlerSet,
                           Set<SensorEventHandler> sensorEventHandlerSet) {
        this.hubEventHandlerMap = hubEventHandlerSet.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
        this.sensorEventHandlerMap = sensorEventHandlerSet.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public addSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        if (sensorEventHandlerMap.containsKey(sensorEvent.getType())) {
            sensorEventHandlerMap.get(sensorEvent.getType()).handle(sensorEvent);
        } else {
            throw new IllegalArgumentException("Handler for sensor type event " + sensorEvent.getType() + " not found");
        }
    }

    @PostMapping("/hubs")
    public addHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        if(hubEventHandlerMap.containsKey(hubEvent.getType())) {
            hubEventHandlerMap.get(hubEvent.getType()).handler(hubEvent);
        } else {
            throw new IllegalArgumentException("Handler for hub type event " + hubEvent.getType() + " not found");
        }

    }


}
