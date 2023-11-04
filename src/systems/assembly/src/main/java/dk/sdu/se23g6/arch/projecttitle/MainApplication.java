package dk.sdu.se23g6.arch.projecttitle;


import dk.sdu.se23g6.arch.projecttitle.models.order.Order;
import dk.sdu.se23g6.arch.projecttitle.models.order.OrderStep;
import dk.sdu.se23g6.arch.projecttitle.models.order.StepStatus;
import dk.sdu.se23g6.arch.projecttitle.models.order.dto.OrderStepDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Random;

/** Application entrypoint. */
@EnableAsync
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
    System.out.println("Received order from the Supervisor system with orderId " + in.getOrderId());

    Random gen = new Random();
    System.out.println("Processing " + in.getSteps().size() + " steps.");

    for (OrderStep step : in.getSteps()) {
      boolean shouldComplete = gen.nextBoolean();
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        System.out.println("Timeout error happened");
      }
      step.setOrderStatus(StepStatus.COMPLETED);
      System.out.println("Completing orders " + );
      OrderStepDTO orderStepDTO = new OrderStepDTO(in.getOrderId(), step.getStepId(), StepStatus.COMPLETED);
      this.template.send(SENDER_QUEUE, converter.toMessage(orderStepDTO, null));
    }
  }

}
