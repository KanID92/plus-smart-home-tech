package ru.yandex.practicum.telemetry.analyzer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.telemetry.analyzer.entity.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    public void deleteByName(String scenarioName);

}
