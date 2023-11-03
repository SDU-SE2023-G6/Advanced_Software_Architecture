package dk.sdu.se23g6.arch.projecttitle.example.AmqpModule;

import dk.sdu.se23g6.arch.projecttitle.example.models.RandomData;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.CreateOrderDTO;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AmqpListener {

    @RabbitListener(queues = "orders")
    public void listenToOrders (@Payload CreateOrderDTO in) {
        System.out.println("OrderId: " + in.orderId() + " and stepId: " + in.stepId());
    }

    @RabbitListener(queues = "assembly.orders")
    public void listenToAssemblyResponse(@Payload RandomData in) {
        System.out.println("Recieved " + in.getOrderId() + " and stepId: " + in.getStepId());
    }
}
