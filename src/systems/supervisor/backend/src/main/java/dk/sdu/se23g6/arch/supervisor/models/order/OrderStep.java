package dk.sdu.se23g6.arch.supervisor.models.order;

import lombok.Data;

@Data
public class OrderStep {
    private String stepId;
    private StepStatus orderStatus;

    public OrderStep() {
        // Default no-argument constructor
    }

    public OrderStep(String stepId, StepStatus orderStatus) {
        this.stepId = stepId;
        this.orderStatus = orderStatus;
    }
}
