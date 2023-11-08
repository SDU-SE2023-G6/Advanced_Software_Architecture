package dk.sdu.se23g6.arch.assembly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrder {
    private String orderId;
    private List<ProductionOrderStep> steps;
}
