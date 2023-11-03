package dk.sdu.se23g6.arch.projecttitle.example.MongoModels;

import dk.sdu.se23g6.arch.projecttitle.example.models.AssemblySystem.AssemblyOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssemblyOrderRepository extends MongoRepository<AssemblyOrder, String> { }
