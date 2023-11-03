package dk.sdu.se23g6.arch.projecttitle.models.Order;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Data
public class Order {
    private String orderId;
    private List<OrderStep> steps; // Filename for program

    public Order(String orderId, List<String> stepIds) {
        this.orderId = orderId;
        this.steps = new ArrayList<>();
        stepIds.stream().forEach(step ->  {
           steps.add(new OrderStep(step, StepStatus.NOT_STARTED));
        });
    }
}
