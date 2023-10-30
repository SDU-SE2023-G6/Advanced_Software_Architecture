package dk.sdu.se23g6.arch.projecttitle;

import dk.sdu.se23g6.arch.projecttitle.example.AmqpModule.AmqpSender;
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
  public CommandLineRunner initDatabase(AmqpSender amqpSender) {
    return args -> {

      amqpSender.sendMessage("public", "Hello world");
    };
  }
}
