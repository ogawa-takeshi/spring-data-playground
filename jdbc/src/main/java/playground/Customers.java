package playground;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Customers extends CrudRepository<Customer, UUID> {

    Optional<Customer> findByName(String name);

    Collection<CustomerSummary> findAllSummaryBy();

}
