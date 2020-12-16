package playground;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Table("ORDER")
@Getter
public class Order {

    @Id
    private UUID id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private final Customer customer;

    private final String orderNo;

    private final LocalDate orderDate;

    private final List<OrderLine> lines;

    public Order(/*Customer customer,*/ String orderNo, LocalDate orderDate, OrderLine... lines) {
//        this.customer = customer;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.lines = List.of(lines);
    }

    protected Order() {
//        this.customer = null;
        this.orderNo = null;
        this.orderDate = null;
        this.lines = Collections.emptyList();
    }

}
