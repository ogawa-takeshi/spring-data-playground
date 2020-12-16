package playground;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class CustomerSpecs {

    public static Specification<Customer> isLongTermCustomer() {
        return (root, query, builder) -> {
            LocalDateTime date = LocalDateTime.now().minusYears(2);
            return builder.lessThan(root.get(Customer_.createdDate), date);
        };
    }

    public static Specification<Customer> hasSalesOfMoreThan(BigDecimal value) {
        return (root, query, builder) -> {
            // build query here
            return null;
        };
    }

}
