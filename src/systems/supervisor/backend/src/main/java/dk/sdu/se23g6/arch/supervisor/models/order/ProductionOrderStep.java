package dk.sdu.se23g6.arch.supervisor.models.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderStep {
    private String stepId;
    private StepStatus orderStatus;
}
