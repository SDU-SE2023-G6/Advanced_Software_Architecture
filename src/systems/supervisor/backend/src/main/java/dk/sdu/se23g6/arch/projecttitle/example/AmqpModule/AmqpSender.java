package dk.sdu.se23g6.arch.projecttitle.example.AmqpModule;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AmqpSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    public AmqpSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
