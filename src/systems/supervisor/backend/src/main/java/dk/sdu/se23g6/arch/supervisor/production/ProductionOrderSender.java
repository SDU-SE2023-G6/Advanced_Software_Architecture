package dk.sdu.se23g6.arch.supervisor.production;

import dk.sdu.se23g6.arch.supervisor.amqp.AMQPConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductionOrderSender {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderSender.class);
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    public ProductionOrderSender(@Autowired AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    public void sendProductionOrderRequest() {
        CompletableFuture<String> future = this.asyncRabbitTemplate.convertSendAndReceive(
                AMQPConfiguration.getProductionOrderQueueName(),
                "Hello World"
        );
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Production order response received.");
                log.info(result);
            }
            else {
                log.error("An error occurred. Production order incomplete.");
            }
        });

    }

}
