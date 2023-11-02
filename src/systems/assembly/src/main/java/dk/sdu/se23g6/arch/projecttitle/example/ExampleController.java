package dk.sdu.se23g6.arch.projecttitle.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping
    String getExample() {
        return "Hello World!";
    }

    @GetMapping("/random-data")
    RandomData getRandomData() {
        return RandomDataGenerator.generateRandomData();
    }

}
