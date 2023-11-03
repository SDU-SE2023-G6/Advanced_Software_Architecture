package dk.sdu.se23g6.arch.projecttitle;

import dk.sdu.se23g6.arch.projecttitle.example.AmqpModule.AmqpSender;
import dk.sdu.se23g6.arch.projecttitle.example.orders.models.CreateOrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/** Application entrypoint. */
@SpringBootApplication
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDatabase(RabbitTemplate template, MessageConverter converter, AmqpSender sender) {
    return args -> {
      CreateOrderDTO newOrder = new CreateOrderDTO("./attach-wheel.py", 10);
      template.send("orders", converter.toMessage(newOrder, null));
    };
  }
}
