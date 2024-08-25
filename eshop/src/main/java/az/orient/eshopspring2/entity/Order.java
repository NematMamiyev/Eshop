package az.orient.eshopspring2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@Table(name = "product_order")
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_seq")
    @SequenceGenerator(name = "order_seq",sequenceName = "ORDER_SEQ",allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
