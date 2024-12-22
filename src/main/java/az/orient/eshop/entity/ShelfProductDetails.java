package az.orient.eshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "shelf_product_details")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelfProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelf_product_seq")
    @SequenceGenerator(name = "shelf_product_seq", sequenceName = "SHELF_PRODUCT_SEQ", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
    @ManyToOne
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
