package playground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@JsonIgnoreProperties("customer")
public class Order extends AbstractAggregateRoot<Order> {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private final Customer customer;

    private final String orderNo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate orderedDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderLine> lines;

    public Order(Customer customer, String orderNo, LocalDate orderedDate, OrderLine... lines) {
        this.customer = customer;
        this.orderNo = orderNo;
        this.orderedDate = orderedDate;
        this.lines = List.of(lines);
    }

    protected Order() {
        this.customer = null;
        this.orderNo = null;
        this.orderedDate = null;
        this.lines = Collections.emptyList();
    }

    Order complete() {
        // do something...
        registerEvent(new OrderCompleted(this));
        return this;
    }

}
