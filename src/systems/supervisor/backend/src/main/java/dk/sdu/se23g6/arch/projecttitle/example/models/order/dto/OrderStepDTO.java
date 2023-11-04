package dk.sdu.se23g6.arch.projecttitle.example.models.order.dto;

import dk.sdu.se23g6.arch.projecttitle.example.models.order.StepStatus;
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
