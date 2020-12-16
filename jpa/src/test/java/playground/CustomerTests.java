package playground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerTests {

    final TestEntityManager em;

    @Test
    void mapping() {
        var customer = this.em.persistAndFlush(new Customer("空条承太郎"));
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCreatedDate()).isNotNull();
        assertThat(customer.getUpdatedDate()).isNotNull();
    }

}