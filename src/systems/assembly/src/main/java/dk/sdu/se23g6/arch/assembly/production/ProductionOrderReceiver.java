package dk.sdu.se23g6.arch.assembly.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ProductionOrderReceiver {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderReceiver.class);
    private static final String PRODUCTION_ORDER_QUEUE = "productionOrders";

    // TODO find a way of returning the production steps independently
//    @RabbitListener(queues = PRODUCTION_ORDER_QUEUE)
//    public ProductionStep listen(@Payload ProductionOrder incomingProductionOrder) {
//        System.out.println("Received order from the Supervisor system with orderId " + incomingProductionOrder.getOrderId());
//        System.out.println("Processing " + incomingProductionOrder.getSteps().size() + " steps.");
//        for (ProductionStep productionStep : incomingProductionOrder.getSteps()) {
//            try {
//                // Interrupt the process for 1 to 10 seconds
//                int factor = ThreadLocalRandom.current().nextInt(1, 11);
//                Thread.sleep(factor * 1000L);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//                // TODO Direct Reply-To to Supervisor system: "error occurred"
//                continue;
//            }
//            productionStep.setOrderStatus(StepStatus.COMPLETED);
//            System.out.println("Completing orders ");
//            productionStep.setOrderStatus(StepStatus.COMPLETED);
////            this.template.send(PRODUCTION_ORDER_QUEUE, converter.toMessage(productionStep, new MessageProperties()));
//            // TODO Direct Reply-To to Supervisor system: "production step status update"
//            return productionStep;
//        }
//        return null;
//    }

    @RabbitListener(queues = PRODUCTION_ORDER_QUEUE)
    public String listen(@Payload String incomingMessage) {
        log.info("Production order request received.");
        log.info(incomingMessage);
        return incomingMessage;
    }

}
