package telemetry.collector.service.handler.sensor;

import telemetry.collector.model.sensor.SensorEvent;
import telemetry.collector.model.sensor.SensorEventType;

public interface SensorEventHandler {

    SensorEventType getMessageType();

    void handle(SensorEvent sensorEvent);

}
