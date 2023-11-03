package dk.sdu.se23g6.arch.projecttitle.example.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Order {
    private String stepId; // Filename for program
    private String orderId;

    public Order(String orderId, String stepId) {
        this.orderId = orderId;
        this.stepId = stepId;
    }
}
