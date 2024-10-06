package az.orient.eshop.entity;

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
@Table(name = "cart_customer")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_customer_seq")
    @SequenceGenerator(name = "cart_customer_seq", sequenceName = "CART_CUSTOMER_SEQ", allocationSize = 1)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_details_id")
    )
    private List<ProductDetails> productDetailsList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private Float amount;

    @CreationTimestamp
    private Date dataDate;

    @ColumnDefault(value = "1")
    private Integer active;
}
