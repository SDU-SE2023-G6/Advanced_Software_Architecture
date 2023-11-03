package dk.sdu.se23g6.arch.projecttitle;

import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.AssemblyOrderRepository;
import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.OrdersRepository;
import CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.models.Order.Order;
import dk.sdu.se23g6.arch.projecttitle.example.models.AssemblySystem.AssemblyOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.ArrayList;
import java.util.List;

/** Application entrypoint. */
@SpringBootApplication
public class MainApplication {

  private final OrdersRepository ordersRepository;
  private final AssemblyOrderRepository assemblyOrderRepository;

  public MainApplication(OrdersRepository ordersRepository, AssemblyOrderRepository assemblyOrderRepository) {
    this.ordersRepository = ordersRepository;
    this.assemblyOrderRepository = assemblyOrderRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @RabbitListener(queues = "orders")
  public void listenToOrders (@Payload CreateOrderDTO in) {
    System.out.println("Order with " + in.steps().size() + " steps");
  }

  @RabbitListener(queues = "assemblyOrders")
  public void listenToAssemblyResponse(@Payload AssemblyOrder in) {
    assemblyOrderRepository.save(in);
    System.out.println("Recieved " + in.getOrderId() + " and stepId: " + in.getStepId());
  }
  @Bean
  public CommandLineRunner initDatabase(RabbitTemplate template, MessageConverter converter) {
    return args -> {
      ordersRepository.deleteAll();
      assemblyOrderRepository.deleteAll();

      List<String> orderSteps = new ArrayList<>();
      orderSteps.add("./attach-wheel.py");
      orderSteps.add("./attach-pedals.py");
      orderSteps.add("./attach-chain.py");

      Order initOrder = new Order("10", orderSteps);
      Order order = ordersRepository.save(initOrder);
      CreateOrderDTO newOrder = new CreateOrderDTO(orderSteps);
      template.send("orders", converter.toMessage(newOrder, null));
    };
  }
}
