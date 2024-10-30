package ru.yandex.practicum.telemetry.analyzer.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "actionId")
@Builder
@RequiredArgsConstructor
@Table(name = "actions")
public class Action {

    @Id
    private Long actionId;

    @Column(name = "value")
    @Enumerated(EnumType.STRING)
    private ActionType type;

    @Column(name = "value")
    private Integer value;

}
