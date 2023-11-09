package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.ProductionOrder;
import dk.sdu.se23g6.arch.assembly.model.ProductionOrderStep;
import dk.sdu.se23g6.arch.assembly.model.StepStatus;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class ProductionOrderReceiver {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderReceiver.class);
    private static final String PRODUCTION_ORDER_QUEUE = "productionOrders";

    @RabbitListener(queues = PRODUCTION_ORDER_QUEUE)
    public List<ProductionOrderStep> listen(@Payload ProductionOrder incomingOrder) {
        log.info("Production order " + incomingOrder.getOrderId() + " request received.");
        log.info("Processing " + incomingOrder.getSteps().size() + " steps.");
        StopWatch stopWatch = new StopWatch();
        for (ProductionOrderStep step : incomingOrder.getSteps()) {
            try {
                stopWatch.start();
                log.info("Processing step " + step);
                step.setStepStatus(StepStatus.IN_PROGRESS);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 1000L); // 1-3 seconds
                step.setStepStatus(StepStatus.COMPLETED);
                log.info("Step " + step + " completed in " + stopWatch.getTime(TimeUnit.MILLISECONDS) + " ms.");
            } catch (InterruptedException e) {
                step.setStepStatus(StepStatus.FAILED);
                log.error(e.getMessage());
            }
            stopWatch.reset();
        }
        log.info("Production process for order " + incomingOrder.getOrderId() + " completed.");
        return incomingOrder.getSteps();
    }

}
