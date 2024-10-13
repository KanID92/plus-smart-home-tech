package telemetry.collector.model.hub;

public class ScenarioAddedEvent extends HubEvent {

    private String name;



    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
