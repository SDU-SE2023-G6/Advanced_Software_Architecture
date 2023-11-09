package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.model.ProductionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trigger")
public class ProductionOrderController {

    private final ProductionOrderSender trigger;

    public ProductionOrderController(@Autowired ProductionOrderSender productionOrderSender) {
        this.trigger = productionOrderSender;
    }

    @GetMapping()
    @ResponseBody
    public String trigger() {
        ProductionOrder productionOrder = new ProductionOrder(
                "test-order-id-A",
                List.of("step1", "step2", "step3", "step4", "step5")
        );
        this.trigger.sendProductionOrderRequest(productionOrder);
        ProductionOrder productionOrder2 = new ProductionOrder(
                "test-order-id-AA",
                List.of("step11", "step22", "step33", "step44", "step55")
        );
        this.trigger.sendProductionOrderRequest(productionOrder2);
        ProductionOrder productionOrder3 = new ProductionOrder(
                "test-order-id-AAA",
                List.of("step111", "step222", "step333", "step444", "step555")
        );
        this.trigger.sendProductionOrderRequest(productionOrder3);
        ProductionOrder productionOrder4 = new ProductionOrder(
                "test-order-id-AAAA",
                List.of("step1111", "step2222", "step3333", "step4444", "step5555")
        );
        this.trigger.sendProductionOrderRequest(productionOrder4);
        ProductionOrder productionOrder5 = new ProductionOrder(
                "test-order-id-AAAAAA",
                List.of("step11111", "step22222", "step33333", "step44444", "step55555")
        );
        this.trigger.sendProductionOrderRequest(productionOrder5);
        return "200 OK";
    }

}
