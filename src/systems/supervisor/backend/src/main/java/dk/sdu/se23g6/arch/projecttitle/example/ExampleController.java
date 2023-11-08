package dk.sdu.se23g6.arch.projecttitle.example;


import dk.sdu.se23g6.arch.projecttitle.example.mongomodels.AssemblyOrderRepository;
import dk.sdu.se23g6.arch.projecttitle.example.mongomodels.OrdersRepository;
import dk.sdu.se23g6.arch.projecttitle.example.models.assemblysystem.AssemblyOrder;
import dk.sdu.se23g6.arch.projecttitle.example.models.order.dto.CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.models.order.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

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
    public String send(@RequestBody CreateOrderDTO orderDTO) {
        Random randomGenerator = new Random();

        int orderId = randomGenerator.nextInt(0, (int) Math.pow(10,10));
        Order newOrder = new Order(String.valueOf(orderId), orderDTO.steps());
        this.ordersRepository.save(newOrder);

        this.template.send(this.QUEUE_KEY, converter.toMessage(newOrder, null));
        return "OK";
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        List<Order> orders = ordersRepository.findAll();
        return orders;
    }

}
