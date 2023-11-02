package dk.sdu.se23g6.arch.projecttitle.example;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String name;

    public User(String name) {
        this.name = name;
    }
}
