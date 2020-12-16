package playground;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static playground.CustomerSpecs.isLongTermCustomer;

@DataJpaTest
@Import(JpaConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerRepositoryTests {

    final CustomerRepository customers;

    final OrderRepository orders;

    final TestEntityManager entityManager;

    @BeforeEach
    void before() {
        var customer1 = this.customers.save(new Customer("空条承太郎", Gender.MALE, LocalDate.of(1971, 2, 1),
                "starplatina@example.com",
                Address.of("日本", "100-0000")));
        var customer2 = this.customers.save(new Customer("東方仗助", Gender.MALE, LocalDate.of(1983, 1, 1),
                "crazy-diamond@example.com",
                Address.of("日本", "100-0000")));
        var customer3 = this.customers.save(new Customer("ジョルノ・ジョバァーナ", Gender.MALE, LocalDate.of(1985, 4, 16),
                "gold-experience@example.com",
                Address.of("イタリア", "00144")));

        this.orders.saveAndFlush(new Order(
                customer1,
                "SF-1234",
                LocalDate.parse("2020-12-17"),
                new OrderLine("T-shirt", 5, BigDecimal.valueOf(15_000)),
                new OrderLine("Socks", 3, BigDecimal.valueOf(900))));
    }

    @Test
    void findAllByName() {
        assertThat(this.customers.findAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.readAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.getAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.queryAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.searchAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.streamAllByName("空条承太郎")).hasSize(1);

        assertThat(this.customers.findTop3ByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.findDistinctByName("空条承太郎")).hasSize(1);
    }

    @Test
    void countByName() {
        assertThat(this.customers.countByName("空条承太郎")).isEqualTo(1);
    }

    @Test
    void existsByName() {
        assertThat(this.customers.existsByName("空条承太郎")).isEqualTo(true);
    }

    @Test
    void deleteByName() {
        assertThat(this.customers.deleteAllByName("東方仗助")).isEqualTo(1);
        assertThat(this.customers.removeAllByName("東方仗助")).isEqualTo(0);
    }

    @Test
    void findAllSummaryBy() {
        var summaries = this.customers.findAllSummaryBy();
        assertThat(summaries).extracting("name", "age").contains(
                tuple("空条承太郎", 49),
                tuple("東方仗助", 37),
                tuple("ジョルノ・ジョバァーナ", 35));
    }

    @Test
    void findAllByExample() {
        var matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        var customers = this.customers.findAll(Example.of(new Customer("空条"), matcher));
        assertThat(customers).hasSize(1);
    }

    @Test
    void findPeopleByGenderAndAddressCountryOrderByBirthday() {
        var customers = this.customers.findPeopleByGenderAndAddressCountryOrderByBirthday(Gender.MALE, "日本", Pageable.unpaged());
        assertThat(customers).hasSize(2);
    }

    @Test
    void findStatisticsByCountry() {
        var statistics = this.customers.findStatisticsByCountry();
        assertThat(statistics).extracting("country", "count").containsSequence(
                tuple("イタリア", 1L),
                tuple("日本", 2L));
    }

    @Test
    void selectByName() {
        var customer = this.customers.selectByName("空条承太郎");
        assertThat(customer).isPresent();
    }

    @Test
    void querydsl() {
        Predicate predicate = QCustomer.customer.gender.eq(Gender.MALE)
                .and(QCustomer.customer.birthday.after(LocalDate.parse("1980-01-01")));
        var customers = this.customers.findAll(predicate);
        assertThat(customers).hasSize(2);
    }

    @Test
    void specification() {
        List<Customer> customers = this.customers.findAll(isLongTermCustomer());
        assertThat(customers).hasSize(0);
    }

    @Test
    void optimisticLocking() {
        var customer1 = this.customers.save(new Customer("吉良吉影"));
        this.entityManager.flush();
        this.entityManager.detach(customer1);

        var customer2 = this.customers.findById(customer1.getId()).orElseThrow();
        customer2.setEmail("killer-queen@example.com");
        this.customers.save(customer2);
        this.entityManager.flush();

        customer1.setEmail("killer-queen@example.com");
        assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> this.customers.save(customer1));
    }

}