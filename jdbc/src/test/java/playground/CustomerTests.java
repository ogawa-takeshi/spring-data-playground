package playground;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(JdbcConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerTests {

    final JdbcAggregateOperations operations;

    @Test
    void mapping() {
        var customer = this.operations.insert(new Customer("空条承太郎"));
        assertThat(customer.getId()).isNotNull();
        AssertionsForClassTypes.assertThat(customer.getCreatedDate()).isNotNull();
        AssertionsForClassTypes.assertThat(customer.getUpdatedDate()).isNotNull();
    }

}