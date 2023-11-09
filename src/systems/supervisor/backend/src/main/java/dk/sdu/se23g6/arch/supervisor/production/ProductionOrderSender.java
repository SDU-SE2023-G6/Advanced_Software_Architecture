package dk.sdu.se23g6.arch.supervisor.production;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.se23g6.arch.supervisor.model.ProductionOrder;
import dk.sdu.se23g6.arch.supervisor.model.ProductionOrderStep;
import dk.sdu.se23g6.arch.supervisor.model.StepStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductionOrderSender {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderSender.class);
    public static final String PRODUCTION_ORDER_QUEUE = "productionOrders";
    public static final String PRODUCTION_ORDER_RESULT_QUEUE = "productionOrderResults";
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Bean
    public Queue productionOrderQueue() {
        return new Queue(PRODUCTION_ORDER_QUEUE, true);
    }

    @Bean
    public Queue productionOrderResultQueue() {
        return new Queue(PRODUCTION_ORDER_RESULT_QUEUE, true);
    }

    public ProductionOrderSender(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendProductionOrderRequest(ProductionOrder productionOrder) {
        this.rabbitTemplate.convertAndSend(PRODUCTION_ORDER_QUEUE, productionOrder);
        // TODO persist in MongoDB
    }

    @RabbitListener(queues = PRODUCTION_ORDER_RESULT_QUEUE)
    public void listenToProductionOrderResults(@Payload List<Map<String, String>> json) {
        List<ProductionOrderStep> productionOrderSteps = objectMapper.convertValue(json, new TypeReference<>(){});
                String orderIdPrefix = "[Order " + productionOrderSteps.get(0).getOrderId() + "] ";
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
        // TODO persist in MongoDB
    }

}
