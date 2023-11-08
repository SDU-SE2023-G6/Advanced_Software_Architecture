package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.amqp.AMQPConfiguration;
import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrder;
import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrderStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductionOrderSender {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderSender.class);
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    public ProductionOrderSender(@Autowired AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    public void sendProductionOrderRequest(ProductionOrder productionOrder) {
        CompletableFuture<List<ProductionOrderStep>> future = this.asyncRabbitTemplate.convertSendAndReceive(
                AMQPConfiguration.getProductionOrderQueueName(),
                productionOrder
        );
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Production order response received.");
                log.info(String.valueOf(result.size()));
            }
            else {
                log.error("An error occurred. Production order incomplete.");
                log.error(ex.getMessage());
            }
        });

    }

}
