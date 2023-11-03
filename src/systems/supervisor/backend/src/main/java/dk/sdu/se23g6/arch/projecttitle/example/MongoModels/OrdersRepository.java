package dk.sdu.se23g6.arch.projecttitle.example.MongoModels;

import dk.sdu.se23g6.arch.projecttitle.example.models.Order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order, String> {
}
