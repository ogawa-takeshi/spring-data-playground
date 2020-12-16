package playground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class OrderRepositoryTests {

    final TestEntityManager entityManager;

    final OrderRepository orders;

    @Test
    void save() {
        var customer = this.entityManager.persistAndFlush(new Customer("空条承太郎"));
        var order = this.orders.saveAndFlush(new Order(
                customer,
                "SF-1234",
                LocalDate.parse("2020-12-17"),
                new OrderLine("T-shirt", 5, BigDecimal.valueOf(15_000)),
                new OrderLine("Socks", 3, BigDecimal.valueOf(900))));
        assertThat(order.getId()).isNotNull();
    }

}