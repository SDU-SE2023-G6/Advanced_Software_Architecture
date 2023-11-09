package dk.sdu.se23g6.arch.supervisor.production;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.se23g6.arch.supervisor.amqp.AMQPConfiguration;
import dk.sdu.se23g6.arch.supervisor.model.ProductionOrder;
import dk.sdu.se23g6.arch.supervisor.model.ProductionOrderStep;
import dk.sdu.se23g6.arch.supervisor.model.StepStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductionOrderSender {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderSender.class);
    private final AsyncRabbitTemplate asyncRabbitTemplate;
    private final ObjectMapper objectMapper;

    public ProductionOrderSender(@Autowired AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendProductionOrderRequest(ProductionOrder productionOrder) {
        CompletableFuture<List<ProductionOrderStep>> future = this.asyncRabbitTemplate.convertSendAndReceive(
                AMQPConfiguration.getProductionOrderQueueName(),
                productionOrder
        );
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                // Conversion from LinkedHashMap to ProductionOrderStep for every element.
                // There might be a way of configuring Jackson to do the conversion.
                List<ProductionOrderStep> productionOrderSteps = objectMapper.convertValue(result, new TypeReference<>() {
                });
                String orderIdPrefix = "[Order " + productionOrderSteps.get(0).getOrderId() + "] ";
                log.info(orderIdPrefix + "Production order response received.");
                List<String> successfulSteps = productionOrderSteps.stream()
                        .filter(productionOrderStep -> productionOrderStep.getStepStatus() == StepStatus.COMPLETED)
                        .map(ProductionOrderStep::getStepId)
                        .toList();
                log.info(orderIdPrefix + "Successfully completed steps: " + successfulSteps);
                List<String> unsuccessfulSteps = productionOrderSteps.stream()
                        .filter(productionOrderStep -> productionOrderStep.getStepStatus() != StepStatus.COMPLETED)
                        .map(ProductionOrderStep::getStepId)
                        .toList();
                if (unsuccessfulSteps.size() > 0) {
                    log.warn(orderIdPrefix + "Unsuccessfully completed steps: " + unsuccessfulSteps);
                }
                // TODO save to MongoDB
            } else {
                log.error("An error occurred. Production order incomplete.");
                log.error(ex.getMessage());
                // TODO save to MongoDB
            }
        });

    }

}
