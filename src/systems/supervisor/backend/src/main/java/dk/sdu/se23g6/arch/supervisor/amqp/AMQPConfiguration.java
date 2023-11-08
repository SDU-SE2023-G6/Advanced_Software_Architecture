package dk.sdu.se23g6.arch.supervisor.amqp;

import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrder;
import dk.sdu.se23g6.arch.supervisor.models.order.ProductionOrderStep;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AMQPConfiguration {

    public static final String PRODUCTION_ORDER_QUEUE = "productionOrders";

    public static String getProductionOrderQueueName() {
        return PRODUCTION_ORDER_QUEUE;
    }

    /**
     * Create production order queue.
     * Supervisor produces production orders to the queue.
     * Assembly consumes production orders from the queue.
     * @return production order queue
     */
    @Bean
    public Queue productionOrderQueue() {
        return new Queue(PRODUCTION_ORDER_QUEUE, true);
    }

    /**
     * Creates a default ASYNC template for RabbitMQ.
     * @param factory Injected factory
     * @return RabbitMQ template
     */
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate();
        template.setMessageConverter(jsonMessageConverter());
        template.setConnectionFactory(factory);
        return new AsyncRabbitTemplate(template);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("productionOrder", ProductionOrder.class);
        idClassMapping.put("productionOrderStep", ProductionOrderStep.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

}


