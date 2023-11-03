package dk.sdu.se23g6.arch.projecttitle.example.orders.impl;

import dk.sdu.se23g6.arch.projecttitle.example.models.Order;
import dk.sdu.se23g6.arch.projecttitle.example.orders.OrdersController;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.OrderError;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DefaultOrdersController implements OrdersController {

    private final RabbitTemplate template;
    private final MessageConverter converter;
    private final String QUEUE_KEY = "orders";
    public DefaultOrdersController(RabbitTemplate template, MessageConverter converter) {
        this.template = template;
        this.converter = converter;
    }

    @Override
    public String send(CreateOrderDTO order) {
        this.template.send(this.QUEUE_KEY, converter.toMessage(order, null));
        return "OK";
    }

    @Override
    public List<Order> completed() {
        return null;
    }

    @Override
    public List<OrderError> errors() {
        return null;
    }
}
