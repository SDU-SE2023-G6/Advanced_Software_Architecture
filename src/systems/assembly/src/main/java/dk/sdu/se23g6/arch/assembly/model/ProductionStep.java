package dk.sdu.se23g6.arch.assembly.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductionStep {
    private String orderId;
    private String stepId;
    private StepStatus orderStatus;
}
