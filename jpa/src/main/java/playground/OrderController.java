package playground;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class OrderController {

    final OrderRepository orders;

    @GetMapping("/orders")
    HttpEntity<PagedModel<Order>> listOrders(
            @PageableDefault(50) Pageable pageable,
            PagedResourcesAssembler assembler) {
        var orders = this.orders.findAll(pageable);
        return new ResponseEntity<>(assembler.toModel(orders), HttpStatus.OK);
    }

    @GetMapping("/orders/search")
    HttpEntity<PagedModel<Order>> searchOrders(
            @QuerydslPredicate(root = Order.class) Predicate predicate,
            @PageableDefault(50) Pageable pageable,
            PagedResourcesAssembler assembler) {
        var orders = this.orders.findAll(predicate, pageable);
        return new ResponseEntity<>(assembler.toModel(orders), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    Order getOrder(@PathVariable("id") Order order) {
        return order;
    }

}
