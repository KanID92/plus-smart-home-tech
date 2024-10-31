package ru.yandex.practicum.telemetry.analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.analyzer.entity.Scenario;
import ru.yandex.practicum.telemetry.analyzer.entity.ScenarioCondition;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private ScenarioRepository scenarioRepository;

    public void analyze(SensorsSnapshotAvro sensorsSnapshot) {

        String hubId = sensorsSnapshot.getHubId();

        Set<Scenario> hubScenario = scenarioRepository.findByHubId(hubId);

        for (Scenario scenario : hubScenario) {

        }

    }

    private boolean isScenarioTriggered(Scenario scenario, SensorsSnapshotAvro sensorsSnapshot) {
        for (ScenarioCondition condition : scenario.getScenarioConditions()) {
            if(!isConditionTriggered(condition, sensorsSnapshot)) {
                return false;
            }
        }
        return true;
    }

    private boolean isConditionTriggered(ScenarioCondition condition, SensorsSnapshotAvro snapshot) {
        SensorStateAvro sensorState = snapshot.getSensorsState().get(condition.getSensorId());

        //TODO Check for null??

        switch(condition.getType()) {
            case MOTION -> {
                if()
            }
        }

    }
}
