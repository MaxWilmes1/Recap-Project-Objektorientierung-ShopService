package Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum OrderStatus {
    IN_PROGRESS,
    IN_DELIVERY,
    WAITING_FOR_PAYMENT,
    DONE
}
