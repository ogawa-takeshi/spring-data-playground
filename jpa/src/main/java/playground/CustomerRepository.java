package playground;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, CustomerStatisticsRepository,
        QuerydslPredicateExecutor<QCustomer>,
        JpaSpecificationExecutor<Customer> {

    List<Customer> findAllByName(String name);
    List<Customer> readAllByName(String name);
    List<Customer> getAllByName(String name);
    List<Customer> queryAllByName(String name);
    List<Customer> searchAllByName(String name);
    List<Customer> streamAllByName(String name);

    List<Customer> findTop3ByName(String name);
    List<Customer> findDistinctByName(String name);

    long countByName(String name);
    boolean existsByName(String name);
    int deleteAllByName(String name);
    int removeAllByName(String name);

    Page<Customer> findPeopleByGenderAndAddressCountryOrderByBirthday(Gender gender, String country, Pageable pageable);

    List<CustomerSummary> findAllSummaryBy();

    @Query("select e from #{#entityName} e where e.name = ?1")
    Optional<Customer> selectByName(String name);

}
