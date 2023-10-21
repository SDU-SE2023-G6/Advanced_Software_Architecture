package dk.sdu.se23g6.arch.projecttitle.example;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document
public class User {
    @MongoId
    private ObjectId id;
    private String name;

    public User(String name) {
        this.name = name;
    }
}
