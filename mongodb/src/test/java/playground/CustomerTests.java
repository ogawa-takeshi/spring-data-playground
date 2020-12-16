package playground;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
@Import(MongoConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CustomerTests {

    final MongoTemplate template;

    @Test
    void mapping() {
        var customer = this.template.save(new Customer("空条承太郎"));
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCreatedDate()).isNotNull();
        assertThat(customer.getUpdatedDate()).isNotNull();
    }

}