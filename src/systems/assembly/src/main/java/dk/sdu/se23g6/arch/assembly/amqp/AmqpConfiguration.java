package dk.sdu.se23g6.arch.assembly.amqp;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

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

}


