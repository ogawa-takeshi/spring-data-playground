package playground;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class NotificationService {

    @TransactionalEventListener
    public void on(OrderCompleted event) {
        // do something...
    }

}
