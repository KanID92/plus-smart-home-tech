package ru.yandex.practicum.collector.service.handler.hub;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.collector.model.hub.DeviceAction;
import ru.yandex.practicum.collector.model.hub.ScenarioCondition;
import ru.yandex.practicum.collector.model.hub.enums.ActionType;
import ru.yandex.practicum.collector.model.hub.enums.ConditionOperation;
import ru.yandex.practicum.collector.model.hub.enums.ConditionType;
import ru.yandex.practicum.collector.model.hub.enums.DeviceType;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.Properties;

public abstract class BaseHubHandler implements HubEventHandler {

    Properties properties;

    @Value("${collector.kafka.producer.properties.bootstrap.servers}")
    String servers;

    @Value("${collector.kafka.producer.properties.client.id}")
    String client;

    @Value("${collector.kafka.producer.properties.key.serializer}")
    String keySerializer;

    @Value("${collector.kafka.producer.properties.value.serializer}")
    String valueSerializer;

    @Value("${collector.kafka.producer.topics.hub-events}")
    String topic;

    public BaseHubHandler() {
        properties = new Properties();
        System.out.println(servers);
        System.out.println(client);
        System.out.println(keySerializer);
        System.out.println(valueSerializer);
        System.out.println(topic);

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, client);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    }

    DeviceTypeAvro toDeviceTypeAvro(DeviceType deviceType) {
        return switch (deviceType) {
            case DeviceType.MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case DeviceType.CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case DeviceType.LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case DeviceType.SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
            case DeviceType.TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
        };
    }

    DeviceActionAvro toDeviceActionAvro(DeviceAction deviceAction) {

        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(toActionTypeAvro(deviceAction.getType()))
                .setValue(deviceAction.getValue())
                .build();
    }

    ActionTypeAvro toActionTypeAvro(ActionType actionType) {
        return switch (actionType) {
            case ActionType.ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case ActionType.DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case ActionType.INVERSE -> ActionTypeAvro.INVERSE;
            case ActionType.SET_VALUE-> ActionTypeAvro.SET_VALUE;
        };
    }

    ScenarioConditionAvro toScenarioConditionAvro(ScenarioCondition scenarioCondition) {

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setType(toConditionTypeAvro(scenarioCondition.getType()))
                .setValue(scenarioCondition.getValue())
                .setOperation(toConditionOperationAvro(scenarioCondition.getOperation()))
                .build();
    }

    ConditionTypeAvro toConditionTypeAvro(ConditionType conditionType) {
        return switch (conditionType) {
            case ConditionType.MOTION -> ConditionTypeAvro.MOTION;
            case ConditionType.LUMINOSITY-> ConditionTypeAvro.LUMINOSITY;
            case ConditionType.SWITCH -> ConditionTypeAvro.SWITCH;
            case ConditionType.TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case ConditionType.CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case ConditionType.HUMIDITY -> ConditionTypeAvro.HUMIDITY;
        };
    }

    ConditionOperationAvro toConditionOperationAvro(ConditionOperation conditionOperation) {
        return switch (conditionOperation) {
            case ConditionOperation.EQUALS -> ConditionOperationAvro.EQUALS;
            case ConditionOperation.GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case ConditionOperation.LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
        };
    }




}
