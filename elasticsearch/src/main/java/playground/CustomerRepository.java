package playground;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String>, CustomerStatisticsRepository {

    List<Customer> findAllByName(String name);
    List<Customer> readAllByName(String name);
    List<Customer> getAllByName(String name);
    List<Customer> queryAllByName(String name);
    List<Customer> searchAllByName(String name);
    List<Customer> streamAllByName(String name);

    long countByName(String name);
    Optional<Customer> existsByName(String name);
    int deleteAllByName(String name);
    int removeAllByName(String name);

    List<Customer> findPeopleByGenderAndAddressCountryOrderByBirthday(Gender gender, String country, Pageable pageable);

    Collection<CustomerSummary> findAllSummaryBy();

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Optional<Customer> selectByName(String name);

}
