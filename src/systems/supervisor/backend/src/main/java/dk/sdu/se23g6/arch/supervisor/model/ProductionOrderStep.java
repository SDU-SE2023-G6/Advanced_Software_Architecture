package dk.sdu.se23g6.arch.supervisor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderStep {
    private String orderId;
    private String stepId; // maps to file name of production program of a production machine in S3
    private StepStatus stepStatus;
}
