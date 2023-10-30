package dk.sdu.se23g6.arch.projecttitle.example.AmqpModule;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }


    @Bean
    public Queue queue(){
        return new Queue("public", false);
    }

    @RabbitListener(queues = "public")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }

}


