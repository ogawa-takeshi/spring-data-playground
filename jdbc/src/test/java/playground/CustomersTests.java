package playground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.mapping.model.MappingInstantiationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@DataJdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomersTests {

    final Customers customers;

    @BeforeEach
    void before() {
        this.customers.save(new Customer("空条承太郎"));
    }

    @Test
    void findByName() {
        var customer = this.customers.findByName("空条承太郎");
        assertThat(customer).get().extracting("name").isEqualTo("空条承太郎");
    }

    @Test
    @DisplayName("Spring Data JDBC は Interface-based projections 非対応")
    void findAllSummaryBy() {
        Throwable thrown = catchThrowable(() -> {
            this.customers.findAllSummaryBy();
        });
        assertThat(thrown).isInstanceOf(MappingInstantiationException.class);
    }

    @Test
    void optimisticLocking() {
        var customer1 = this.customers.save(new Customer("東方仗助"));

        var customer2 = this.customers.findById(customer1.getId()).orElseThrow();
        customer2.setEmail("jojo@example.com");
        this.customers.save(customer2);

        customer1.setEmail("crazy-diamond@example.com");
        assertThatExceptionOfType(DbActionExecutionException.class).isThrownBy(() -> this.customers.save(customer1));
    }

}