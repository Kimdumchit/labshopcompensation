package labshopcompensation.domain;

import java.time.LocalDate;
import java.util.*;
import labshopcompensation.domain.*;
import labshopcompensation.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private Long id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;

    public DeliveryStarted(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryStarted() {
        super();
    }
}
//>>> DDD / Domain Event
