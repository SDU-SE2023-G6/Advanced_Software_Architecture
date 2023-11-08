package dk.sdu.se23g6.arch.supervisor.models.order;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Data
@Document
public class Order {
    @MongoId
    private String orderId;
    private List<OrderStep> steps;

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