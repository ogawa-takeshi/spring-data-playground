package playground;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerTests extends AbstractElasticsearchTests {

    final ElasticsearchOperations operations;

    @Test
    void mapping() {
        var customer = this.operations.save(new Customer("空条承太郎"));
        assertThat(customer.getId()).isNotNull();
        AssertionsForClassTypes.assertThat(customer.getCreatedDate()).isNotNull();
        AssertionsForClassTypes.assertThat(customer.getUpdatedDate()).isNotNull();
    }

}