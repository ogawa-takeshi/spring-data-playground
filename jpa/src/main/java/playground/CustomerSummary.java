package playground;

import org.springframework.beans.factory.annotation.Value;

public interface CustomerSummary {

    String getName();

    @Value("#{target.age}")
    int getAge();

    @Value("#{@orderRepository.findTop1ByCustomer(target)}")
    OrderSummary getLatestOrder();

}
