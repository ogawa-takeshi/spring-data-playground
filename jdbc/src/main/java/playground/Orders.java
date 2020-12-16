package playground;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface Orders extends CrudRepository<Order, UUID> {

}
