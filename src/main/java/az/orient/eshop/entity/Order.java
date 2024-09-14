package az.orient.eshop.entity;

import az.orient.eshop.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "product_order")
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_order_seq")
    @SequenceGenerator(name = "product_order_seq",sequenceName = "PRODUCT_ORDER_SEQ",allocationSize = 1)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "order_product_details",
            joinColumns = @JoinColumn(name = "product_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_details_id")
    )
    private List<ProductDetails> productDetailsList  = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Float amount;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
