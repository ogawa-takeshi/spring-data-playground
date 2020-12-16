package playground;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface Orders extends MongoRepository<Order, UUID> {

}
