package dk.sdu.se23g6.arch.projecttitle.models.order.dto;

import dk.sdu.se23g6.arch.projecttitle.models.order.StepStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderStepDTO {
    private String orderId;
    private String stepId;
    private StepStatus status;
}
