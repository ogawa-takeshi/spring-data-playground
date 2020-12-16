package playground;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerRepositoryTests extends AbstractElasticsearchTests {

    final CustomerRepository customers;

    @BeforeEach
    void before() {
        this.customers.save(new Customer("空条承太郎", Gender.MALE, LocalDate.of(1971, 2, 1),
                "starplatina@example.com",
                Address.of("日本", "100-0000")));
        this.customers.save(new Customer("東方仗助", Gender.MALE, LocalDate.of(1983, 1, 1),
                "crazy-diamond@example.com",
                Address.of("日本", "100-0000")));
        this.customers.save(new Customer("ジョルノ・ジョバァーナ", Gender.MALE, LocalDate.of(1985, 4, 16),
                "gold-experience@example.com",
                Address.of("イタリア", "00144")));
    }

    @AfterEach
    void after() {
        this.customers.deleteAll();
    }

    @Test
    void findAllByName() {
        assertThat(this.customers.findAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.readAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.getAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.queryAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.searchAllByName("空条承太郎")).hasSize(1);
        assertThat(this.customers.streamAllByName("空条承太郎")).hasSize(1);
    }

    @Test
    void countByName() {
        assertThat(this.customers.countByName("空条承太郎")).isEqualTo(1);
    }

    @Test
    void existsByName() {
        assertThat(this.customers.existsByName("空条承太郎")).isPresent();
    }

    @Test
    void deleteByName() {
        assertThat(this.customers.deleteAllByName("空条承太郎")).isEqualTo(1);
        assertThat(this.customers.removeAllByName("空条承太郎")).isEqualTo(0);
    }

    @Test
    @DisplayName("Spring Data Elasticsearch は Interface-based projections 非対応")
    void findAllSummaryBy() {
        assertThatExceptionOfType(ConversionFailedException.class).isThrownBy(() -> this.customers.findAllSummaryBy());
    }

    @Test
    void findPeopleByGenderAndAddressCountryOrderByBirthday() {
        var customers = this.customers.findPeopleByGenderAndAddressCountryOrderByBirthday(Gender.MALE, "日本", Pageable.unpaged());
        assertThat(customers).hasSize(2);
    }

    @Test
    void findStatisticsByCountry() {
        var statistics = this.customers.findStatisticsByCountry();
        var aggregation = (Terms) statistics.getAggregations().get("statisticsByCountry");
        var buckets = aggregation.getBuckets();
        assertThat(buckets).extracting("keyAsString", "docCount").containsSequence(
                tuple("イタリア", 1L),
                tuple("日本", 2L));
    }

    @Test
    void selectByName() {
        var customer = this.customers.selectByName("空条承太郎");
        assertThat(customer).isPresent();
    }

    /**
     * @see VersionType
     * @see SeqNoPrimaryTerm
     */
    @Test
    void optimisticLocking() {
        var customer1 = this.customers.save(new Customer("吉良吉影"));

        var customer2 = this.customers.findById(customer1.getId()).orElseThrow();
        customer2.setEmail("killer-queen@example.com");
        this.customers.save(customer2);

        customer1.setEmail("killer-queen@example.com");
        assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> this.customers.save(customer1));
    }

}