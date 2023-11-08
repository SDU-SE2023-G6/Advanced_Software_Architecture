package dk.sdu.se23g6.arch.supervisor.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        this.trigger.sendProductionOrderRequest();
        return "200 OK";
    }

}
