package dk.sdu.se23g6.arch.projecttitle.example;


import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.AssemblyOrderRepository;
import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.OrdersRepository;
import dk.sdu.se23g6.arch.projecttitle.example.models.AssemblySystem.AssemblyOrder;
import CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.models.Order.Order;
import dk.sdu.se23g6.arch.projecttitle.example.models.Order.OrderError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {

    private final OrdersRepository ordersRepository;
    private final AssemblyOrderRepository assemblyOrderRepository;

    @GetMapping
    String getExample() {
        return "Hello world";
    }

    private final RabbitTemplate template;
    private final MessageConverter converter;
    private final String QUEUE_KEY = "orders";
    public ExampleController(OrdersRepository ordersRepository, AssemblyOrderRepository assemblyOrderRepository, RabbitTemplate template, MessageConverter converter) {
        this.ordersRepository = ordersRepository;
        this.assemblyOrderRepository = assemblyOrderRepository;
        this.template = template;
        this.converter = converter;
    }

    @PostMapping("/order/create")
    @ApiResponse(responseCode = "200", description = "Send a order to the assembly system")
    public String send(@RequestBody CreateOrderDTO order) {
        this.template.send(this.QUEUE_KEY, converter.toMessage(order, null));
        Order newOrder = new Order(order.stepId());
        this.ordersRepository.save(new Order(order.orderId(), order.stepId()));
        return "OK";
    }

    public List<AssemblyOrder> completed() {
        List<AssemblyOrder> orders = assemblyOrderRepository.findAll();
        assemblyOrderRepository.deleteAll();
        return orders;
    }
    public List<OrderError> errors() {
        return null;
    }

}
