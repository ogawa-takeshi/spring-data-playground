package playground;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, ObjectId>, CustomerStatisticsRepository,
        QuerydslPredicateExecutor<QCustomer> {

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

    @Query("{'name' : ?0}")
    Optional<Customer> selectByName(String name);

}
