package dk.sdu.se23g6.arch.supervisor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
//@Document
public class ProductionOrder {
//    @MongoId
    private String orderId;
    private List<ProductionOrderStep> steps;

    public ProductionOrder(String orderId, List<String> stepIds) {
        this.orderId = orderId;
        this.steps = new ArrayList<>();
        stepIds.forEach(step -> steps.add(new ProductionOrderStep(orderId, step, StepStatus.NOT_STARTED)));
    }
}