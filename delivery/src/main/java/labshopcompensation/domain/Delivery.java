package labshopcompensation.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshopcompensation.DeliveryApplication;
import labshopcompensation.domain.DeliveryStarted;
import lombok.Data;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private String customerId;

    private Integer quantity;

    private Long orderId;

    @PostPersist
    public void onPostPersist() {
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }


    public static void addToDeliveryList(OrderPlaced orderPlaced) {
        /** Example 1:  new item  */
        Delivery delivery = new Delivery();

        delivery.setOrderId(orderPlaced.getId());
        delivery.setCustomerId(orderPlaced.getCustomerId());
        delivery.setQuantity(orderPlaced.getQty());
        delivery.setAddress(orderPlaced.getAddress());
        
        repository().save(delivery);

       

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

    }

}
//>>> DDD / Aggregate Root
