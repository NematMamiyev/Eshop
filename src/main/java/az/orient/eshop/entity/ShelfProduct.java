package az.orient.eshop.entity;

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
@Table(name = "shelf_product")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelfProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelf_product_seq")
    @SequenceGenerator(name = "shelf_product_seq", sequenceName = "SHELF_PRODUCT_SEQ", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
