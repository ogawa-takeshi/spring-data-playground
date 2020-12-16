package playground;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface OrderRepository extends ElasticsearchRepository<Order, UUID> {

}
