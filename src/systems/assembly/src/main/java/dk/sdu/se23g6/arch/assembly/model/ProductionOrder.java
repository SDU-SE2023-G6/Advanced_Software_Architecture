package dk.sdu.se23g6.arch.assembly.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class ProductionOrder {
    private String orderId;
    // Filenames for production machine programs
    private List<ProductionStep> steps;

    public ProductionOrder(String orderId) {
        this.orderId = orderId;
        this.steps = new ArrayList<>();
    }
}
