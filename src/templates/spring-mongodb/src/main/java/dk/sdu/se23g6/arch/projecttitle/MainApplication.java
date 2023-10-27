package dk.sdu.se23g6.arch.projecttitle;

import dk.sdu.se23g6.arch.projecttitle.example.User;
import dk.sdu.se23g6.arch.projecttitle.example.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/** Application entrypoint. */
@SpringBootApplication
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDatabase(UserRepository userRepository) {
    return args -> {
      userRepository.saveAll(List.of(
         new User("John"),
         new User("Paul")
      ));
    };
  }
}
