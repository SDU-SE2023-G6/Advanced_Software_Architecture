package dk.sdu.se23g6.arch.supervisor.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        template.setMessageConverter(new SimpleMessageConverter());
        template.setConnectionFactory(factory);
        AsyncRabbitTemplate asyncTemplate = new AsyncRabbitTemplate(template);
        asyncTemplate.setReceiveTimeout(30000); // 30 seconds
        return asyncTemplate;
    }

}


