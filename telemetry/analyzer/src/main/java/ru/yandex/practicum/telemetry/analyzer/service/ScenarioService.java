package ru.yandex.practicum.telemetry.analyzer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.analyzer.entity.Scenario;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    public void add(Scenario scenario) {
        scenarioRepository.save(scenario);
    }

    public void delete(String name, String hubId) {
        scenarioRepository.deleteByNameAndHubId(name, hubId);
    }

    public Optional<Scenario> findByNameAndHubId(String name, String hubId) {
        return scenarioRepository.findByNameAndHubId(name, hubId);
    }

}
