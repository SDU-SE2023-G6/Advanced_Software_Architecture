package dk.sdu.se23g6.arch.projecttitle;

import com.mongodb.client.model.UpdateOneModel;
import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.AssemblyOrderRepository;
import dk.sdu.se23g6.arch.projecttitle.example.MongoModels.OrdersRepository;
import dk.sdu.se23g6.arch.projecttitle.example.models.CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.example.models.Order;
import dk.sdu.se23g6.arch.projecttitle.example.models.AssemblyOrder;
import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.handler.annotation.Payload;

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
    System.out.println("OrderId: " + in.orderId() + " and stepId: " + in.stepId());
  }

  @RabbitListener(queues = "assemblyOrders")
  public void listenToAssemblyResponse(@Payload AssemblyOrder in) {
    UpdateOneModel<AssemblyOrderRepository> updateOneModel = new UpdateOneModel<AssemblyOrderRepository>();
    assemblyOrderRepository.findBy();
    System.out.println("Recieved " + in.getOrderId() + " and stepId: " + in.getStepId());
  }
  @Bean
  public CommandLineRunner initDatabase(RabbitTemplate template, MessageConverter converter) {
    return args -> {
      ordersRepository.deleteAll();
      assemblyOrderRepository.deleteAll();

      Order initOrder = new Order("10", "client.py");
      ordersRepository.save(initOrder);
      CreateOrderDTO newOrder = new CreateOrderDTO(initOrder.getStepId(), initOrder.getOrderId());
      template.send("orders", converter.toMessage(newOrder, null));
    };
  }
}
