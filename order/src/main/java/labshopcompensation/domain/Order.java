package labshopcompensation.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshopcompensation.OrderApplication;
import labshopcompensation.domain.OrderPlaced;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private Integer qty;

    private String customerId;

    private Double amount;

    private String status;

    private String address;

    @PostPersist
    public void onPostPersist() {
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {
        // Get request from Inventory
        labshopcompensation.external.Inventory inventory =
           OrderApplication.applicationContext.getBean(labshopcompensation.external.InventoryService.class)
           .getInventory(Long.valueOf(getProductId()));

        if(inventory.getStock() < getQty())
            throw new RuntimeException("Out of stock!");

    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
