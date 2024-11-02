package ru.yandex.practicum.telemetry.analyzer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.telemetry.analyzer.entity.Action;

import java.util.Set;

public interface ActionRepository extends JpaRepository<Action, Long> {

    void deleteAllByActionIdIn(Set<Long> actionIds);

}
