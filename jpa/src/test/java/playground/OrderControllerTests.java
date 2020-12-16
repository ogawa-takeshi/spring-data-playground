package playground;

import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class OrderControllerTests {

    final MockMvc mvc;

    final CustomerRepository customers;

    final OrderRepository orders;

    Order order;

    @BeforeEach
    void before() {
        var customer = this.customers.saveAndFlush(new Customer("空条承太郎"));
        this.order = this.orders.saveAndFlush(new Order(
                customer,
                "SF-1234",
                LocalDate.parse("2020-12-17"),
                new OrderLine("T-shirt", 5, BigDecimal.valueOf(15_000)),
                new OrderLine("Socks", 3, BigDecimal.valueOf(900))));
    }

//    @Test
//    void createOrder() throws Exception {
//        ClassPathResource resource = new ClassPathResource("order.json");
//        this.mvc.perform(post("/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Files.contentOf(resource.getFile(), "utf-8")))
//                .andExpect(status().isCreated());
//    }

    @Test
    void listOrders() throws Exception {
        this.mvc.perform(get("/orders?page=0&size=10&sort=orderedDate"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void searchOrders() throws Exception {
        this.mvc.perform(get("/orders/search?orderNo={orderNO}&orderedDate={orderedDate}&page=0&size=10&sort=orderedDate",
                "SF-1234", "2020-12-17"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getOrder() throws Exception {
        this.mvc.perform(get("/orders/{id}", this.order.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
