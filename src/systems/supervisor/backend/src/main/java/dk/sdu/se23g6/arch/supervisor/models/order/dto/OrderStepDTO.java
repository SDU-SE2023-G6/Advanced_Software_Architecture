package dk.sdu.se23g6.arch.supervisor.models.order.dto;

import dk.sdu.se23g6.arch.supervisor.models.order.StepStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderStepDTO {
    private String orderId;
    private String stepId;
    private StepStatus status;
}
