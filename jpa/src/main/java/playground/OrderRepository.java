package playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;
import java.util.UUID;

interface OrderRepository extends JpaRepository<Order, UUID>, QuerydslPredicateExecutor, QuerydslBinderCustomizer<QOrder> {

    Optional<OrderSummary> findTop1ByCustomer(Customer customer);

    @Override
    default void customize(QuerydslBindings bindings, QOrder order) {
        bindings.bind(order.orderNo).first((path, value) -> path.like(value));
        bindings.bind(order.orderedDate).first((path, value) -> path.gt(value));
    }

}
