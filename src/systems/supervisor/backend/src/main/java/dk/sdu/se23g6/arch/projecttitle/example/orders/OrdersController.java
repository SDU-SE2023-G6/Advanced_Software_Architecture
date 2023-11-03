package dk.sdu.se23g6.arch.projecttitle.example.orders;

import dk.sdu.se23g6.arch.projecttitle.example.models.Order;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.OrderError;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface OrdersController {

    /**
     * Send a message to the broker
     * @param order the order you want to send
     * @return message as string
     */
    String send(CreateOrderDTO order);

    /**
     * Get the list of completed orders. This clears the queue of orders.
     * @return list of completed orders.
     */
    List<Order> completed();

    /**
     * Return a list of errors from the AmqpBroker. This clears the queue of errors.
     * @return list of errors.
     */
    List<OrderError> errors();
}
