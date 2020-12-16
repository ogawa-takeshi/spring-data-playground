package playground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class OrderEventTests {

    final CustomerRepository customers;

    final OrderRepository orders;

    @Test
    void orderCompleted() {
        var customer = this.customers.saveAndFlush(new Customer("空条承太郎"));
        var order = new Order(
                customer,
                "SF-1234",
                LocalDate.parse("2020-12-17"),
                new OrderLine("T-shirt", 5, BigDecimal.valueOf(15_000)),
                new OrderLine("Socks", 3, BigDecimal.valueOf(900)));
        order.complete();
        var saved = this.orders.saveAndFlush(order);
        assertThat(saved.getId()).isNotNull();
    }

}
