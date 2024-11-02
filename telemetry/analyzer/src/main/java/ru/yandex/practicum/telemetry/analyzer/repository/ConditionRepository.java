package ru.yandex.practicum.telemetry.analyzer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.telemetry.analyzer.entity.ScenarioCondition;

import java.util.Set;

public interface ConditionRepository extends JpaRepository<ScenarioCondition, Long> {

    void deleteAllByConditionIdIn(Set<Long> ids);
}
