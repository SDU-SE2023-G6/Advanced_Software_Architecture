package dk.sdu.se23g6.arch.projecttitle.example.models.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStep {

    private String stepId;
    private StepStatus orderStatus;

}
