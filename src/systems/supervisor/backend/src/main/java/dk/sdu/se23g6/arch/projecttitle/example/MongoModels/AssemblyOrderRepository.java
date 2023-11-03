package dk.sdu.se23g6.arch.projecttitle.example.MongoModels;

import dk.sdu.se23g6.arch.projecttitle.example.models.AssemblyOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssemblyOrderRepository extends MongoRepository<AssemblyOrder, String> {
}
