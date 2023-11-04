package dk.sdu.se23g6.arch.projecttitle.models.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Order {
    private String orderId;
    private List<OrderStep> steps; // Filename for program

    public Order() {
        // Default no-argument constructor
    }

    public Order(String orderId, List<String> stepIds) {
        this.orderId = orderId;
        this.steps = new ArrayList<>();
        stepIds.forEach(step -> {
            steps.add(new OrderStep(step, StepStatus.NOT_STARTED));
        });
    }
}
