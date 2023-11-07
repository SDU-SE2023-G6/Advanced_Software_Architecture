package dk.sdu.se23g6.arch.projecttitle.example.mongomodels;

import dk.sdu.se23g6.arch.projecttitle.example.models.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order, String> {
}
