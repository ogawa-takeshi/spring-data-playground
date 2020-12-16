package playground;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public abstract class AbstractElasticsearchTests {

    @Container
    static GenericContainer elasticsearch = new GenericContainer("docker.elastic.co/elasticsearch/elasticsearch:7.9.3")//
            .withEnv("discovery.type", "single-node")//
            .withExposedPorts(9200, 9300)//
            .waitingFor(Wait.forHttp("/")//
                    .forPort(9200)//
                    .forStatusCodeMatching(response -> response == HTTP_OK || response == HTTP_UNAUTHORIZED)//
                    .withStartupTimeout(Duration.ofMinutes(5)));

    @DynamicPropertySource
    static void before(DynamicPropertyRegistry registry) {
        elasticsearch.start();
        registry.add("spring.elasticsearch.rest.uris", () -> "http://localhost:" + elasticsearch.getFirstMappedPort());
    }

}
