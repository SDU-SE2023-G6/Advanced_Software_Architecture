package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrder;
import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrderStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.List;

@Controller
@RequestMapping("/trigger")
public class ProductionTriggerController {

    private final ProductionOrderSender trigger;

    public ProductionTriggerController(@Autowired ProductionOrderSender trigger) {
        this.trigger = trigger;
    }

    @GetMapping()
    @ResponseBody
    public String trigger() {
        ProductionOrder productionOrder = new ProductionOrder(
                "test-order-id",
                List.of("step1", "step2", "step3", "step4", "step5")
        );
        this.trigger.sendProductionOrderRequest(productionOrder);
        return "200 OK";
    }

}
