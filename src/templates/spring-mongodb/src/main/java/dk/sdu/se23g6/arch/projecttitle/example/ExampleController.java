package dk.sdu.se23g6.arch.projecttitle.example;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {
    private final UserRepository userRepo;

    public ExampleController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    List<User> getExample() {
        return userRepo.findAll();
    }

}
