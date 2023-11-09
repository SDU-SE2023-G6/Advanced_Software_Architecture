package dk.sdu.se23g6.arch.assembly.production;

import dk.sdu.se23g6.arch.assembly.model.ProductionOrder;
import dk.sdu.se23g6.arch.assembly.model.ProductionOrderStep;
import dk.sdu.se23g6.arch.assembly.model.StepStatus;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class ProductionOrderReceiver {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderReceiver.class);
    private static final String PRODUCTION_ORDER_QUEUE = "productionOrders";
    private static final String PRODUCTION_ORDER_RESULT_QUEUE = "productionOrderResults";

    private final RabbitTemplate rabbitTemplate;

    public ProductionOrderReceiver(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = PRODUCTION_ORDER_QUEUE)
    public void listen(@Payload ProductionOrder productionOrder) {
        log.info("Production order " + productionOrder.getOrderId() + " request received.");
        log.info("Processing " + productionOrder.getSteps().size() + " steps.");
        StopWatch stopWatch = new StopWatch();
        for (ProductionOrderStep step : productionOrder.getSteps()) {
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
        log.info("Production process for order " + productionOrder.getOrderId() + " completed.");
        this.rabbitTemplate.convertAndSend(PRODUCTION_ORDER_RESULT_QUEUE, productionOrder.getSteps());
    }

}
