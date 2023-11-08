package dk.sdu.se23g6.arch.supervisor.mongomodels;

import dk.sdu.se23g6.arch.supervisor.models.assemblysystem.AssemblyOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssemblyOrderRepository extends MongoRepository<AssemblyOrder, String> { }
