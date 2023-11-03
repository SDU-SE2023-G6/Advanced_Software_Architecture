package dk.sdu.se23g6.arch.projecttitle;


import dk.sdu.se23g6.arch.projecttitle.example.RandomDataGenerator;
import dk.sdu.se23g6.arch.projecttitle.models.AssemblySystem.AssemblyOrder;
import dk.sdu.se23g6.arch.projecttitle.models.Order.CreateOrderDTO;
import dk.sdu.se23g6.arch.projecttitle.models.Order.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;

/** Application entrypoint. */
@SpringBootApplication
public class MainApplication {

  private final RabbitTemplate template;
  private final MessageConverter converter;
  private static final boolean NON_DURABLE = false;
  private static final String SENDER_QUEUE = "assemblyOrders";
  private static final String RECEIVER_QUEUE = "orders";

  public MainApplication(RabbitTemplate template, MessageConverter converter) {
    this.template = template;
    this.converter = converter;
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  /*
  @Bean
  public ApplicationRunner runner(RabbitTemplate template) {
    return args -> template.convertAndSend(sender, "Hello, world!");
  }
  */

  @Bean
  public Queue senderQueue() {
    return new Queue(SENDER_QUEUE, NON_DURABLE);
  }

  @Bean
  public Queue receiverQueue() {
    return new Queue(RECEIVER_QUEUE, NON_DURABLE);
  }

  @RabbitListener(queues = RECEIVER_QUEUE)
  public void listen(@Payload Order in) {
    System.out.println("Received order from the Supervisor system with orderId " + in.getOrderId() + " and " + in.getSteps().size() + " steps");
    RandomDataGenerator generator = new RandomDataGenerator();
    AssemblyOrder order = generator.createRandomResponse(in.getOrderId());
    Message message = converter.toMessage(order, null);
    this.template.send(SENDER_QUEUE, message);
  }

}
