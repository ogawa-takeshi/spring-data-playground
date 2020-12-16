package playground;

import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestTests extends AbstractElasticsearchTests {

    @Autowired
    MockMvc mvc;

    @Test
    void createOrder() throws Exception {
        ClassPathResource resource = new ClassPathResource("order.json");
        this.mvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.contentOf(resource.getFile(), "utf-8")))
                .andExpect(status().isCreated());
    }

}
