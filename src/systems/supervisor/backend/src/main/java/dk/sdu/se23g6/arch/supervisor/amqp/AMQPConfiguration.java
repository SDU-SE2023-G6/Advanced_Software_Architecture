package dk.sdu.se23g6.arch.supervisor.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    private static final String SENDER_QUEUE = "orders";
    private static final String RECEIVER_QUEUE = "assemblyOrders";

    /**
     * Create a default template for RabbitMQ.
     * @param factory Injected factory
     * @param converter Injected message converter
     * @return RabbitMQ template
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        return template;
    }

    /**
     * Create a default message converter for the project.
     * @return the message converter.
     */
    @Bean
    MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue senderQueue() {
        return new Queue(SENDER_QUEUE, false);
    }

    @Bean
    Queue receiverQueue() {
        return new Queue(RECEIVER_QUEUE, false);
    }


}


