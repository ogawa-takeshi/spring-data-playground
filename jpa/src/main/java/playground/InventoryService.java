package playground;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @EventListener
    public void on(OrderCompleted event) {
        // do something...
    }

}
