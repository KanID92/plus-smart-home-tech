package ru.yandex.practicum.telemetry.analyzer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.telemetry.analyzer.entity.Scenario;

import java.util.Optional;
import java.util.Set;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    void deleteByNameAndHubId(String scenarioName, String hubId);

    Set<Scenario> findByHubId(String hubId);

    Optional<Scenario> findByNameAndHubId(String name, String hubId);

}
