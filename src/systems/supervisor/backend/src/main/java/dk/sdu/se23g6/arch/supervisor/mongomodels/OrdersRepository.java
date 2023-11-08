package dk.sdu.se23g6.arch.supervisor.mongomodels;

import dk.sdu.se23g6.arch.supervisor.models.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order, String> {
}
